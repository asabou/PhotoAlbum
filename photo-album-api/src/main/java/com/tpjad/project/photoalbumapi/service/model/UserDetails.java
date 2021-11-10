package com.tpjad.project.photoalbumapi.service.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private User user;
}
