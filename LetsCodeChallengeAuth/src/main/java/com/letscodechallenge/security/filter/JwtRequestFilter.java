package com.letscodechallenge.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.letscodechallenge.entity.User;
import com.letscodechallenge.security.component.UserRequest;
import com.letscodechallenge.service.UserService;
import com.letscodechallenge.utils.JwtTokenUtils;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	private UserRequest userRequest;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken=null;
		String username =null;
		if (!request.getRequestURI().contains("/signin") && !request.getRequestURI().contains("/public")
				&& !request.getRequestURI().contains("/login")) {
			final String requestTokenHeader = request.getHeader("Authorization");
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					username = jwtTokenUtils.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
					throw new ServletException("JWT Token has expired");
				}

			}else {
				if(SecurityContextHolder.getContext().getAuthentication() != null) {
					new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
					SecurityContextHolder.getContext().setAuthentication(null);
				}
			}
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
				// if token is valid configure Spring Security to manually set authentication
				if (jwtTokenUtils.validateTokenAndUser(jwtToken, userDetails)) {
					if(doRequestBefore((User) userDetails, request)){
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						// After setting the Authentication in the context, we specify
						// that the current user is authenticated. So it passes the
						// Spring Security Configurations successfully.
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
		}

		filterChain.doFilter(request, response);
	}
	
	public boolean doRequestBefore(User user, HttpServletRequest request) {
		userRequest.setId(user.getId());
		userRequest.setUsername(user.getUsername());
		userRequest.setRole(user.getRole().getAuthority());
		return true;
	}
}
