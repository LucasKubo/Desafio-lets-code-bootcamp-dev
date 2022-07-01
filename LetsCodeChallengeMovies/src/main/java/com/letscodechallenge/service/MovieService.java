package com.letscodechallenge.service;

import com.google.gson.Gson;
import com.letscodechallenge.dto.CommentaryResponseDTO;
import com.letscodechallenge.dto.MovieResponseDTO;
import com.letscodechallenge.dto.RatingResponseDTO;
import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.entity.Movie;
import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.repository.IMovieRepository;
import com.letscodechallenge.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMovieRepository movieRepository;

    public MovieResponseDTO findMovieByTitle(String movieTitle){
        //Looking for movie in omdb API
        MovieResponseDTO movieResponseDTO = findMovieByTitleApi(movieTitle);
        //Looking for movie in DB to get comments and ratings
        Movie movie = movieRepository.findMovieById(movieResponseDTO.getImdbID());
        parseMovieToDTO(movieResponseDTO, movie);
        return movieResponseDTO;
    }

    private void parseMovieToDTO(MovieResponseDTO movieResponseDTO, Movie movie) {
        if(movie != null){
            //Setting comments
            List<Commentary> comments = movie.getComments();
            if(comments != null){
                parseCommentsToDTO(movieResponseDTO, comments);
            }

            //Setting ratings
            List<Rating> ratings = movie.getRatings();
            if(ratings != null){
                parseRatingsToDTO(movieResponseDTO, ratings);
            }
        }
    }

    private void parseRatingsToDTO(MovieResponseDTO movieResponseDTO, List<Rating> ratings) {
        List<RatingResponseDTO> ratingsDTO = new ArrayList<>();
        ratingsDTO.addAll(ratings.stream().map(RatingResponseDTO::new).collect(Collectors.toList()));
        movieResponseDTO.setRatings(ratingsDTO);
    }

    private void parseCommentsToDTO(MovieResponseDTO movieResponseDTO, List<Commentary> comments) {
        List<CommentaryResponseDTO> commentsDTO = new ArrayList<>();
        commentsDTO.addAll(comments.stream().map(CommentaryResponseDTO::new).collect(Collectors.toList()));
        movieResponseDTO.setComments(commentsDTO);
    }

    public MovieResponseDTO findMovieByTitleApi(String movieTitle) {
        String apiKey = "c6847707";
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.omdbapi.com?t="+ movieTitle +"&apikey="+ apiKey;
        ResponseEntity<String> responseJson = restTemplate.getForEntity(url,
                String.class);
        Gson gson =new Gson();
        MovieResponseDTO movieResponseDTO = gson.fromJson(responseJson.getBody(),MovieResponseDTO.class);
        return movieResponseDTO;
    }

    public void insertMovieIfNotExistInDataBase(String movieTitle) {
        Movie movieInDataBase = movieRepository.findByTitle(movieTitle);
        if(movieInDataBase == null){
            MovieResponseDTO movieResponseDTO = this.findMovieByTitle(movieTitle);
            Movie newMovie = new Movie();
            newMovie.setId(movieResponseDTO.getImdbID());
            newMovie.setTitle(movieResponseDTO.getTitle());
            movieRepository.save(newMovie);
        }
    }
}
