package ru.systemoteh.photos.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.systemoteh.photos.annotation.Email;
import ru.systemoteh.photos.annotation.EnglishLanguage;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "photos", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"uid"})
})
public class Profile extends AbstractDomain {

    @Id
    @Basic(optional = false)
    @Column(unique = true, nullable = false, updatable = false)
    @SequenceGenerator(name = "profile_generator", sequenceName = "profile_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_generator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(unique = true, nullable = false, length = 255, updatable = false)
    private String uid;

    @Email
    @NotNull
    @Size(max = 100)
    @Basic(optional = false)
    @Column(unique = true, nullable = false, length = 100, updatable = false)
    private String email;

    @NotNull(message = "{Profile.firstName.NotNull}")
    @Size(min = 1, max = 50, message = "{Profile.firstName.Size}")
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull(message = "{Profile.lastName.NotNull}")
    @Size(min = 1, max = 60, message = "{Profile.lastName.Size}")
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "avatar_url", nullable = false, length = 255)
    private String avatarUrl;

    @NotNull(message = "{Profile.jobTitle.NotNull}")
    @Size(min = 5, max = 100, message = "{Profile.jobTitle.Size}")
    @EnglishLanguage(withSpecialSymbols = false)
    @Basic(optional = false)
    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @NotNull(message = "{Profile.location.NotNull}")
    @Size(min = 5, max = 100, message = "{Profile.location.Size}")
    @EnglishLanguage(withSpecialSymbols = false)
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String location;

    @Min(0)
    @Basic(optional = false)
    @Column(name = "photo_count", nullable = false)
    private int photoCount;

    @Min(0)
    @Basic(optional = false)
    @Column(nullable = false)
    private int rating;
}