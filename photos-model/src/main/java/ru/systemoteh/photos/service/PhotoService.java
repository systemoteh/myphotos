package ru.systemoteh.photos.service;

import ru.systemoteh.photos.exception.ObjectNotFoundException;
import ru.systemoteh.photos.model.*;
import ru.systemoteh.photos.model.domain.Photo;
import ru.systemoteh.photos.model.domain.Profile;

import java.util.List;

public interface PhotoService {

    List<Photo> findProfilePhotos(Long profileId, Pageable pageable);

    List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable);

    long countAllPhotos();

    String viewLargePhoto(Long photoId) throws ObjectNotFoundException;

    OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException;

    void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation);
}