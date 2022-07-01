package com.letscodechallenge.restcontroller;

import com.letscodechallenge.dto.*;
import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.service.CommentaryService;
import com.letscodechallenge.service.MovieService;
import com.letscodechallenge.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieRestController {

	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentaryService commentaryService;

	@Autowired
	private RatingService ratingService;

	@RequestMapping(value ="/{movieTitle}", method = RequestMethod.GET)
	public ResponseEntity<MovieResponseDTO> findMovie(@PathVariable String movieTitle){
		MovieResponseDTO movieResponseDTO = movieService.findMovieByTitle(movieTitle);
		return new ResponseEntity<>(movieResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryResponseDTO> comment(@RequestBody CommentaryRequestDTO commentaryRequestDTO, UriComponentsBuilder uriBuilder){
		Commentary commentary = commentaryService.createCommentary(commentaryRequestDTO);
		CommentaryResponseDTO commentaryResponseDTO = new CommentaryResponseDTO(commentary);
		URI uri = uriBuilder.path("/comment/{commentaryId}").buildAndExpand(commentary.getId()).toUri();
		return ResponseEntity.created(uri).body(commentaryResponseDTO);
	}

	@RequestMapping(value ="/rate", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<RatingResponseDTO> rate(@RequestBody RatingRequestDTO ratingRequestDTO, UriComponentsBuilder uriBuilder){
		Rating rating = ratingService.rate(ratingRequestDTO);
		RatingResponseDTO ratingResponseDTO = new RatingResponseDTO(rating);
		URI uri = uriBuilder.path("/rate/{ratingId}").buildAndExpand(rating.getId()).toUri();
		return ResponseEntity.created(uri).body(ratingResponseDTO);
	}

	@RequestMapping(value ="/comment/answer", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryAnswerResponseDTO> answerCommentary(@RequestBody CommentaryAnswerRequestDTO commentaryAnswerRequestDTO, UriComponentsBuilder uriBuilder){
		Commentary commentaryAnswer = commentaryService.answerCommentary(commentaryAnswerRequestDTO);
		CommentaryAnswerResponseDTO commentaryAnswerResponseDTO = new CommentaryAnswerResponseDTO(commentaryAnswer);
		URI uri = uriBuilder.path("/comment/{answerId}").buildAndExpand(commentaryAnswer.getId()).toUri();
		return ResponseEntity.created(uri).body(commentaryAnswerResponseDTO);
	}

	@RequestMapping(value ="/comment/mention", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryMentionResponseDTO> answerCommentary(@RequestBody CommentaryMentionRequestDTO commentaryMentionRequestDTO, UriComponentsBuilder uriBuilder){
		Commentary commentaryMention = commentaryService.metionCommentary(commentaryMentionRequestDTO);
		CommentaryMentionResponseDTO commentaryMentionResponseDTO = new CommentaryMentionResponseDTO(commentaryMention);
		URI uri = uriBuilder.path("/comment/{mentionId}").buildAndExpand(commentaryMention.getId()).toUri();
		return ResponseEntity.created(uri).body(commentaryMentionResponseDTO);
	}

	@RequestMapping(value ="/comment/{commentaryId}/react", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<CommentaryResponseDTO> reactCommentary(@PathVariable Long commentaryId, @RequestBody CommentaryReactRequestDTO commentaryReactRequestDTO, UriComponentsBuilder uriBuilder){
		Commentary commentaryReaction = commentaryService.reactCommentary(commentaryId,commentaryReactRequestDTO);
		CommentaryResponseDTO commentaryResponseDTO = new CommentaryResponseDTO(commentaryReaction);
		return ResponseEntity.ok(commentaryResponseDTO);
	}
}
