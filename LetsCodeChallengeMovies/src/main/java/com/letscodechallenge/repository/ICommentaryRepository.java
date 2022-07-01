package com.letscodechallenge.repository;

import com.letscodechallenge.entity.Commentary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ICommentaryRepository extends CrudRepository<Commentary, Long> {
    List<Commentary> findByMovieId (String id);

    Commentary findCommentaryById(Long id);
}
