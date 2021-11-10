package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumEntity;
import com.tpjad.project.photoalbumapi.dao.entity.AlbumPhotoEntity;
import com.tpjad.project.photoalbumapi.dao.entity.PhotoEntity;
import com.tpjad.project.photoalbumapi.dao.repository.*;
import com.tpjad.project.photoalbumapi.exceptions.NotFoundException;
import com.tpjad.project.photoalbumapi.service.impl.factory.AlbumFactory;
import com.tpjad.project.photoalbumapi.service.model.Album;
import com.tpjad.project.photoalbumapi.service.model.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class PhotoServiceImplTest extends AbstractTest {
    @InjectMocks
    private PhotoServiceImpl photoService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IUserDetailsRepository userDetailsRepository;

    @Mock
    private IAlbumRepository albumRepository;

    @Mock
    private IPhotoRepository photoRepository;

    @Mock
    private IAlbumPhotoRepository albumPhotoRepository;

    private AlbumFactory albumFactory;
    private AlbumEntity mockAlbumEntity;
    private Album mockAlbum;
    private Photo mockPhoto;
    private PhotoEntity mockPhotoEntity;
    private List<PhotoEntity> mockPhotoEntities;
    private List<AlbumPhotoEntity> mockAlbumPhotoEntities;

    @BeforeEach
    void setUp() {
        photoService = new PhotoServiceImpl(passwordEncoder, userRepository, userDetailsRepository, albumRepository, photoRepository, albumPhotoRepository);
        albumFactory = new AlbumFactory();
        mockAlbumEntity = albumFactory.createMockAlbumEntity("album");
        mockAlbum = albumFactory.createMockAlbum("album");
        mockPhoto = albumFactory.createMockPhoto("photo");
        mockPhotoEntity = albumFactory.createMockPhotoEntity("photo");
        mockPhotoEntities = Arrays.asList(albumFactory.createMockPhotoEntity("photo"));
        mockAlbumPhotoEntities = Arrays.asList(albumFactory.createMockPhotoAlbumEntity("photo"));
    }

    @Test
    void whenCreatePhotoAndAlbumDoesNotExists_thenExceptionIsThrown() {
        when(albumRepository.findById(any())).thenReturn(Optional.empty());
        try {
            photoService.create(mockPhoto);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
    }

    @Test
    void whenCreatePhotoAndAlbumExists_thenReturnNothing() {
        when(albumRepository.findById(any())).thenReturn(Optional.of(mockAlbumEntity));
        when(photoRepository.save(any(PhotoEntity.class))).thenReturn(null);
        when(albumPhotoRepository.save(any(AlbumPhotoEntity.class))).thenReturn(null);
        photoService.create(mockPhoto);
        verify(albumRepository, times(1)).findById(any());
        verify(photoRepository, times(1)).save(any(PhotoEntity.class));
        verify(albumPhotoRepository, times(1)).save(any(AlbumPhotoEntity.class));
    }

    @Test
    void whenUpdatePhotoAndPhotoExists_thenReturnNothing() {
        when(photoRepository.findById(any())).thenReturn(Optional.of(mockPhotoEntity));
        when(photoRepository.save(any(PhotoEntity.class))).thenReturn(null);
        photoService.update(mockPhoto);
        verify(photoRepository, times(1)).findById(any());
        verify(photoRepository, times(1)).save(any(PhotoEntity.class));
    }

    @Test
    void whenUpdatePhotoAndPhotoDoesNotExists_thenExceptionIsThrown() {
        when(photoRepository.findById(any())).thenReturn(Optional.empty());
        try {
            photoService.update(mockPhoto);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
    }

    @Test
    void whenPhotoDeleteAndPhotoDoesNotExists_thenExceptionIsThrown() {
        when(photoRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            photoService.delete(1l);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot delete"));
        }
        verify(photoRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenPhotoDeleteAndPhotoExists_thenReturnNothing() {
        when(photoRepository.findById(anyLong())).thenReturn(Optional.of(mockPhotoEntity));
        doNothing().when(photoRepository).delete(any(PhotoEntity.class));
        photoService.delete(1l);
        verify(photoRepository, times(1)).findById(anyLong());
        verify(photoRepository, times(1)).delete(any(PhotoEntity.class));
    }

    @Test
    void whenFindAllFromAlbumAndAlbumDoesNotExists_thenExceptionIsThrown() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            photoService.findAllFromAlbum(1l);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
        verify(albumRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenFindAllFromAlbumAndAlbumExists_thenReturnPhotos() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.of(mockAlbumEntity));
        when(albumPhotoRepository.findPhotosFromAlbum(anyLong())).thenReturn(mockPhotoEntities);
        List<Photo> photos = photoService.findAllFromAlbum(1l);
        assertNotNull(photos);
        assertEquals(1, photos.size());
        verify(albumRepository, times(1)).findById(anyLong());
        verify(albumPhotoRepository, times(1)).findPhotosFromAlbum(anyLong());
    }

    @Test
    void whenDeleteFromAlbumAndAlbumOrPhotoDoesNotExists_thenExceptionIsThrown() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(photoRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            photoService.deleteFromAlbum(1l,1l);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
        verify(albumRepository, times(1)).findById(anyLong());
        verify(photoRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenDeleteFromAlbumAndAlbumExistsAndPhotoExists_thenReturnNothing() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.of(mockAlbumEntity));
        when(photoRepository.findById(anyLong())).thenReturn(Optional.of(mockPhotoEntity));
        when(albumPhotoRepository.findByAlbumIdAndPhotoId(anyLong(), anyLong())).thenReturn(mockAlbumPhotoEntities);
        doNothing().when(albumPhotoRepository).deleteAll(anyList());
        photoService.deleteFromAlbum(1l, 1l);
        verify(albumPhotoRepository, times(1)).deleteAll(anyList());
        verify(albumPhotoRepository, times(1)).findByAlbumIdAndPhotoId(1l,1l);
    }
}