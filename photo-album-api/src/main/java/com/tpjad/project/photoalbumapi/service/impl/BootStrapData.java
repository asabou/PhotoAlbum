package com.tpjad.project.photoalbumapi.service.impl;

import com.tpjad.project.photoalbumapi.dao.entity.RoleEntity;
import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.dao.repository.IAlbumRepository;
import com.tpjad.project.photoalbumapi.dao.repository.IRoleRepository;
import com.tpjad.project.photoalbumapi.dao.repository.IUserRepository;
import com.tpjad.project.photoalbumapi.service.abstracts.IAdminService;
import com.tpjad.project.photoalbumapi.service.abstracts.IAlbumService;
import com.tpjad.project.photoalbumapi.service.abstracts.IPhotoService;
import com.tpjad.project.photoalbumapi.service.model.Album;
import com.tpjad.project.photoalbumapi.service.model.Photo;
import com.tpjad.project.photoalbumapi.service.model.User;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
import com.tpjad.project.photoalbumapi.utils.Base64Utils;
import com.tpjad.project.photoalbumapi.utils.DateUtils;
import com.tpjad.project.photoalbumapi.utils.ImageBytesUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final IRoleRepository roleRepository;
    private final IAdminService adminService;
    private final IUserRepository userRepository;
    private final IAlbumService albumService;
    private final IPhotoService photoService;

    public BootStrapData(IRoleRepository roleRepository,
                         IAdminService adminService,
                         IUserRepository userRepository,
                         IAlbumService albumService, IPhotoService photoService) {
        this.roleRepository = roleRepository;
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.albumService = albumService;
        this.photoService = photoService;
    }

    @Override
    public void run(String... args) throws Exception {
        saveRoles();
        saveAdminAlex();
        saveUserIulia();
        save2AlbumsForIulia();
        save3PhotosForIulia();
    }

    private void saveRoles() {
        RoleEntity admin = new RoleEntity(), user = new RoleEntity();
        admin.setRole("ADMIN");
        user.setRole("USER");
        roleRepository.save(admin);
        roleRepository.save(user);
    }

    private void saveAdminAlex() {
        User user = new User();
        UserDetails userDetails = new UserDetails();

        user.setUsername("alex");
        user.setPassword(Base64Utils.encode("password"));

        userDetails.setFirstName("Alex");
        userDetails.setLastName("Sabou");
        userDetails.setUser(user);
        adminService.createAdmin(userDetails);
    }

    private void saveUserIulia() {
        User user = new User();
        UserDetails userDetails = new UserDetails();

        user.setUsername("iulia");
        user.setPassword(Base64Utils.encode("password"));

        userDetails.setFirstName("Iulia");
        userDetails.setLastName("Sabou");
        userDetails.setUser(user);

        adminService.createUser(userDetails);
    }

    private void save2AlbumsForIulia() {
        UserEntity userEntity = userRepository.findUserByUsername("iulia");
        Album album1 = new Album(), album2 = new Album();
        album1.setTitle("Stadion1");
        album2.setTitle("Stadion2");
        albumService.create(album1, userEntity.getId());
        albumService.create(album2, userEntity.getId());
    }

    private void save3PhotosForIulia() {
        UserEntity userEntity = userRepository.findUserByUsername("iulia");
        Photo photo1 = new Photo(), photo2 = new Photo(), photo3 = new Photo();
        photo1.setDate(DateUtils.getCurrentDate());
        photo1.setTitle("Cel mai tare stadion");
        photo1.setAlbumId(1l);
        photo1.setImage(new ImageBytesUtils("images/entplace1.jpg").extractBytes());

        photo2.setDate(DateUtils.getCurrentDate());
        photo2.setTitle("Cel mai tare stadion2");
        photo2.setAlbumId(1l);
        photo2.setImage(new ImageBytesUtils("images/entplace2.jpg").extractBytes());

        photo3.setDate(DateUtils.getCurrentDate());
        photo3.setTitle("Cel mai tare stadion3");
        photo3.setAlbumId(2l);
        photo3.setImage(new ImageBytesUtils("images/entplace3.jpg").extractBytes());

        photoService.create(photo1);
        photoService.create(photo2);
        photoService.create(photo3);
    }


}
