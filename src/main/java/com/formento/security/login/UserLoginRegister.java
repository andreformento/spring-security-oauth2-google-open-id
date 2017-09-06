package com.formento.security.login;

import org.springframework.stereotype.Component;

@Component
public class UserLoginRegister {

    private final UserLoginRepository userLoginRepository;

    public UserLoginRegister(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    public UserLogin createOrLoadUser(final String username, final String socialServiceName, final String socialId) {
        return userLoginRepository.
            findByUsernameAndSocialServiceNameAndSocialId(username, socialServiceName, socialId).
            orElseGet(() -> userLoginRepository.save(new UserLogin(null, username, socialServiceName, socialId)));
    }

}
