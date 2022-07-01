package com.letscodechallenge.service;

import com.letscodechallenge.dto.RatingRequestDTO;
import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.entity.User;
import com.letscodechallenge.repository.IRatingRepository;
import com.letscodechallenge.repository.IUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    IRatingRepository ratingRepository;
    @Autowired
    IUserRepository userRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    public Rating rate(RatingRequestDTO ratingRequestDTO){
        String movieTitle = ratingRequestDTO.getMovieTitle();
        movieService.insertMovieIfNotExistInDataBase(movieTitle);
        String movieId = movieService.findMovieByTitleApi(movieTitle).getImdbID();
        Rating rating = new Rating();
        BeanUtils.copyProperties(ratingRequestDTO,rating);
        rating.setMovieId(movieId);
        rating.setUser(userRepository.findUserById(ratingRequestDTO.getUserId()));
        userService.addScore(ratingRequestDTO.getUserId());
        ratingRepository.save(rating);
        return rating;
    }
}
