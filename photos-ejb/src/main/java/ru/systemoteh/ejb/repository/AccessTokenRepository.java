package ru.systemoteh.ejb.repository;

import ru.systemoteh.photos.model.domain.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends EntityRepository<AccessToken, String> {

    Optional<AccessToken> findByToken(String token);

    boolean removeAccessToken(String token);
}
