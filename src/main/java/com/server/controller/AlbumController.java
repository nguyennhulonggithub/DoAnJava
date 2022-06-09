package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.entity.Album;
import com.server.exception.AlbumException;
import com.server.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @PostMapping("/addAlbum")
    public ResponseEntity addAlbum(@RequestPart("album") String albumString, @RequestPart("image") MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(albumService.addAlbum(albumString, image));
        } catch (AlbumException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/findAlbumById/{id}")
    public Album findAlbumById(@PathVariable String id) {
        return albumService.findAlbumById(id);
    }

    @GetMapping("/findAllAlbums")
    public List<Album> findAllAlbums() {
        return albumService.findAllAlbums();
    }

    @PutMapping("/updateAlbum")
    public ResponseEntity updateAlbum(@RequestPart("album") String albumString,
                                      @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(albumService.updateAlbum(albumString, image));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch(AlbumException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAlbum/{id}")
    public String deleteAlbum(@PathVariable String id) {
        return albumService.deleteAlbum(id);
    }
}