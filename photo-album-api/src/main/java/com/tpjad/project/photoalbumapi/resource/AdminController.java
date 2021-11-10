package com.tpjad.project.photoalbumapi.resource;

import com.tpjad.project.photoalbumapi.service.abstracts.IAdminService;
import com.tpjad.project.photoalbumapi.service.model.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/create-admin")
    void createAdmin(@RequestBody UserDetails userDetails) {
        adminService.createAdmin(userDetails);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @PostMapping("/create-user")
    void createUser(@RequestBody UserDetails userDetails) {
        adminService.createUser(userDetails);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @DeleteMapping("/delete-account/{id}")
    void deleteAccount(@PathVariable("id") Long id) {
        adminService.deleteAccount(id);
    }

}
