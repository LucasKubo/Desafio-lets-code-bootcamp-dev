package com.letscodechallenge.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tbuser")
@Getter
@Setter
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(generator = "sq_db", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Length(max = 128)
	@Column(nullable = false)
	private String username;
	
	@Length(max = 255)
	@Column(nullable = false)
	private String password;
	
	@Column(name = "roleid")
	private Long roleId;
	
	@JoinColumn(name = "roleid", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;

	private int score;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
