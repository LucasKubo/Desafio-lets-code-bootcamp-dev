package com.letscodechallenge.restcontroller;

import com.google.gson.Gson;
import com.letscodechallenge.dto.*;
import com.letscodechallenge.security.component.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;


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
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryResponseDTO> comment(@RequestBody CommentaryRequestDTO commentaryRequestDTO){
		HttpHeaders headers = new HttpHeaders();
		commentaryRequestDTO.setUserId(userRequest.getId());
		String[] values = {"BASIC","ADVANCED","MODERATOR"};
		boolean contains = Arrays.stream(values).anyMatch(userRequest.getRole()::equals);
		ResponseEntity<CommentaryResponseDTO> response;
		if(contains){
			response = new RestTemplate()
					.postForEntity("http://localhost:8081/api/v1/movie/comment",
							new HttpEntity<CommentaryRequestDTO>(commentaryRequestDTO,headers),
							CommentaryResponseDTO.class);
		}else {
			response = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		return response;
	}
	
	@RequestMapping(value ="/rate", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<RatingResponseDTO> rate(@RequestBody RatingRequestDTO ratingRequestDTO){
		HttpHeaders headers = new HttpHeaders();
		ratingRequestDTO.setUserId(userRequest.getId());
		String[] values = {"READER","BASIC","ADVANCED","MODERATOR"};
		boolean contains = Arrays.stream(values).anyMatch(userRequest.getRole()::equals);
		ResponseEntity<RatingResponseDTO> response;
		if(contains) {
			response = new RestTemplate()
				.postForEntity("http://localhost:8081/api/v1/movie/rate", 
						new HttpEntity<RatingRequestDTO>(ratingRequestDTO,headers),
						RatingResponseDTO.class);
		}
		else {
			response =new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		return response;
	}

	@RequestMapping(value ="/comment/answer", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryAnswerResponseDTO> answerCommentary(@RequestBody CommentaryAnswerRequestDTO commentaryAnswerRequestDTO){
		HttpHeaders headers = new HttpHeaders();
		commentaryAnswerRequestDTO.setUserId(userRequest.getId());
		String[] values = {"BASIC","ADVANCED","MODERATOR"};
		boolean contains = Arrays.stream(values).anyMatch(userRequest.getRole()::equals);
		ResponseEntity<CommentaryAnswerResponseDTO> response;
		if(contains){
			response = new RestTemplate()
					.postForEntity("http://localhost:8081/api/v1/movie/comment/answer",
							new HttpEntity<CommentaryAnswerRequestDTO>(commentaryAnswerRequestDTO,headers),
							CommentaryAnswerResponseDTO.class);
		}else {
			response = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		return response;
	}

	@RequestMapping(value ="/comment/mention", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<CommentaryMentionResponseDTO> mentionCommentary(@RequestBody CommentaryMentionRequestDTO commentaryMentionRequestDTO){
		HttpHeaders headers = new HttpHeaders();
		commentaryMentionRequestDTO.setUserId(userRequest.getId());
		String[] values = {"ADVANCED","MODERATOR"};
		boolean contains = Arrays.stream(values).anyMatch(userRequest.getRole()::equals);
		ResponseEntity<CommentaryMentionResponseDTO> response;
		if(contains){
			response = new RestTemplate()
					.postForEntity("http://localhost:8081/api/v1/movie/comment/mention",
							new HttpEntity<CommentaryMentionRequestDTO>(commentaryMentionRequestDTO,headers),
							CommentaryMentionResponseDTO.class);
		}else {
			response = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		return response;
	}

	@RequestMapping(value ="/comment/react/{commentaryId}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<UpdateDTO> reactCommentary(@PathVariable Long commentaryId, @RequestBody CommentaryReactRequestDTO commentaryReactRequestDTO){
		HttpHeaders headers = new HttpHeaders();
		String[] values = {"ADVANCED","MODERATOR"};
		boolean contains = Arrays.stream(values).anyMatch(userRequest.getRole()::equals);
		ResponseEntity<UpdateDTO> response;
		if(contains){
			new RestTemplate()
					.put("http://localhost:8081/api/v1/movie/comment/react/"+commentaryId,
							new HttpEntity<CommentaryReactRequestDTO>(commentaryReactRequestDTO,headers),
							CommentaryMentionResponseDTO.class);
			response = new ResponseEntity<>(new UpdateDTO(true), HttpStatus.OK);
		}else {
			response = new ResponseEntity<>(new UpdateDTO(false), HttpStatus.FORBIDDEN);
		}
		return response;
	}

	@RequestMapping(value ="/comment/repeated/{commentaryId}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<UpdateDTO> markCommentaryAsRepeated (@PathVariable Long commentaryId, @RequestBody CommentaryRepeatedRequestDTO commentaryRepeatedRequestDTO){
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<UpdateDTO> response;
		if(userRequest.getRole().equals("MODERATOR")){
			new RestTemplate()
					.put("http://localhost:8081/api/v1/movie/comment/repeated/"+commentaryId,
							new HttpEntity<CommentaryRepeatedRequestDTO>(commentaryRepeatedRequestDTO,headers),
							CommentaryRepeatedRequestDTO.class);
			response = new ResponseEntity<>(new UpdateDTO(true), HttpStatus.OK);
		}else {
			response = new ResponseEntity<>(new UpdateDTO(false), HttpStatus.FORBIDDEN);
		}
		return response;
	}

	@RequestMapping(value="/comment/delete/{commentaryId}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<?> deleteCommentary (@PathVariable Long commentaryId){
		ResponseEntity<DeleteDTO> response;
		if(userRequest.getRole().equals("MODERATOR")){
			new RestTemplate()
					.delete("http://localhost:8081/api/v1/movie/comment/delete/"+commentaryId,
							CommentaryRepeatedRequestDTO.class);
			response = new ResponseEntity<>(new DeleteDTO(true), HttpStatus.OK);
		}else {
			response = new ResponseEntity<>(new DeleteDTO(false), HttpStatus.FORBIDDEN);
		}
		return response;
	}
}
