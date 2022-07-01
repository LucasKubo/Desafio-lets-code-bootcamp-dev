package com.letscodechallenge.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
        String errorMessage = "Invalid login";
		if (exception.getMessage()
	            .equalsIgnoreCase("blocked")) {
			errorMessage="Attempt limit exceeded";
		}
        request.getSession()
            .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}

}
