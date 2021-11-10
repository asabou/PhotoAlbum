package com.tpjad.project.photoalbumapi.service.helper;

import com.tpjad.project.photoalbumapi.dao.entity.AlbumEntity;
import com.tpjad.project.photoalbumapi.service.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumTransformer {
    public static AlbumEntity transform(Album input) {
        AlbumEntity target = new AlbumEntity();
        target.setTitle(input.getTitle());
        return target;
    }

    public static Album transform(AlbumEntity  input) {
        Album target = new Album();
        target.setTitle(input.getTitle());
        return target;
    }

    public static List<Album> transform(List<AlbumEntity> input) {
        List<Album> target = new ArrayList<>();
        input.forEach(el -> target.add(transform(el)));
        return target;
    }

}
