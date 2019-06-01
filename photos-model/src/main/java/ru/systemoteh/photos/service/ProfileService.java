package ru.systemoteh.photos.service;

import ru.systemoteh.photos.exception.ObjectNotFoundException;
import ru.systemoteh.photos.model.AsyncOperation;
import ru.systemoteh.photos.model.ImageResource;
import ru.systemoteh.photos.model.domain.Profile;

import java.util.Optional;

public interface ProfileService {

    Profile findById(Long id) throws ObjectNotFoundException;

    Profile findByUid(String uid) throws ObjectNotFoundException;

    Optional<Profile> findByEmail(String email);

    void signUp(Profile profile, boolean uploadProfileAvatar);

    void translitSocialProfile(Profile profile);

    void update(Profile profile);

    void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation);
}
