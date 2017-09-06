package com.formento.security.login;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class AuthInfoDTO implements Serializable {

    private static final long serialVersionUID = -3990492711214610130L;

    private final String socialId;
    private final String username;

    @ConstructorProperties({"sub", "email"})
    public AuthInfoDTO(String socialId, String username) {
        this.socialId = socialId;
        this.username = username;
    }

    public String getSocialId() {
        return socialId;
    }

    public String getUsername() {
        return username;
    }

}
