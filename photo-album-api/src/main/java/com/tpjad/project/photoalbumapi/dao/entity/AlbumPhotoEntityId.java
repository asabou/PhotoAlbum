package com.tpjad.project.photoalbumapi.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@ToString
public class AlbumPhotoEntityId implements Serializable {
    private Long album;
    private Long photo;
}
