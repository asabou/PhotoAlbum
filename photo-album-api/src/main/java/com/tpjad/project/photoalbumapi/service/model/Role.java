package com.tpjad.project.photoalbumapi.service.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private Long id;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
