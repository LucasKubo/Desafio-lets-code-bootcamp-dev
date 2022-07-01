package com.letscodechallenge.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movieid", updatable = false, insertable = false)
    private Movie movie;

    @Column(name="userid")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userid", updatable = false, insertable = false)
    private User user;

}
