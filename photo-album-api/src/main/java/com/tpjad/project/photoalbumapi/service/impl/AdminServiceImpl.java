package com.tpjad.project.photoalbumapi.service.impl;

import com.google.common.collect.Sets;
import com.tpjad.project.photoalbumapi.dao.entity.*;
import com.tpjad.project.photoalbumapi.dao.repository.*;
import com.tpjad.project.photoalbumapi.service.abstracts.AbstractService;
import com.tpjad.project.photoalbumapi.service.abstracts.IAdminService;
import com.tpjad.project.photoalbumapi.service.helper.RoleTransformer;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl extends AbstractService implements IAdminService {
    private final IRoleRepository roleRepository;
    private final IAlbumRepository albumRepository;
    private final IPhotoRepository photoRepository;
    private final IAlbumPhotoRepository albumPhotoRepository;

    public AdminServiceImpl(IUserRepository userRepository,
                            IRoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            IUserDetailsRepository userDetailsRepository,
                            IAlbumRepository albumRepository,
                            IPhotoRepository photoRepository,
                            IAlbumPhotoRepository albumPhotoRepository) {
        super(passwordEncoder, userRepository, userDetailsRepository);
        this.roleRepository = roleRepository;
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.albumPhotoRepository = albumPhotoRepository;
    }

    @Override
    public void createAdmin(UserDetails userDetails) {
        RoleEntity admin = roleRepository.findByRole("ADMIN");
        RoleEntity user = roleRepository.findByRole("USER");
        if (admin == null || user == null) {
            throwInternalServerErrorException("Authorities for admin not found!");
        }
        userDetails.getUser().setRoles(Sets.newHashSet(RoleTransformer.transformRoleEntity(admin), RoleTransformer.transformRoleEntity(user)));
        super.createUser(userDetails);
    }

    @Override
    public void createUser(UserDetails userDetails) {
        RoleEntity user = roleRepository.findByRole("USER");
        if (user == null) {
            throwInternalServerErrorException("Authorities for admin not found!");
        }
        userDetails.getUser().setRoles(Sets.newHashSet(RoleTransformer.transformRoleEntity(user)));
        super.createUser(userDetails);
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throwNotFoundException("User not found!");
        }
        deleteAllDependentEntities(userOptional.get().getId());
        userRepository.delete(userOptional.get());
    }

    private void deleteAllDependentEntities(Long userId) {
        List<AlbumEntity> albums = albumRepository.findAlbumEntitiesByUserId(userId);
        albums.forEach(album -> {
            List<PhotoEntity> photos = albumPhotoRepository.findPhotosFromAlbum(album.getId());
            photoRepository.deleteAll(photos);
            albumRepository.delete(album);
            photos.forEach(photo -> {
                List<AlbumPhotoEntity> albumPhotoEntities = albumPhotoRepository.findByAlbumIdAndPhotoId(album.getId(), photo.getId());
                albumPhotoRepository.deleteAll(albumPhotoEntities);
            });
        });
    }
}
