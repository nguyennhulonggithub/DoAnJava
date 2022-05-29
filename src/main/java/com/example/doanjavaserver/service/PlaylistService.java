package com.example.doanjavaserver.service;

import com.example.doanjavaserver.entity.Playlist;
import com.example.doanjavaserver.entity.Song;
import com.example.doanjavaserver.repository.PlaylistRepository;
import com.example.doanjavaserver.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    public Playlist addPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist findPlaylistById(String id) {
        return playlistRepository.findById(id).orElse(null);
    }

    public List<Playlist> findAllPlaylists() {
        return playlistRepository.findAll();
    }

    public String deletePlaylist(String id) {
        playlistRepository.deleteById(id);
        return ("Playlist has been deleted: " + id);
    }

    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist addSongToPlaylist(String idPlaylist, String idSong) {
        Playlist playlist = playlistRepository.findById(idPlaylist).get();
        System.out.println(playlist);
        Song song = songRepository.findById(idSong).get();
        System.out.println(song);
        return playlistRepository.save(playlist);
    }
}
