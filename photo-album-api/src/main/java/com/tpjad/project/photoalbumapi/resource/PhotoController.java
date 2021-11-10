package com.tpjad.project.photoalbumapi.resource;

import com.tpjad.project.photoalbumapi.service.abstracts.IPhotoService;
import com.tpjad.project.photoalbumapi.service.model.Photo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final IPhotoService photoService;

    public PhotoController(IPhotoService photoService) {
        this.photoService = photoService;
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @PostMapping("/create")
    void create(@RequestBody Photo photo) {
        photoService.create(photo);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @PutMapping("/update")
    void update(@RequestBody Photo photo) {
        photoService.update(photo);
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable("id") Long id) {
        photoService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN, USER')")
    @DeleteMapping("/delete-from-album/{idAlbum}/{idPhoto}")
    void deleteFromAlbum(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idPhoto") Long idPhoto) {
        photoService.deleteFromAlbum(idAlbum, idPhoto);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @GetMapping("/all/{id}")
    List<Photo> getAll(@PathVariable("id") Long id) {
        return photoService.findAllFromAlbum(id);
    }
}
