package com.letscodechallenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority{
	
	public Role(Long id, @Length(max = 128) String name) {
		this.id = id;
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "sq_db", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Length(max = 128)
	@Column(nullable = false)
	private String name;

	public Role() {

	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}
