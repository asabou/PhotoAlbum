package com.tpjad.project.photoalbumapi.dao.repository;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlbumRepository extends JpaRepository<AlbumEntity, Long> {
    @Query("select a from AlbumEntity a where a.userDetails.id = :userId")
    List<AlbumEntity> findAlbumEntitiesByUserId(final Long userId);
}
