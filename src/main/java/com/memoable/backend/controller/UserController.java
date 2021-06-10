package com.memoable.backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.memoable.backend.model.User;
import com.memoable.backend.repository.UserRepository;
import com.memoable.backend.security.SecurityConstants;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class for catching REST requests for creating and deleting users
 * @author Jere Salmensaari
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	/**
	 * User repository for communicating with the database
	 */
	@Autowired
	private UserRepository repository;

	/**
	 * PasswordEncoder for encoding incoming passwords
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/{name}") 
	public User findByName(@PathVariable String name) {
		return repository.findByUsername(name);
	}

	/**
	 * Creates a user into the database
	 * <p>
	 * If username is already in use, returns "unameErr". If
	 * username or password fields are empty returns null
	 * @param body User object in JSON form
	 * @return username and user's id, null if fields are missing or unameErr if username is in use
	 */
	@PostMapping
	public String createUser(@RequestBody Map<String, String> body) {
		if (body.get("username").equals("") || body.get("password").equals(""))
			return null;

		User copy = repository.findByUsername(body.get("username"));

		if (copy != null)
			return "unameErr";

		User u = new User(body.get("username"), passwordEncoder.encode(body.get("password")) );

		repository.save(u);
		return u.getUsername()+" "+u.getId();
		
	}

	/**
	 * Deletes a user corresponding to the given id
	 * <p>
	 * Must have a valid token matching to the user's
	 * ID in the headers as a Bearer token
	 * @param headers Headers of the Http request
	 * @param id id of the user to be deleted
	 * @return true if user is deleted, otherwise false
	 */
	@DeleteMapping("/delete/{id}")
	public Boolean deleteUser(@RequestHeader Map<String, String> headers, @PathVariable String id) {
		String token = headers.get("authorization");

		if (!parseJWT(token, id)) 
			return false;
		
		try {
			repository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	/**
	 * Parses a JWT token and checks if the token's user matches the given id
	 * @param token JWT token to parse
	 * @param id User id to compare the token to
	 * @return True if id's match, otherwise false
	 */
	private Boolean parseJWT(String token, String id) {
		String user = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET.getBytes()))
						.build()
						.verify(token.replace(SecurityConstants.TOKEN_PREFIX,""))
						.getSubject();
		
		if (user != null && user.equals(id))
			return true;
		return false;
	}

	
	
}
