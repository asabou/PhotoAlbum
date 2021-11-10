package com.tpjad.project.photoalbumapi.service.abstracts;

import com.tpjad.project.photoalbumapi.service.model.Photo;

import java.util.List;

public interface IPhotoService {
    void create(Photo photo);
    void update(Photo photo);
    void delete(Long id);
    List<Photo> findAllFromAlbum(Long idAlbum);
    void deleteFromAlbum(Long idAlbum, Long idPhoto);
}
