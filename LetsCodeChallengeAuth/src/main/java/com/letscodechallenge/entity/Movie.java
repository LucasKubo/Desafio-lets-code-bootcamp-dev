package com.letscodechallenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Movie {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String title;

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "movie")
    private List<Commentary> comments;
}
