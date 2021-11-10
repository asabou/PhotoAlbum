package com.tpjad.project.photoalbumapi.service.impl.factory;

import com.tpjad.project.photoalbumapi.dao.entity.RoleEntity;
import com.tpjad.project.photoalbumapi.dao.entity.UserDetailsEntity;
import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.service.model.User;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
import com.tpjad.project.photoalbumapi.utils.Base64Utils;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.List;

public class UserFactory {
    private UserEntity createSimpleMockUser(final String username) {
        final UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("password");
        return user;
    }

    public UserEntity createMockUserAdmin(final String username) {
        final UserEntity user = createSimpleMockUser(username);
        user.setAuthorities(Sets.newHashSet(createMockAdminRoles()));
        return user;
    }

    public RoleEntity createMockRole(final String role) {
        final RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        return roleEntity;
    }

    public List<RoleEntity> createMockAdminRoles() {
        return Arrays.asList(createMockRole("ADMIN"), createMockRole("USER"));
    }

    public UserDetailsEntity createSimpleMockUserDetailsEntity(final String name) {
        final UserDetailsEntity entity = new UserDetailsEntity();
        entity.setFirstName(name);
        entity.setLastName(name);
        return entity;
    }

    public User createMockUser(final String name) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(Base64Utils.encode(name));
        return user;
    }

    public UserDetails createMockUserDetails(final String name) {
        UserDetails user = new UserDetails();
        user.setFirstName(name);
        user.setLastName(name);
        user.setUser(createMockUser(name));
        return user;
    }

    public List<UserEntity> createSimpleMockUserEntities() {
        return Arrays.asList(createSimpleMockUser("alex"));
    }

    public List<UserDetailsEntity> createSimpleMockUserDetailsEntities() {
        return Arrays.asList(createSimpleMockUserDetailsEntity("alex"));
    }

    public List<RoleEntity> createMockUserRoles() {
        return Arrays.asList(createMockRole("USER"));
    }
}
