package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.*;
import com.tpjad.project.photoalbumapi.dao.repository.*;
import com.tpjad.project.photoalbumapi.service.abstracts.AbstractService;
import com.tpjad.project.photoalbumapi.service.abstracts.IAlbumService;
import com.tpjad.project.photoalbumapi.service.helper.AlbumTransformer;
import com.tpjad.project.photoalbumapi.service.model.Album;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl extends AbstractService implements IAlbumService {
    private final IAlbumRepository albumRepository;
    private final IAlbumPhotoRepository albumPhotoRepository;
    private final IPhotoRepository photoRepository;

    public AlbumServiceImpl(PasswordEncoder passwordEncoder,
                            IUserRepository userRepository,
                            IUserDetailsRepository userDetailsRepository,
                            IAlbumRepository albumRepository,
                            IAlbumPhotoRepository albumPhotoRepository,
                            IPhotoRepository photoRepository) {
        super(passwordEncoder, userRepository, userDetailsRepository);
        this.albumRepository = albumRepository;
        this.albumPhotoRepository = albumPhotoRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public void create(Album album, Long userId) {
        final Optional<UserDetailsEntity> userDetailsEntityOptional = userDetailsRepository.findById(userId);
        if (!userDetailsEntityOptional.isPresent()) {
            throwBadRequestException("Cannot create album for this user!");
        }
        AlbumEntity albumEntity = AlbumTransformer.transform(album);
        albumEntity.setUserDetails(userDetailsEntityOptional.get());
        albumRepository.save(albumEntity);
    }

    @Override
    public void update(Album album) {
        Optional<AlbumEntity> albumEntityOptional = albumRepository.findById(album.getId());
        if (!albumEntityOptional.isPresent()) {
            throwNotFoundException("Cannot find the album!");
        }
        AlbumEntity albumToUpdate = albumEntityOptional.get();
        albumToUpdate.setTitle(album.getTitle());
        albumRepository.save(albumToUpdate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<AlbumEntity> albumEntityOptional = albumRepository.findById(id);
        if (!albumEntityOptional.isPresent()) {
            throwNotFoundException("Cannot find the album!");
        }
        deleteAllDependentEntities(albumEntityOptional.get().getId());
        albumRepository.delete(albumEntityOptional.get());
    }

    @Override
    public List<Album> findAllForUser(Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (!userEntityOptional.isPresent()) {
            throwNotFoundException("Cannot find the user!");
        }
        final List<AlbumEntity> albums = albumRepository.findAlbumEntitiesByUserId(userId);
        return AlbumTransformer.transform(albums);
    }

    private void deleteAllDependentEntities(Long albumId) {
        List<PhotoEntity> photos = albumPhotoRepository.findPhotosFromAlbum(albumId);
        photoRepository.deleteAll(photos);
        photos.forEach(photo -> {
            List<AlbumPhotoEntity> albumPhotoEntities = albumPhotoRepository.findByAlbumIdAndPhotoId(albumId, photo.getId());
            albumPhotoRepository.deleteAll(albumPhotoEntities);
        });
    }
}
