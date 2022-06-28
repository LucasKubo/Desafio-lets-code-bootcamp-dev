package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.service.MovieService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class RatingResponseDTO {
    private int value;
    private String movieTitle;
    private String movieId;

    public RatingResponseDTO(Rating rating, MovieService movieService) {
        this.value = rating.getValue();
        this.movieTitle = movieService.findMovieById(rating.getMovieId()).getTitle();
        this.movieId = rating.getMovieId();
    }
}
