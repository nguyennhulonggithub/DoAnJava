package com.server.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.entity.object.SongOtd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String id;

    @NotNull(message = "Playlist name cannot be null")
    private String name;

    private String image;
    private String idUser;

    private List<Song>songs = new ArrayList<Song>();

    private int totalView;
    private Date createdAt;
    private Date updatedAt;
}
