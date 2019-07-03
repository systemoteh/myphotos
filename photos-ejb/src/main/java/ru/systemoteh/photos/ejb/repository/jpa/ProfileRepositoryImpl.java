package ru.systemoteh.photos.ejb.repository.jpa;

import ru.systemoteh.photos.ejb.repository.ProfileRepository;
import ru.systemoteh.photos.model.domain.Profile;

import javax.enterprise.context.Dependent;
import java.util.List;
import java.util.Optional;

@Dependent
public class ProfileRepositoryImpl extends AbstractJPARepository<Profile, Long> implements ProfileRepository {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }

    @Override
    public Optional<Profile> findByUid(String uid) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void updateRating() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<String> findUids(List<String> uids) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
