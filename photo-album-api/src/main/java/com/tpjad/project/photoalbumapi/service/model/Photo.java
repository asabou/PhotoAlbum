package com.tpjad.project.photoalbumapi.service.model;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Photo {
    private Long id;

    private Long albumId;

    private String title;

    private Date date;

    private byte[] image;
}
