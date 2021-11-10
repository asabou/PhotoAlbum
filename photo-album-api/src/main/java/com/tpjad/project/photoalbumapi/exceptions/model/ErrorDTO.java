package com.tpjad.project.photoalbumapi.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ErrorDTO {
    public String message;
}
