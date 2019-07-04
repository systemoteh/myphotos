package ru.systemoteh.photos.ejb.service.bean;

import ru.systemoteh.photos.common.annotation.cdi.Property;
import ru.systemoteh.photos.common.config.ImageCategory;
import ru.systemoteh.photos.ejb.repository.ProfileRepository;
import ru.systemoteh.photos.ejb.service.ImageStorageService;
import ru.systemoteh.photos.ejb.service.interceptor.AsyncOperationInterceptor;
import ru.systemoteh.photos.exception.ObjectNotFoundException;
import ru.systemoteh.photos.model.AsyncOperation;
import ru.systemoteh.photos.model.ImageResource;
import ru.systemoteh.photos.model.domain.Profile;
import ru.systemoteh.photos.service.ProfileService;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Optional;

@Stateless
@LocalBean
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService {

    @Inject
    @Property("photos.profile.avatar.placeholder.url")
    private String avatarPlaceHolderUrl;

    @Inject
//    @Factory    // for mock data
    private ProfileRepository profileRepository;

    @EJB
    private ImageProcessorBean imageProcessorBean;

    @Inject
    private ImageStorageService imageStorageService;

    @Override
    public Profile findById(Long id) throws ObjectNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if (!profile.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by id: %s", id));
        }
        return profile.get();
    }

    @Override
    public Profile findByUid(String uid) throws ObjectNotFoundException {
        Optional<Profile> profile = profileRepository.findByUid(uid);
        if (!profile.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by uid: %s", uid));
        }
        return profile.get();
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public void signUp(Profile profile, boolean uploadProfileAvatar) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void translitSocialProfile(Profile profile) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void update(Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    @Asynchronous
    @Interceptors(AsyncOperationInterceptor.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation) {
        try {
            uploadNewAvatar(currentProfile, imageResource);
            asyncOperation.onSuccess(currentProfile);
        } catch (Throwable throwable) {
            setAvatarPlaceHolder(currentProfile);
            asyncOperation.onFailed(throwable);
        }
    }

    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource) {
        String avatarUrl = imageProcessorBean.processProfileAvatar(imageResource);
        if (ImageCategory.isImageCategoryUrl(currentProfile.getAvatarUrl())) {
            imageStorageService.deletePublicImage(currentProfile.getAvatarUrl());
        }
        currentProfile.setAvatarUrl(avatarUrl);
        profileRepository.update(currentProfile);
    }

    public void setAvatarPlaceHolder(Long profileId) {
        setAvatarPlaceHolder(profileRepository.findById(profileId).get());
    }

    public void setAvatarPlaceHolder(Profile currentProfile) {
        if (currentProfile.getAvatarUrl() == null) {
            currentProfile.setAvatarUrl(avatarPlaceHolderUrl);
            profileRepository.update(currentProfile);
        }
    }

}