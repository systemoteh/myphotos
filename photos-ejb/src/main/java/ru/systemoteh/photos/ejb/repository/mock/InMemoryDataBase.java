package ru.systemoteh.photos.ejb.repository.mock;

import ru.systemoteh.photos.model.domain.Photo;
import ru.systemoteh.photos.model.domain.Profile;

import java.util.*;

public final class InMemoryDataBase {

    public static final Profile PROFILE;

    public static final List<Photo> PHOTOS;

    static {
        PROFILE = createProfile();
        PHOTOS = createPhotos(PROFILE);
    }

    private static Profile createProfile() {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setUid("petr-first");
        profile.setCreated(new Date());
        profile.setFirstName("Petr");
        profile.setLastName("First");
        profile.setJobTitle("CEO of IT-Land");
        profile.setLocation("Moscow, Russia");
        profile.setAvatarUrl("http://systemoteh.ru/photos/petr-first.jpg");
        return profile;
    }

    private static List<Photo> createPhotos(Profile profile) {
        Random random = new Random();
        List<Photo> photos = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Photo photo = new Photo();
            photo.setProfile(profile);
            profile.setPhotoCount(profile.getPhotoCount() + 1);
            String imageUrl = String.format("http://systemoteh.ru/photos/test-images/%s.jpg", i % 6 + 1);
            photo.setSmallUrl(imageUrl);
            photo.setLargeUrl("http://systemoteh.ru/photos/test-images/large.jpg");
            photo.setOriginalUrl(imageUrl);
            photo.setViews(random.nextInt(100) * 10 + 1);
            photo.setDownloads(random.nextInt(20) * 10 + 1);
            photo.setCreated(new Date());
            photos.add(photo);
        }
        return Collections.unmodifiableList(photos);
    }

    private InMemoryDataBase() {
    }
}
