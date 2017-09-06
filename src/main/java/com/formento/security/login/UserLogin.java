package com.formento.security.login;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class UserLogin implements Serializable {

    private static final long serialVersionUID = -7937725713241340572L;

    private final String id;
    private final String username;
    private final String socialServiceName;
    private final String socialId;

    @ConstructorProperties({"id", "username", "socialServiceName", "socialId"})
    public UserLogin(String id, String username, String socialServiceName, String socialId) {
        this.id = id;
        this.username = username;
        this.socialServiceName = socialServiceName;
        this.socialId = socialId;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSocialServiceName() {
        return socialServiceName;
    }

    public String getSocialId() {
        return socialId;
    }
}
