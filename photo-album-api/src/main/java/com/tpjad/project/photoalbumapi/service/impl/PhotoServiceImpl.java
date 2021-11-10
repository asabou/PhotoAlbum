package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumEntity;
import com.tpjad.project.photoalbumapi.dao.entity.AlbumPhotoEntity;
import com.tpjad.project.photoalbumapi.dao.entity.PhotoEntity;
import com.tpjad.project.photoalbumapi.dao.repository.*;
import com.tpjad.project.photoalbumapi.service.abstracts.AbstractService;
import com.tpjad.project.photoalbumapi.service.abstracts.IPhotoService;
import com.tpjad.project.photoalbumapi.service.helper.PhotoTransformer;
import com.tpjad.project.photoalbumapi.service.model.Photo;
import com.tpjad.project.photoalbumapi.utils.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoServiceImpl extends AbstractService implements IPhotoService {
    private final IAlbumRepository albumRepository;
    private final IPhotoRepository photoRepository;
    private final IAlbumPhotoRepository albumPhotoRepository;

    protected PhotoServiceImpl(PasswordEncoder passwordEncoder,
                               IUserRepository userRepository,
                               IUserDetailsRepository userDetailsRepository,
                               IAlbumRepository albumRepository,
                               IPhotoRepository photoRepository,
                               IAlbumPhotoRepository albumPhotoRepository) {
        super(passwordEncoder, userRepository, userDetailsRepository);
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.albumPhotoRepository = albumPhotoRepository;
    }

    @Transactional
    @Override
    public void create(Photo photo) {
        Optional<AlbumEntity> albumEntityOptional = albumRepository.findById(photo.getAlbumId());
        if (!albumEntityOptional.isPresent()) {
            throwNotFoundException("Cannot find the desired album!");
        }
        photo.setDate(DateUtils.getCurrentDate());
        PhotoEntity photoEntity = PhotoTransformer.transform(photo);
        AlbumPhotoEntity albumPhotoEntity = new AlbumPhotoEntity();
        albumPhotoEntity.setPhoto(photoEntity);
        albumPhotoEntity.setAlbum(albumEntityOptional.get());
        photoRepository.save(photoEntity);
        albumPhotoRepository.save(albumPhotoEntity);
    }

    @Override
    public void update(Photo photo) {
        Optional<PhotoEntity> photoEntityOptional = photoRepository.findById(photo.getId());
        if (!photoEntityOptional.isPresent()) {
            throwNotFoundException("Cannot find the photo to update!");
        }
        PhotoEntity photoToUpdate = photoEntityOptional.get();
        if (photo.getDate() != null) {
            photoToUpdate.setDate(photo.getDate());
        }
        if (photo.getImage() != null) {
            photoToUpdate.setImage(photo.getImage());
        }
        if (photo.getTitle() != null) {
            photoToUpdate.setTitle(photo.getTitle());
        }
        photoRepository.save(photoToUpdate);
    }

    @Override
    public void delete(Long id) {
        Optional<PhotoEntity> photoOptional = photoRepository.findById(id);
        if (!photoOptional.isPresent()) {
            throwNotFoundException("Cannot delete photo! Photo does not exists!");
        }
        photoRepository.delete(photoOptional.get());
    }

    @Override
    public List<Photo> findAllFromAlbum(Long idAlbum) {
        Optional<AlbumEntity> albumEntityOptional = albumRepository.findById(idAlbum);
        if (!albumEntityOptional.isPresent()) {
            throwNotFoundException("Cannot find the desired album!");
        }
        List<PhotoEntity> photos = albumPhotoRepository.findPhotosFromAlbum(idAlbum);
        return PhotoTransformer.transform(photos);
    }

    @Override
    public void deleteFromAlbum(Long idAlbum, Long idPhoto) {
        Optional<AlbumEntity> album = albumRepository.findById(idAlbum);
        Optional<PhotoEntity> photo = photoRepository.findById(idPhoto);
        if (!album.isPresent() || !photo.isPresent())  {
            throwNotFoundException("Cannot find the album or the photo!");
        }
        List<AlbumPhotoEntity> photos = albumPhotoRepository.findByAlbumIdAndPhotoId(idAlbum, idPhoto);
        albumPhotoRepository.deleteAll(photos);
    }
}
