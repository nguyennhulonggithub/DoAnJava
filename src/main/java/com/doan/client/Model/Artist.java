package com.doan.client.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Artist {


    private String id;
    private String name;
    private String image;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
