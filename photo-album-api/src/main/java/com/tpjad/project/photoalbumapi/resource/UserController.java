package com.tpjad.project.photoalbumapi.resource;

import com.tpjad.project.photoalbumapi.service.abstracts.IAdminService;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IAdminService adminService;

    public UserController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/register")
    void register(@RequestBody UserDetails userDetails) {
        adminService.createUser(userDetails);
    }
}
