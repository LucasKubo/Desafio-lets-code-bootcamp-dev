package com.letscodechallenge.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.letscodechallenge.dto.MovieResponseDTO;
import com.letscodechallenge.security.conponent.UserRequest;

@RestController
@RequestMapping("/restricted/api/v1/movie")
public class MovieRestController {

	@Autowired
	private UserRequest userRequest;

	@RequestMapping(value = "/{movieName}", method = RequestMethod.GET)
	public ResponseEntity<MovieResponseDTO> findMovie(@PathVariable String movieName) {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(userRequest.getRole());
		ResponseEntity<String> responseJson = restTemplate
				.getForEntity("http://localhost:8081/api/v1/movie/" + movieName, String.class);
		Gson gson = new Gson();
		MovieResponseDTO movieResponseDTO = gson.fromJson(responseJson.getBody(), MovieResponseDTO.class);
		return new ResponseEntity<>(movieResponseDTO, HttpStatus.OK);
		// restTemplate.exchange("http://localhost:8081/api/v1/movie",
		// HttpMethod.GET,{user:asd}, String.class);
	}
}
