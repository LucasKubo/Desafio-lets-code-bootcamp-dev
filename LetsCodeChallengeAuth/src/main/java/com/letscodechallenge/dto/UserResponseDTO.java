package com.letscodechallenge.dto;

import com.letscodechallenge.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {

	private Long id;
	private String username;
	private RoleResponseDTO role;

	private String token;

	public UserResponseDTO(User user) {
		this.username = user.getUsername();
		this.role = new RoleResponseDTO(user.getRole());
	}
}
