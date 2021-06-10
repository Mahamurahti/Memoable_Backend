package com.memoable.backend.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.memoable.backend.security.SecurityConstants;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Class for checking authorization in Http requests
 * <p>
 * Based on https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 * Constructor, sets authenticationManager of the class
	 * @param authManager AuthenticationManager to set
	 */
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, 
		FilterChain chain) throws IOException, ServletException {
		
		String header = req.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	/**
	 * Parses a JWT token from Http headers and creates a UsernamePasswordAuthenticationToken 
	 * from the user in the JWT
	 * @param request Http request
	 * @return UsernamePasswordAuthenticationToken if user exists, otherwise null
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (token != null) {
			// Parse the token
			String user = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET.getBytes()))
								.build()
								.verify(token.replace(SecurityConstants.TOKEN_PREFIX,""))
								.getSubject();
			
			if (user != null) {
				// new ArrayList means authorities
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}

			return null;
		}

		return null;
	}
	
}
