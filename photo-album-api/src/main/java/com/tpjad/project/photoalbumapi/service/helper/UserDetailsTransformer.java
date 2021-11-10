package com.tpjad.project.photoalbumapi.service.helper;

import com.tpjad.project.photoalbumapi.dao.entity.UserDetailsEntity;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;

public class UserDetailsTransformer {
    public static UserDetailsEntity transform(UserDetails input) {
        UserDetailsEntity target = new UserDetailsEntity();
        target.setId(input.getId());
        target.setUser(UserTransformer.transformUser(input.getUser()));
        target.setFirstName(input.getFirstName());
        target.setLastName(input.getLastName());
        return target;
    }
    public static UserDetails transform(UserDetailsEntity input) {
        UserDetails target = new UserDetails();
        target.setId(input.getId());
        target.setUser(UserTransformer.transformUserEntity(input.getUser()));
        target.setFirstName(input.getFirstName());
        target.setLastName(input.getLastName());
        return target;
    }
}
