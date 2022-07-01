package com.letscodechallenge.restcontroller;

import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.letscodechallenge.dto.CommentaryAnswerRequestDTO;
import com.letscodechallenge.dto.CommentaryAnswerResponseDTO;
import com.letscodechallenge.dto.CommentaryRequestDTO;
import com.letscodechallenge.dto.CommentaryResponseDTO;
import com.letscodechallenge.dto.MovieResponseDTO;
import com.letscodechallenge.dto.RatingRequestDTO;
import com.letscodechallenge.dto.RatingResponseDTO;
import com.letscodechallenge.entity.Commentary;
import com.letscodechallenge.entity.Rating;
import com.letscodechallenge.security.conponent.UserRequest;

@RestController
@RequestMapping("/restricted/api/v1/movie")
public class MovieRestController {

	@Autowired
	private UserRequest userRequest;

	@RequestMapping(value = "/{movieName}", method = RequestMethod.GET)
	public ResponseEntity<MovieResponseDTO> findMovie(@PathVariable String movieName) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseJson = restTemplate
				.getForEntity("http://localhost:8081/api/v1/movie/" + movieName, String.class);
		Gson gson = new Gson();
		MovieResponseDTO movieResponseDTO = gson.fromJson(responseJson.getBody(), MovieResponseDTO.class);
		return new ResponseEntity<>(movieResponseDTO, HttpStatus.OK);
		// restTemplate.exchange("http://localhost:8081/api/v1/movie",
		// HttpMethod.GET,{user:asd}, String.class);
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryResponseDTO> comment(@RequestBody CommentaryRequestDTO commentaryRequestDTO, UriComponentsBuilder uriBuilder){
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<CommentaryResponseDTO> response = new RestTemplate()
				.postForEntity("http://localhost:8081/api/v1/movie/comment", 
						new HttpEntity<CommentaryRequestDTO>(commentaryRequestDTO,headers),
						CommentaryResponseDTO.class);
		return response;
	}

	@RequestMapping(value ="/rate", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<RatingResponseDTO> rate(@RequestBody RatingRequestDTO ratingRequestDTO, UriComponentsBuilder uriBuilder){
		return null;
	}

	@RequestMapping(value ="/comment/answer", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryAnswerResponseDTO> answerCommentary(@RequestBody CommentaryAnswerRequestDTO commentaryAnswerRequestDTO, UriComponentsBuilder uriBuilder){
		return null;
	}
}
