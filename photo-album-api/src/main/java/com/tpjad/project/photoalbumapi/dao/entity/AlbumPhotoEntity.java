package com.tpjad.project.photoalbumapi.dao.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "albums_photos")
@IdClass(AlbumPhotoEntityId.class)
public class AlbumPhotoEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @Id
    @ManyToOne
    @JoinColumn(name = "photo_id")
    private PhotoEntity photo;
}
