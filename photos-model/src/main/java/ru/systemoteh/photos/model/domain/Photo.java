package ru.systemoteh.photos.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "photos", schema = "public")
public class Photo extends AbstractDomain {

    @Id
    @Basic(optional = false)
    @Column(unique = true, nullable = false, updatable = false)
    @SequenceGenerator(name = "photo_generator", sequenceName = "photo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_generator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "small_url", nullable = false, length = 255, updatable = false)
    private String smallUrl;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "large_url", nullable = false, length = 255, updatable = false)
    private String largeUrl;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "original_url", nullable = false, length = 255, updatable = false)
    private String originalUrl;

    @Min(0)
    @Basic(optional = false)
    @Column(nullable = false)
    private long views;

    @Min(0)
    @Basic(optional = false)
    @Column(nullable = false)
    private long downloads;

    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;
}