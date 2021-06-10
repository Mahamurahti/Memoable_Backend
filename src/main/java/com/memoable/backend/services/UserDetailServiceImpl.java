package com.memoable.backend.services;

import com.memoable.backend.model.User;
import com.memoable.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User service class for loading user details into Spring-security
 * User objects
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	/**
	 * Userrepository for communitcating with the database
	 */
	@Autowired
	private UserRepository repository;

	/**
	 * Loads a user from database into a Spring-security UserDetails object
	 * <p>
	 * If user doesn't exist, throws Exception
	 * @param username user to find
	 * @return UserDetails object for current user
	 * @throws UsernameNotFoundException if user is not found
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = repository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		UserDetails userDet = org.springframework.security.core.userdetails.User.withUsername(user.getId().toString()).password(user.getPassword()).roles("USER").build();
		return userDet;
	}
	
}
