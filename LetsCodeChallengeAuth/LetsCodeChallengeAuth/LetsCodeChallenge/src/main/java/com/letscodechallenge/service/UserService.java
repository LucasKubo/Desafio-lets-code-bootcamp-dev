package com.letscodechallenge.service;

import com.letscodechallenge.entity.User;
import com.letscodechallenge.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private IUserRepository userRepository;
	
	@Transactional
	public User signUp(User user) {
		// TODO Auto-generated method stub
		//Role role = new Role(1L,"READER");
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRoleId(1L);
		return userRepository.save(user);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}
	
}
