package ru.systemoteh.photos.service;

import ru.systemoteh.photos.exception.RetrieveSocialDataFailedException;
import ru.systemoteh.photos.model.domain.Profile;

public interface SocialService {

    Profile fetchProfile(String code) throws RetrieveSocialDataFailedException;

    String getClientId();

    default String getAuthorizeUrl() {
        throw new UnsupportedOperationException();
    }
}
