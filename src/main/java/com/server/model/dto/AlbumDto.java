package com.server.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.model.Artist;
import com.server.model.Song;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDto {
    private String id;
    private String name;
    private String image;
    private Artist artist;
    private List<SongDto> songs;
}
