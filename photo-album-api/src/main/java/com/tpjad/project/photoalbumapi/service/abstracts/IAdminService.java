package com.tpjad.project.photoalbumapi.service.abstracts;

import com.tpjad.project.photoalbumapi.service.model.UserDetails;

public interface IAdminService {
    void createAdmin(UserDetails userDetails);
    void createUser(UserDetails userDetails);
    void deleteAccount(Long id);
}
