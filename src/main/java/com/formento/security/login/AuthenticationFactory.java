package com.formento.security.login;

import com.formento.security.openid.OpenIdConnectUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFactory {

    private final UserLoginRegister userLoginRegister;

    @Autowired
    public AuthenticationFactory(UserLoginRegister userLoginRegister) {
        this.userLoginRegister = userLoginRegister;
    }

    public Authentication create(final AuthInfoDTO authInfo, final String socialServiceName, final OAuth2AccessToken accessToken) {
        final String username = authInfo.getUsername();
        final String socialId = authInfo.getSocialId();

        final UserLogin userLogin = userLoginRegister.createOrLoadUser(username, socialServiceName, socialId);

        final UserDetails user = new OpenIdConnectUserDetails(userLogin, accessToken);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
