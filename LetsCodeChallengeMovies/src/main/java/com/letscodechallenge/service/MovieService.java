package com.letscodechallenge.service;

import com.google.gson.Gson;
import com.letscodechallenge.dto.MovieResponseDTO;
import com.letscodechallenge.entity.Movie;
import com.letscodechallenge.repository.IMovieRepository;
import com.letscodechallenge.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMovieRepository movieRepository;

    public MovieResponseDTO findMovieById(String movieId){
        String apiKey = "c6847707";
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.omdbapi.com?i="+movieId+"&apikey="+ apiKey;
        ResponseEntity<String> responseJson = restTemplate.getForEntity(url,
                String.class);
        Gson gson =new Gson();
        MovieResponseDTO movieResponseDTO = gson.fromJson(responseJson.getBody(),MovieResponseDTO.class);
        return movieResponseDTO;
    }
    public void insertMovieIfNotExistInDB(String movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.equals(Optional.empty())){
            MovieResponseDTO movieApi = this.findMovieById(movieId);
            Movie newMovie = new Movie();
            newMovie.setId(movieApi.getImdbID());
            newMovie.setTitle(movieApi.getTitle());
            movieRepository.save(newMovie);
        }
    }
}
