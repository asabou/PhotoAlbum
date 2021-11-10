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
@Table(name = "photos")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    private String title;

    private Date date;

    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.MERGE)
    private Set<AlbumPhotoEntity> albumPhotoEntities = new HashSet<>();
}
