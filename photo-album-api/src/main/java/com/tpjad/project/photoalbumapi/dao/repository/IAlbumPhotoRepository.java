package com.tpjad.project.photoalbumapi.dao.repository;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumPhotoEntity;
import com.tpjad.project.photoalbumapi.dao.entity.AlbumPhotoEntityId;
import com.tpjad.project.photoalbumapi.dao.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlbumPhotoRepository extends JpaRepository<AlbumPhotoEntity, AlbumPhotoEntityId> {
    @Query("select ap from AlbumPhotoEntity ap where ap.album.id = :albumId and ap.photo.id = :photoId")
    List<AlbumPhotoEntity> findByAlbumIdAndPhotoId(final Long albumId, final Long photoId);

    @Query("delete from AlbumPhotoEntity ap where ap.album.id = :albumId and ap.photo.id = :photoId")
    void deleteAlbumPhotoByAlbumIdAndPhotoId(final Long albumId, final Long photoId);

    @Query("select p from PhotoEntity p " +
            "inner join AlbumPhotoEntity ap on p.id = ap.photo.id " +
            "inner join AlbumEntity a on ap.album.id = a.id " +
            "where a.id = :albumId")
    List<PhotoEntity> findPhotosFromAlbum(Long albumId);
}
