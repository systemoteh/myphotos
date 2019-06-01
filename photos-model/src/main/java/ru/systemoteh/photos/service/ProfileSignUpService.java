package ru.systemoteh.photos.service;

import ru.systemoteh.photos.exception.ObjectNotFoundException;
import ru.systemoteh.photos.model.domain.Profile;

public interface ProfileSignUpService {

    void createSignUpProfile(Profile profile);

    Profile getCurrentProfile() throws ObjectNotFoundException;

    void completeSignUp();

    void cancel();
}
