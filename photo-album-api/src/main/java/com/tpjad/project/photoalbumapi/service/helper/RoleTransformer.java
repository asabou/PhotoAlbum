package com.tpjad.project.photoalbumapi.service.helper;

import com.tpjad.project.photoalbumapi.dao.entity.RoleEntity;
import com.tpjad.project.photoalbumapi.service.model.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleTransformer {
    private static void fillRole(final RoleEntity input, final Role target) {
        target.setId(input.getId());
        target.setRole(input.getRole());
    }

    private static void fillRoleEntity(final Role input, final RoleEntity target) {
        target.setId(input.getId());
        target.setRole(input.getRole());
    }

    public static Role transformRoleEntity(final RoleEntity input) {
        if (input == null) {
            return null;
        }
        final Role target = new Role();
        fillRole(input, target);
        return target;
    }

    public static RoleEntity transformRole(final Role input) {
        if (input == null) {
            return null;
        }
        final RoleEntity target = new RoleEntity();
        fillRoleEntity(input, target);
        return target;
    }

    public static Set<RoleEntity> transformRoles(final Set<Role> inputs) {
        final Set<RoleEntity> targets = new HashSet<>();
        if (inputs != null) {
            inputs.forEach((role) -> targets.add(transformRole(role)));
        }
        return targets;
    }

    public static Set<Role> transformRoleEntities(final Set<RoleEntity> inputs) {
        final Set<Role> targets = new HashSet<>();
        if (inputs != null) {
            inputs.forEach((entity) -> targets.add(transformRoleEntity(entity)));
        }
        return targets;
    }

    public static List<Role> transformRoleEntities(final Iterable<RoleEntity> inputs) {
        final List<Role> targets = new ArrayList<>();
        if (inputs != null) {
            inputs.forEach((entity) -> targets.add(transformRoleEntity(entity)));
        }
        return targets;
    }
}

