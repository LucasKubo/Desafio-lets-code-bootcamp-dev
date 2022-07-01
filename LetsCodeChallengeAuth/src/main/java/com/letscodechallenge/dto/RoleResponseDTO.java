package com.letscodechallenge.dto;

import com.letscodechallenge.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RoleResponseDTO {

	private String name;

	public RoleResponseDTO(Role role) {
		this.name = role.getName();
	}
}
