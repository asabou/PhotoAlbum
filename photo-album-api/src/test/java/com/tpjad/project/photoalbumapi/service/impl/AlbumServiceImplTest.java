package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.*;
import com.tpjad.project.photoalbumapi.dao.repository.*;
import com.tpjad.project.photoalbumapi.exceptions.BadRequestException;
import com.tpjad.project.photoalbumapi.exceptions.NotFoundException;
import com.tpjad.project.photoalbumapi.service.impl.factory.AlbumFactory;
import com.tpjad.project.photoalbumapi.service.impl.factory.UserFactory;
import com.tpjad.project.photoalbumapi.service.model.Album;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AlbumServiceImplTest extends AbstractTest {
    @InjectMocks
    private AlbumServiceImpl albumService;
    @Mock
    private IAlbumRepository albumRepository;
    @Mock
    private IAlbumPhotoRepository albumPhotoRepository;
    @Mock
    private IPhotoRepository photoRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IUserDetailsRepository userDetailsRepository;

    private AlbumFactory albumFactory;
    private UserFactory userFactory;
    private Album mockAlbum;
    private AlbumEntity mockAlbumEntity;
    private UserDetailsEntity mockUserDetailsEntity;
    private List<PhotoEntity> mockPhotoEntities;
    private List<AlbumPhotoEntity> mockAlbumPhotoEntities;
    private List<UserEntity> mockUserEntities;
    private UserEntity mockUserEntity;
    private List<AlbumEntity> mockAlbumEntities;

    @BeforeEach
    void setUp() {
        albumService = new AlbumServiceImpl(passwordEncoder,
                userRepository,
                userDetailsRepository,
                albumRepository,
                albumPhotoRepository,
                photoRepository);
        userFactory = new UserFactory();
        albumFactory = new AlbumFactory();
        mockAlbum = albumFactory.createMockAlbum("album");
        mockUserDetailsEntity = userFactory.createSimpleMockUserDetailsEntity("name");
        mockAlbumEntity = albumFactory.createMockAlbumEntity("album");
        mockPhotoEntities = Arrays.asList(albumFactory.createMockPhotoEntity("album"));
        mockAlbumPhotoEntities = Arrays.asList(albumFactory.createMockPhotoAlbumEntity("album"));
        mockUserEntity = userFactory.createMockUserAdmin("user");
        mockAlbumEntities = Arrays.asList(albumFactory.createMockAlbumEntity("album"));
    }

    @Test
    void whenCreateAlbumAndUserDoesNotExists_thenExceptionIsThrown() {
        when(userDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            albumService.create(mockAlbum, 1l);
        } catch (Exception e) {
            assertTrue(e instanceof BadRequestException);
            assertTrue(e.getMessage().contains("Cannot create"));
        }
        verify(userDetailsRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenCreateAlbumAndUserExists_thenReturnNothing() {
        when(userDetailsRepository.findById(anyLong())).thenReturn(Optional.of(mockUserDetailsEntity));
        when(albumRepository.save(any(AlbumEntity.class))).thenReturn(null);
        albumService.create(mockAlbum, 1l);
        verify(userDetailsRepository, times(1)).findById(anyLong());
        verify(albumRepository, times(1)).save(any(AlbumEntity.class));
    }

    @Test
    void whenUpdateAlbumAndAlbumDoesNotExists_thenExceptionIsThrown() {
        when(albumRepository.findById(any())).thenReturn(Optional.empty());
        try {
            albumService.update(mockAlbum);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
        verify(albumRepository, times(1)).findById(any());
    }

    @Test
    void whenUpdateAlbumAndAlbumExists_thenReturnNothing() {
        when(albumRepository.findById(any())).thenReturn(Optional.of(mockAlbumEntity));
        when(albumRepository.save(any(AlbumEntity.class))).thenReturn(null);
        albumService.update(mockAlbum);
        verify(albumRepository, times(1)).findById(any());
        verify(albumRepository, times(1)).save(any(AlbumEntity.class));
    }

    @Test
    void whenDeleteAlbumAndAlbumDoesNotExists_thenExceptionIsThrown() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            albumService.delete(1l);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
        verify(albumRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenDeleteAlbumAndAlbumExists_thenReturnNothing() {
        when(albumRepository.findById(anyLong())).thenReturn(Optional.of(mockAlbumEntity));
        when(albumPhotoRepository.findPhotosFromAlbum(any())).thenReturn(mockPhotoEntities);
        doNothing().when(photoRepository).deleteAll(anyList());
        when(albumPhotoRepository.findByAlbumIdAndPhotoId(any(), any())).thenReturn(mockAlbumPhotoEntities);
        doNothing().when(albumPhotoRepository).deleteAll(anyList());
        doNothing().when(albumRepository).delete(any(AlbumEntity.class));
        albumService.delete(1l);
        verify(albumRepository, times(1)).findById(anyLong());
        verify(albumRepository, times(1)).delete(any(AlbumEntity.class));
        verify(albumPhotoRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void whenFindAllForUserAndUserDoesNotExists_thenExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            albumService.findAllForUser(1l);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("Cannot find"));
        }
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenFindAllForUserAndUserExists_thenReturnAlbums() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserEntity));
        when(albumRepository.findAlbumEntitiesByUserId(anyLong())).thenReturn(mockAlbumEntities);
        albumService.findAllForUser(1l);
        verify(userRepository, times(1)).findById(anyLong());
        verify(albumRepository, times(1)).findAlbumEntitiesByUserId(anyLong());
    }
}