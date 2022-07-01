package com.letscodechallenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.letscodechallenge.security.filter.JwtRequestFilter;
import com.letscodechallenge.security.filter.LetsCodeJwtAuthenticationFilter;
import com.letscodechallenge.service.UserService;
import com.letscodechallenge.utils.JwtTokenUtils;

@Configuration //Bean de config
@EnableWebSecurity //Habilitar segurança no projeto
public class SecurityConfigurationImpl extends WebSecurityConfigurerAdapter{
	
//	@Autowired
//	private LetsCodeAuthenticationEntryPoint letsCodeAuthenticationEntryPoint ;

	@Autowired
    private  UserService userService;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/restricted/api/v1/movie").hasAnyRole("READER","BASIC","ADVANCED","MODERATOR")
        .antMatchers("/public/**").permitAll()
        .antMatchers("/webjars/**").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/v2/api-docs").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .anyRequest().authenticated()
//        .and()
//        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
		.and()
		.addFilter(new LetsCodeJwtAuthenticationFilter(authenticationManager(),jwtTokenUtils,userService))
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
//	private AuthenticationEntryPoint authenticationEntryPoint() {
//		// TODO Auto-generated method stub
//		return letsCodeAuthenticationEntryPoint;
//	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
