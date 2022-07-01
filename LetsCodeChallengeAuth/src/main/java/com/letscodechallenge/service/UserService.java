package com.letscodechallenge.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.letscodechallenge.entity.User;
import com.letscodechallenge.repository.IUserRepository;
import com.letscodechallenge.security.LetsCodeAuthenticationEntryPoint;
import com.letscodechallenge.security.LoginAttemptService;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private LetsCodeAuthenticationEntryPoint authenticationEntryPoint;

	@Transactional
	public User signUp(User user) {
		// TODO Auto-generated method stub
		// Role role = new Role(1L,"READER");
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRoleId(1L);
		return userRepository.save(user);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			LockedException lockedException = new LockedException("Attempt limit exceeded");
            try {
				this.authenticationEntryPoint.commence(request, response, lockedException);
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
			throw lockedException;
		}
		try {
			final User user = userRepository.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("No user found with username: " + username);
			}
			return user;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}
