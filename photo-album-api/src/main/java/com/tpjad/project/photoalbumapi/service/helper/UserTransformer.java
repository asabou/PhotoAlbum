package com.tpjad.project.photoalbumapi.service.helper;

import com.tpjad.project.photoalbumapi.dao.entity.UserEntity;
import com.tpjad.project.photoalbumapi.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserTransformer {
    private static void fillUser(final UserEntity input, final User target) {
        target.setId(input.getId());
        target.setUsername(input.getUsername()); //passwords are not supposed to be sent to client
        target.setRoles(RoleTransformer.transformRoleEntities(input.getAuthorities()));
    }

    private static void fillUserEntity(final User input, final UserEntity target) {
        target.setId(input.getId());
        target.setUsername(input.getUsername());
        target.setPassword(input.getPassword()); //password when a user is created
        target.setAuthorities(RoleTransformer.transformRoles(input.getRoles()));
    }

    public static User transformUserEntity(final UserEntity input) {
        if (input == null) {
            return null;
        }
        final User target = new User();
        fillUser(input, target);
        return target;
    }

    public static UserEntity transformUser(final User input) {
        if (input == null) {
            return null;
        }
        final UserEntity target = new UserEntity();
        fillUserEntity(input, target);
        return target;
    }

    public static List<UserEntity> transformUsers(final List<User> inputs) {
        final List<UserEntity> targets = new ArrayList<>();
        if (inputs != null) {
            inputs.forEach((user) -> targets.add(transformUser(user)));
        }
        return targets;
    }

    public static List<User> transformUserEntities(final Iterable<UserEntity> entities) {
        final List<User> users = new ArrayList<>();
        if (entities != null) {
            entities.forEach((entity) -> users.add(transformUserEntity(entity)));
        }
        return users;
    }
}
