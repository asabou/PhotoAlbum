package com.tpjad.project.photoalbumapi.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "albums")
public class  AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    private String title;

    @ManyToOne
    private UserDetailsEntity userDetails;

    @OneToMany(mappedBy = "album", cascade =  { CascadeType.MERGE, CascadeType.REMOVE })
    private Set<AlbumPhotoEntity> albumPhotoEntities = new HashSet<>();
}
