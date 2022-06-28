package com.letscodechallenge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Commentary {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "cmlike")
    private int like;

    private int deslike;

    private boolean repeated;

    @Column(name="movieid")
    private String movieId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movieid", updatable = false, insertable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", updatable = false, insertable = false)
    private Commentary answerdCommentary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answerdCommentary")
    private List<Commentary> commentaryMentions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", updatable = false, insertable = false)
    private Commentary mentionedCommentary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mentionedCommentary")
    private List<Commentary> commentaryAnswers;

}
