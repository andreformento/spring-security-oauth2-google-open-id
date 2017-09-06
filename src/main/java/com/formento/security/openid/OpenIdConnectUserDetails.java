package com.formento.security.openid;

import com.formento.security.login.UserLogin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;
import java.util.Collection;

public class OpenIdConnectUserDetails implements UserDetails {

    private static final long serialVersionUID = -8210486668733648080L;

    private final UserLogin userLogin;
    private final DefaultOAuth2AccessToken token;

    public OpenIdConnectUserDetails(UserLogin userLogin, OAuth2AccessToken token) {
        this.userLogin = userLogin;
        this.token = new DefaultOAuth2AccessToken(token);
    }

    @Override
    public String getUsername() {
        return userLogin.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public OAuth2AccessToken getToken() {
        return token;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
