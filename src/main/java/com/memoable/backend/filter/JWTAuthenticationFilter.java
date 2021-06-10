package com.memoable.backend.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memoable.backend.model.User;
import com.memoable.backend.security.SecurityConstants;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class for authenticating incoming Http requests
 * <p>
 * Based on https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * AutenticationManager for creating authentications
	 */
	private AuthenticationManager authenticationManager;

	/**
	 * Constructor, sets authenticationManager and filterURL
	 * @param authenticationManager
	 */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;

		setFilterProcessesUrl("/api/login");
	}

	/**
	 * Performs authentication on an incoming request
	 * @param req request to authenticate
	 * @param res Http response
	 * @return Authentication of the request
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
								HttpServletResponse res) {
		try {
			User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_user"));
			Authentication a = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						creds.getUsername(),
						creds.getPassword()
			));
			return a;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	/**
	 * Creates an JWT token for a succesfully authenticated request
	 * @param req authenticated request
	 * @param res Http response
	 * @param chain filterChain
	 * @param auth Given authentication
	 * @throws IOException if response writing fails
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
											FilterChain chain, Authentication auth) throws IOException {
		String token = JWT.create()
							.withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
							.sign(Algorithm.HMAC256(SecurityConstants.SECRET.getBytes()));

		String body = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername() + " " + token;

		res.getWriter().write(body);
		res.getWriter().flush();
	}
												
}
