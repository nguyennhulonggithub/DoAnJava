package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.model.Artist;
import com.server.model.Song;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDto {
    private String id;
    private String name;
    private String image;
    private Artist artist;
    private int totalView;
    private List<SongDto> songs;
    private Date createdAt;
    private Date updatedAt;
}
