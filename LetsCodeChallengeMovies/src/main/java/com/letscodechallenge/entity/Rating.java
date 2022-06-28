package com.letscodechallenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

    @Column(name="movieid")
    private String movieId;
    @ManyToOne
    private Movie movie;
}
