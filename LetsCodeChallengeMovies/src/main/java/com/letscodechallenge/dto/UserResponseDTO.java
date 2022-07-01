package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Role;
import com.letscodechallenge.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {

	private String username;
	private RoleResponseDTO role;

	public UserResponseDTO(User user) {
		this.username = user.getUsername();
		this.role = new RoleResponseDTO(user.getRole());
	}
}
