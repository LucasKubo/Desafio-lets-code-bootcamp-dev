package com.letscodechallenge.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
	
	@Size(min = 13, max = 13,message = "")
	@NotEmpty(message = "Username can't be empty")
	private String username;
	
	@Size(min = 8, max = 8,message = "")
	@NotEmpty(message = "Password can't be empty")
	private String password;

}
