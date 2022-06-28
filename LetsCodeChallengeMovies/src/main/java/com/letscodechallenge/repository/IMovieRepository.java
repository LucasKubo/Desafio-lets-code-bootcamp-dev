package com.letscodechallenge.repository;

import com.letscodechallenge.dto.MovieResponseDTO;
import com.letscodechallenge.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, String> {
    public Movie findByTitle (String title);
}
