package com.letscodechallenge.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tbuser")
public class User{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Length(max = 128)
	@Column(nullable = false)
	private String username;
	
	@Length(max = 255)
	@Column(nullable = false)
	private String password;

	private int score;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "role_user"))
	private Role role;

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Commentary> comments;
}
