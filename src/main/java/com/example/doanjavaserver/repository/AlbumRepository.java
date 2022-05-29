package com.example.doanjavaserver.repository;


import com.example.doanjavaserver.entity.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album,String> {
}
