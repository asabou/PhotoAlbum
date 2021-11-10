package com.tpjad.project.photoalbumapi.dao.repository;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumEntity;
import com.tpjad.project.photoalbumapi.dao.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhotoRepository extends JpaRepository<PhotoEntity, Long> {
    @Query("delete from AlbumPhotoEntity ap where ap.album = :album and ap.photo = :photo")
    void deletePhotoFromAlbum(Long album, Long photo);
}
