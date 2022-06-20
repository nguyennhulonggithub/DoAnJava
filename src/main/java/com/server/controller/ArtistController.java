package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.model.Artist;
import com.server.exception.ArtistException;
import com.server.exception.FileFormatException;
import com.server.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @PostMapping("/addArtist")
    public ResponseEntity addArtist(@RequestPart("artist") String artistString, @RequestPart("image") MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(artistService.addArtist(artistString, image));
        } catch (ArtistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (FileFormatException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/findArtistById/{id}")
    public ResponseEntity findArtistById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(artistService.findArtistById(id));
        } catch (ArtistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findAllArtists")
    public List<Artist> findArtists() {
        return artistService.findAllArtists();
    }

    @DeleteMapping("/deleteArtist/{id}")
    public ResponseEntity deleteArtist(@PathVariable String id) {
        try {
            artistService.deleteArtist(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete artist with id: " + id);
        } catch (ArtistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/updateArtist")
    public ResponseEntity updateArtist(@RequestPart("artist") String artistString,
                                       @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(artistService.updateArtist(artistString, image));
        } catch (IOException | FileFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (ArtistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
