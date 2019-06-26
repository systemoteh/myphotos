package ru.systemoteh.ejb.repository;

import ru.systemoteh.photos.model.domain.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends EntityRepository<Profile, Long> {

    Optional<Profile> findByUid(String uid);

    Optional<Profile> findByEmail(String email);

    void updateRating();

    List<String> findUids(List<String> uids);
}
