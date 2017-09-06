package com.formento.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formento.security.login.AuthenticationFactory;
import com.formento.security.openid.OpenIdConnectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${google.loginUri}")
    private String loginUri;

    @Value("${google.socialServiceName}")
    private String socialServiceName;

    @Autowired
    private OAuth2RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationFactory authenticationFactory;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public OpenIdConnectFilter openIdConnectFilter() {
        return new OpenIdConnectFilter(objectMapper, loginUri, restTemplate, authenticationFactory, socialServiceName);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class).
                addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class).
                httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(loginUri)).
                and().authorizeRequests().
                anyRequest().authenticated();
    }
}
