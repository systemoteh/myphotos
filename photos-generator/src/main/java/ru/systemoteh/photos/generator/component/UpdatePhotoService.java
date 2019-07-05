package ru.systemoteh.photos.generator.component;

import ru.systemoteh.photos.ejb.repository.PhotoRepository;
import ru.systemoteh.photos.model.domain.Photo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class UpdatePhotoService {

    @Inject
    private PhotoRepository photoRepository;

    private Random random = new Random();

    @Transactional
    public void updatePhotos(List<Photo> photos) {
        for (Photo photo : photos) {
            photo.setDownloads(random.nextInt(100));
            photo.setViews(random.nextInt(1000) * 5 + 100);
            photoRepository.update(photo);
        }
    }
}