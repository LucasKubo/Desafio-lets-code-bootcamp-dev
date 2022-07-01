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

    @Column(name="userid")
    private Long userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userid", updatable = false, insertable = false)
    private User user;

    @Column(name="answeredcommentaryid")
    private Long answeredCommentaryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="answeredcommentaryid",referencedColumnName = "id", updatable = false, insertable = false)
    private Commentary answeredCommentary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answeredCommentary")
    private List<Commentary> answers;

    @Column(name="mentionedcommentaryid")
    private Long mentionedCommentaryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mentionedcommentaryid", referencedColumnName = "id", updatable = false, insertable = false)
    private Commentary mentionedCommentary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mentionedCommentary")
    private List<Commentary> mentions;
}
