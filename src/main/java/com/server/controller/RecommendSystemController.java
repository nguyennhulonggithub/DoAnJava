package com.server.controller;

import com.server.model.result.SongResult;
import com.server.service.RecommendSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendSystemController {
    @Autowired
    private RecommendSystemService recommendSystemService;

    @PostMapping("/getRecommendedRecentlySong")
    public ResponseEntity getRecommendSongs(List<SongResult> songs) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8000";

        //set header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<SongResult>> entity = new HttpEntity<List<SongResult>>(songs, headers);
        System.out.println(entity);
        // send request and parse result
        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.POST, entity, String.class);
        System.out.println(response);
        return response;
    }

    @PostMapping("/getSongForPlaylistRecommend/{idPlaylist}")
    public ResponseEntity getSongForPlaylistRecommend(@PathVariable  String idPlaylist) {
        return getRecommendSongs(recommendSystemService.getSongForPlaylistRecommend(idPlaylist));
    }

    @PostMapping("/recommendUser/{idUser}")
    public ResponseEntity recommendUser(@PathVariable String idUser){
        return getRecommendSongs(recommendSystemService.recommendUser(idUser));
    }
}
