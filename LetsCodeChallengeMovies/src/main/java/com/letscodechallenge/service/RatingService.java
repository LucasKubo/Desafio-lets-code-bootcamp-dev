package com.letscodechallenge.service;

import com.letscodechallenge.dto.RatingRequestDTO;
import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.repository.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    IRatingRepository ratingRepository;
    public Rating rate(RatingRequestDTO ratingRequestDTO){
        Rating rating = new Rating();
        rating.setMovieId(ratingRequestDTO.getMovieId());
        rating.setValue(ratingRequestDTO.getRating());
        ratingRepository.save(rating);
        return rating;
    }
}
