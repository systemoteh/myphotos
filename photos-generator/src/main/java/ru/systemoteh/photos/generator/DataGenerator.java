package ru.systemoteh.photos.generator;

import ru.systemoteh.photos.common.annotation.cdi.Property;
import ru.systemoteh.photos.common.config.ImageCategory;
import ru.systemoteh.photos.generator.component.AbstractEnvironmentGenerator;
import ru.systemoteh.photos.generator.component.PhotoGenerator;
import ru.systemoteh.photos.generator.component.ProfileGenerator;
import ru.systemoteh.photos.model.domain.Profile;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataGenerator extends AbstractEnvironmentGenerator {

    @Inject
    private ProfileGenerator profileGenerator;

    @Inject
    private PhotoGenerator photoGenerator;

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
        //TODO craete profiles
        System.out.println("Generated " + profiles.size() + " profiles");
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
            st.executeQuery("SELECT SETVAL('photo_seq', 100000, false)");
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

}
