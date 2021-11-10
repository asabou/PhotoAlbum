package com.tpjad.project.photoalbumapi.service.abstracts;

import com.tpjad.project.photoalbumapi.service.model.Album;

import java.util.List;

public interface IAlbumService {
    void create(Album album, Long userId);
    void update(Album album);
    void delete(Long id);
    List<Album> findAllForUser(Long userId);
}
