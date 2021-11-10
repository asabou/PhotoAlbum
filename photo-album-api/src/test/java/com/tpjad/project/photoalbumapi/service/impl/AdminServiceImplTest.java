package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.*;
import com.tpjad.project.photoalbumapi.dao.repository.*;
import com.tpjad.project.photoalbumapi.exceptions.BadRequestException;
import com.tpjad.project.photoalbumapi.exceptions.InternalServerErrorException;
import com.tpjad.project.photoalbumapi.exceptions.NotFoundException;
import com.tpjad.project.photoalbumapi.service.impl.factory.AlbumFactory;
import com.tpjad.project.photoalbumapi.service.impl.factory.UserFactory;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AdminServiceImplTest extends AbstractTest {
    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

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

    private UserFactory userFactory;
    private AlbumFactory albumFactory;
    private UserEntity mockUser;
    private UserDetails mockUserDetails;
    private List<AlbumEntity> mockAlbumEntities;
    private List<PhotoEntity> mockPhotoEntities;
    private List<AlbumPhotoEntity> mockAlbumPhotoEntities;

    @BeforeEach
    void setUp() {
        adminService = new AdminServiceImpl(userRepository, roleRepository, passwordEncoder,
                userDetailsRepository, albumRepository, photoRepository, albumPhotoRepository);
        userFactory = new UserFactory();
        albumFactory = new AlbumFactory();
        mockUser = userFactory.createMockUserAdmin("alexandru");
        mockUserDetails = userFactory.createMockUserDetails("alex");
        mockAlbumEntities = Arrays.asList(albumFactory.createMockAlbumEntity("album"));
        mockPhotoEntities = Arrays.asList(albumFactory.createMockPhotoEntity("photo"));
        mockAlbumPhotoEntities = Arrays.asList(albumFactory.createMockPhotoAlbumEntity("photo_album"));
    }

    @Test
    void whenCreateAdminAndRolesNotFound_thenExceptionIsThrown() {
        when(roleRepository.findByRole(anyString())).thenReturn(null);
        try {
            adminService.createAdmin(mockUserDetails);
        } catch (Exception e) {
            assertTrue(e instanceof InternalServerErrorException);
            assertTrue(e.getMessage().contains("not found"));
        }
        verify(roleRepository, times(2)).findByRole(anyString());
    }

    @Test
    void whenCreateAdminAndRolesExistsAndUserAlreadyExists_thenExceptionIsThrown() {
        RoleEntity admin = userFactory.createMockRole("ADMIN");
        when(roleRepository.findByRole(anyString())).thenReturn(admin);
        when(userRepository.findUserByUsername(anyString())).thenReturn(mockUser);
        try {
            adminService.createAdmin(mockUserDetails);
        } catch (Exception e) {
            assertTrue(e instanceof BadRequestException);
            assertTrue(e.getMessage().contains("already exists"));
        }
        verify(roleRepository, times(2)).findByRole(anyString());
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }

    @Test
    void whenCreateAdminAndRolesExistsAndUserDoesNotExists_thenReturnNothing() {
        RoleEntity admin = userFactory.createMockRole("ADMIN");
        when(roleRepository.findByRole(anyString())).thenReturn(admin);
        when(userRepository.findUserByUsername(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(any(UserEntity.class))).thenReturn(null);
        when(userDetailsRepository.save(any(UserDetailsEntity.class))).thenReturn(null);
        adminService.createAdmin(mockUserDetails);
        verify(roleRepository, times(2)).findByRole(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userDetailsRepository, times(1)).save(any(UserDetailsEntity.class));
    }

    @Test
    void whenCreateUserAndRolesExistsAndUserDoesNotExists_thenReturnNothing() {
        RoleEntity user = userFactory.createMockRole("USER");
        when(roleRepository.findByRole(anyString())).thenReturn(user);
        when(userRepository.findUserByUsername(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(any(UserEntity.class))).thenReturn(null);
        when(userDetailsRepository.save(any(UserDetailsEntity.class))).thenReturn(null);
        adminService.createUser(mockUserDetails);
        verify(roleRepository, times(1)).findByRole(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userDetailsRepository, times(1)).save(any(UserDetailsEntity.class));
    }

    @Test
    void whenDeleteAccountAndUserDoesNotExists_thenExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            adminService.deleteAccount(1l);
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
            assertTrue(e.getMessage().contains("not found"));
        }
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenDeleteAccountAndUserExists_thenAllDependentEntitiesWillBeRemoved() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(albumRepository.findAlbumEntitiesByUserId(any())).thenReturn(mockAlbumEntities);
        when(albumPhotoRepository.findPhotosFromAlbum(anyLong())).thenReturn(mockPhotoEntities);
        doNothing().when(photoRepository).deleteAll(anyCollection());
        doNothing().when(albumRepository).delete(any(AlbumEntity.class));
        doNothing().when(albumPhotoRepository).deleteAll(anyCollection());
        doNothing().when(userRepository).delete(any(UserEntity.class));
        adminService.deleteAccount(1l);
        verify(userRepository, times(1)).findById(anyLong());
        verify(albumRepository, times(1)).findAlbumEntitiesByUserId(any());
        verify(photoRepository, times(1)).deleteAll(anyCollection());
    }
}