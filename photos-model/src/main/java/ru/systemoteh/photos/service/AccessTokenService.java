package ru.systemoteh.photos.service;

import ru.systemoteh.photos.exception.AccessForbiddenException;
import ru.systemoteh.photos.exception.InvalidAccessTokenException;
import ru.systemoteh.photos.model.domain.AccessToken;
import ru.systemoteh.photos.model.domain.Profile;

public interface AccessTokenService {

    AccessToken generateAccessToken(Profile profile);

    Profile findProfile(String token, Long profileId) throws AccessForbiddenException, InvalidAccessTokenException;

    void invalidateAccessToken(String token) throws InvalidAccessTokenException;
}