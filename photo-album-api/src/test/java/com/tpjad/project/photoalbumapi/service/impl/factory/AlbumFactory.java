package com.tpjad.project.photoalbumapi.service.impl.factory;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumEntity;
import com.tpjad.project.photoalbumapi.dao.entity.AlbumPhotoEntity;
import com.tpjad.project.photoalbumapi.dao.entity.PhotoEntity;
import com.tpjad.project.photoalbumapi.service.model.Album;
import com.tpjad.project.photoalbumapi.service.model.Photo;

public class AlbumFactory {
    public AlbumEntity createMockAlbumEntity(String name) {
        AlbumEntity album = new AlbumEntity();
        album.setTitle(name);
        return album;
    }

    public PhotoEntity createMockPhotoEntity(String name) {
        PhotoEntity photo = new PhotoEntity();
        photo.setTitle(name);
        return photo;
    }

    public AlbumPhotoEntity createMockPhotoAlbumEntity(String name) {
        AlbumPhotoEntity entity = new AlbumPhotoEntity();
        entity.setAlbum(createMockAlbumEntity(name));
        entity.setPhoto(createMockPhotoEntity(name));
        return entity;
    }

    public Album createMockAlbum(String name) {
        Album album = new Album();
        album.setTitle(name);
        return album;
    }

    public Photo createMockPhoto(String name) {
        Photo photo = new Photo();
        photo.setTitle(name);
        return photo;
    }

}
