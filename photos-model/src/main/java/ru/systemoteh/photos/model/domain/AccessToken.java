package ru.systemoteh.photos.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "access_token", schema = "public")
public class AccessToken extends AbstractDomain {

    @Id
    @Basic(optional = false)
    @Column(unique = true, nullable = false)
    @NotNull
    private String token;

    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;
}