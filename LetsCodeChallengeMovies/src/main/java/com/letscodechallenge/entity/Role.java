package com.letscodechallenge.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
public class Role {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Length(max = 128)
	@Column(nullable = false)
	private String name;

}
