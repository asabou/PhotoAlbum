package com.tpjad.project.photoalbumapi.service.helper;

import com.tpjad.project.photoalbumapi.dao.entity.PhotoEntity;
import com.tpjad.project.photoalbumapi.service.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoTransformer {
    public static PhotoEntity transform(Photo input) {
        PhotoEntity target = new PhotoEntity();
        target.setImage(input.getImage());
        target.setTitle(input.getTitle());
        target.setDate(input.getDate());
        return target;
    }

    public static Photo transform(PhotoEntity input) {
        Photo target = new Photo();
        target.setId(input.getId());
        target.setImage(input.getImage());
        target.setTitle(input.getTitle());
        target.setDate(input.getDate());
        return target;
    }

    public static List<Photo> transform(List<PhotoEntity> inputs) {
        List<Photo> target = new ArrayList<>();
        inputs.forEach(el -> target.add(transform(el)));
        return target;
    }
}
