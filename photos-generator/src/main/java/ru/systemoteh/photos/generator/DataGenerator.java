package ru.systemoteh.photos.generator;

import ru.systemoteh.photos.common.annotation.cdi.Property;
import ru.systemoteh.photos.common.config.ImageCategory;
import ru.systemoteh.photos.common.model.TempImageResource;
import ru.systemoteh.photos.ejb.service.bean.PhotoServiceBean;
import ru.systemoteh.photos.ejb.service.bean.ProfileServiceBean;
import ru.systemoteh.photos.generator.component.AbstractEnvironmentGenerator;
import ru.systemoteh.photos.generator.component.PhotoGenerator;
import ru.systemoteh.photos.generator.component.ProfileGenerator;
import ru.systemoteh.photos.generator.component.UpdatePhotoService;
import ru.systemoteh.photos.model.domain.Photo;
import ru.systemoteh.photos.model.domain.Profile;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DataGenerator extends AbstractEnvironmentGenerator {

    @Inject
    private ProfileGenerator profileGenerator;

    @Inject
    private PhotoGenerator photoGenerator;

    @Inject
    private UpdatePhotoService updatePhotoService;

    @EJB
    private ProfileServiceBean profileServiceBean;

    @EJB
    private PhotoServiceBean photoServiceBean;

    @Resource(mappedName = "PhotosDBPool")
    private DataSource dataSource;

    @Inject
    @Property("photos.storage.root.dir")
    private String storageRoot;

    @Inject
    @Property("photos.media.absolute.root")
    private String mediaRoot;

    public static void main(String[] args) throws Exception {
        new DataGenerator().execute();
    }

    @Override
    protected void generate() throws Exception {
        clearExternalResources();
        List<Profile> profiles = profileGenerator.generateProfiles();
        List<Photo> uploadedPhotos = new ArrayList<>();
        for (Profile profile : profiles) {
            profileServiceBean.signUp(profile, false);
            profileServiceBean.uploadNewAvatar(profile, new PathImageResource(profile.getAvatarUrl()));
            List<String> photoPaths = photoGenerator.generatePhotos(profile.getPhotoCount());
            for (String path : photoPaths) {
                Profile dbProfile = profileServiceBean.findById(profile.getId());
                uploadedPhotos.add(photoServiceBean.uploadNewPhoto(dbProfile, new PathImageResource(path)));
            }
        }
        updatePhotoService.updatePhotos(uploadedPhotos);
        updateProfileRating();
        System.out.println("Generated " + profiles.size() + " profiles");
        System.out.println("Generated " + uploadedPhotos.size() + " photos");
    }

    private void clearExternalResources() throws SQLException, IOException {
        clearDatabase();
        clearDirectory(storageRoot);
        clearDirectory(mediaRoot + ImageCategory.LARGE_PHOTO.getRelativeRoot());
        clearDirectory(mediaRoot + ImageCategory.SMALL_PHOTO.getRelativeRoot());
        clearDirectory(mediaRoot + ImageCategory.PROFILE_AVATAR.getRelativeRoot());
    }

    private void clearDatabase() throws SQLException {
        try (Connection c = dataSource.getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate("TRUNCATE photo CASCADE");
            st.executeUpdate("TRUNCATE access_token CASCADE");
            st.executeUpdate("TRUNCATE profile CASCADE");
            st.executeQuery("SELECT SETVAL('profile_seq', 1, false)");
            st.executeQuery("SELECT SETVAL('photo_seq', 100001, false)");
        }
        System.out.println("Database cleared");
    }

    private void clearDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (Files.exists(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return super.visitFile(file, attrs);
                }
            });
            System.out.println("Directory " + directoryPath + " cleared");
        } else {
            Files.createDirectories(path);
        }
    }

    private void updateProfileRating() throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT update_rating()")) {
            c.setAutoCommit(false);
            ps.executeQuery();
            c.commit();
        }
    }

    private static class PathImageResource extends TempImageResource {

        public PathImageResource(String path) throws IOException {
            Files.copy(Paths.get(path), getTempPath(), REPLACE_EXISTING);
        }
    }
}
