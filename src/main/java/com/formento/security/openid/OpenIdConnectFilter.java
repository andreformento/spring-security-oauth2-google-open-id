package com.formento.security.openid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formento.security.login.AuthInfoDTO;
import com.formento.security.login.AuthenticationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;
    private final OAuth2RestOperations restTemplate;
    private final AuthenticationFactory authenticationFactory;
    private final String socialServiceName;

    public OpenIdConnectFilter(ObjectMapper objectMapper, String defaultFilterProcessesUrl, OAuth2RestOperations restTemplate, AuthenticationFactory authenticationFactory, String socialServiceName) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.authenticationFactory = authenticationFactory;
        this.socialServiceName = socialServiceName;
        setAuthenticationManager(new NoopAuthenticationManager());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        OAuth2AccessToken accessToken;
        try {
            accessToken = restTemplate.getAccessToken();
        } catch (final OAuth2Exception e) {
            throw new BadCredentialsException("Could not obtain access token", e);
        }
        try {
            final String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
            final Jwt tokenDecoded = JwtHelper.decode(idToken);
            logger.debug("===== : " + tokenDecoded.getClaims());

            final AuthInfoDTO authInfo = objectMapper.readValue(tokenDecoded.getClaims(), new TypeReference<AuthInfoDTO>() {
            });

            return authenticationFactory.create(authInfo, socialServiceName, accessToken);
        } catch (final InvalidTokenException e) {
            throw new BadCredentialsException("Could not obtain user details from token", e);
        }

    }

    private static class NoopAuthenticationManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
        }

    }
}
