package com.letscodechallenge.repository;

import com.letscodechallenge.entity.Commentary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICommentaryRepository extends CrudRepository<Commentary, Long> {
    List<Commentary> findByMovieId (String movieId);
}
