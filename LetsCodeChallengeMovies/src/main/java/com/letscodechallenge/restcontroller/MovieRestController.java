package com.letscodechallenge.restcontroller;

import com.letscodechallenge.dto.*;
import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.service.CommentaryService;
import com.letscodechallenge.service.MovieService;
import com.letscodechallenge.service.RatingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieRestController {

	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentaryService commentaryService;

	@Autowired
	private RatingService ratingService;

	@RequestMapping(value ="/{movieId}", method = RequestMethod.GET)
	public ResponseEntity<MovieResponseDTO> findMovie(@PathVariable String movieId){
		MovieResponseDTO movieResponseDTO = movieService.findMovieById(movieId);
		return new ResponseEntity<>(movieResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(value="/comments/{movieId}", method = RequestMethod.GET)
	public List<CommentaryResponseDTO> findMovieComments(@PathVariable String movieId){
		List<Commentary> comments = commentaryService.getCommentsByMovieId(movieId);
		List<CommentaryResponseDTO> commentsDTO = new ArrayList<>();
		commentsDTO.addAll(comments.stream().map(CommentaryResponseDTO::new).collect(Collectors.toList()));
		return commentsDTO;
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryResponseDTO> comment(@RequestBody CommentaryRequestDTO commentaryRequestDTO, UriComponentsBuilder uriBuilder){
		//Rever onde colocar isso
		String movieID = commentaryRequestDTO.getMovieId();
		movieService.insertMovieIfNotExistInDB(movieID);
		Commentary commentary = commentaryService.createCommentary(commentaryRequestDTO);
		CommentaryResponseDTO commentaryResponseDTO = new CommentaryResponseDTO();
		BeanUtils.copyProperties(commentary,commentaryResponseDTO);
		URI uri = uriBuilder.path("/comment/{movieId}").buildAndExpand(commentary.getId()).toUri();
		return ResponseEntity.created(uri).body(commentaryResponseDTO);
	}

	@RequestMapping(value ="/rate", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<RatingResponseDTO> rate(@RequestBody RatingRequestDTO ratingRequestDTO, UriComponentsBuilder uriBuilder){
		String movieID = ratingRequestDTO.getMovieId();
		movieService.insertMovieIfNotExistInDB(movieID);
		Rating rating = ratingService.rate(ratingRequestDTO);
		RatingResponseDTO ratingResponseDTO = new RatingResponseDTO(rating,movieService);
		URI uri = uriBuilder.path("/rate/{movieId}").buildAndExpand(rating.getId()).toUri();
		return ResponseEntity.created(uri).body(ratingResponseDTO);
	}
}
