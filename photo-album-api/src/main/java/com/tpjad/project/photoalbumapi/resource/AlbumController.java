package com.tpjad.project.photoalbumapi.resource;

import com.tpjad.project.photoalbumapi.service.abstracts.IAlbumService;
import com.tpjad.project.photoalbumapi.service.model.Album;
import com.tpjad.project.photoalbumapi.utils.TokenUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final IAlbumService albumService;
    private final TokenUtils tokenUtils;

    public AlbumController(IAlbumService albumService,
                           TokenUtils tokenUtils) {
        this.albumService = albumService;
        this.tokenUtils = tokenUtils;
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @PostMapping("/create")
    void create(@RequestBody Album album, @RequestHeader(name = "authorization") String authorization) {
        Long userId = tokenUtils.getUserIdFromToken(authorization);
        albumService.create(album, userId);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @PutMapping("/update")
    void update(@RequestBody Album album) {
        albumService.update(album);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable("id") Long id) {
        albumService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @GetMapping("/all")
    List<Album> getAll(@RequestHeader(name = "authorization") String authorization) {
        Long userId = tokenUtils.getUserIdFromToken(authorization);
        return albumService.findAllForUser(userId);
    }

}
