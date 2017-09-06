package com.formento.security.login;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends MongoRepository<UserLogin, UUID> {

    Optional<UserLogin> findByUsernameAndSocialServiceNameAndSocialId(final String username, final String socialServiceName, final String socialId);

}
