package com.letscodechallenge.restcontroller;

import com.letscodechallenge.dto.CommentaryMentionRequestDTO;
import com.letscodechallenge.dto.CommentaryMentionResponseDTO;
import com.letscodechallenge.security.component.UserRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.letscodechallenge.dto.UserRequestDTO;
import com.letscodechallenge.dto.UserResponseDTO;
import com.letscodechallenge.entity.User;
import com.letscodechallenge.service.UserService;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/public/api/v1/signup")
public class SignUpRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRequest userRequest;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserResponseDTO> signUp (@RequestBody UserRequestDTO userRequestDTO) {
		User user = new User();
		BeanUtils.copyProperties(userRequestDTO, user);
		user = userService.signUp(user);
		user.setScore(0);
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		BeanUtils.copyProperties(user, userResponseDTO);
		return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
	}
	
}