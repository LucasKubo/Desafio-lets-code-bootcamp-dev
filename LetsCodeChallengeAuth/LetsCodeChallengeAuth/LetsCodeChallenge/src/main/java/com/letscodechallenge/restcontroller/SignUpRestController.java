package com.letscodechallenge.restcontroller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letscodechallenge.dto.UserRequestDTO;
import com.letscodechallenge.dto.UserResponseDTO;
import com.letscodechallenge.entity.User;
import com.letscodechallenge.service.UserService;

@RestController
@RequestMapping("/public/api/v1/signup")
public class SignUpRestController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserResponseDTO> signUp (@RequestBody UserRequestDTO userRequestDTO) {
		User user = new User();
		BeanUtils.copyProperties(userRequestDTO, user);
		user = userService.signUp(user);
		user.setScore(0);
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		BeanUtils.copyProperties(user, userResponseDTO);
//		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
//		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
	}
	
}