package com.letscodechallenge.security.filter;

import com.google.gson.Gson;
import com.letscodechallenge.dto.UserResponseDTO;
import com.letscodechallenge.entity.User;
import com.letscodechallenge.service.UserService;
import com.letscodechallenge.utils.JwtTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class LetsCodeJwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	private JwtTokenUtils jwtTokenUtil;
	private UserService userService;

	public LetsCodeJwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtil, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService=userService;
		setFilterProcessesUrl("/api/v1/login");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			StringBuffer jb = new StringBuffer();
			BufferedReader reader = request.getReader();
			String line = null;

			while ((line = reader.readLine()) != null)
				jb.append(line);
			line = null;

			JSONObject jsonObject = new JSONObject(jb.toString());
			jb = null;

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					jsonObject.getString("username"), jsonObject.getString("password"));
			jsonObject = null;

			return authenticationManager.authenticate(authenticationToken);
		} catch (Exception e) {
			throw new BadCredentialsException("Error parsing JSON request string");
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) throws AuthenticationException {
		try {
			Gson gson =new Gson();
			User user = (User) authentication.getPrincipal();
			//nearly three month 
			String token = jwtTokenUtil.generateToken(user, 7889400 );
			UserResponseDTO userResponseDTO = new UserResponseDTO();
			BeanUtils.copyProperties(user, userResponseDTO);
			userResponseDTO.setToken(token);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(gson.toJson(userResponseDTO));
			out.flush();
		} catch (Exception e) {
			throw new BadCredentialsException("Error parsing JSON request string");
		}
	}

}
