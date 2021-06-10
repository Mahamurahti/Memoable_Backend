package com.memoable.backend.repository;

import com.memoable.backend.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Interface for connecting to the User collection
 * @author Jere Salmensaari
 */
@Component("UserRepository")
public interface UserRepository extends MongoRepository<User, String>{

	/**
	 * Finds a user by their username
	 * @param username username to find
	 * @return found user or null
	 */
	public User findByUsername(String username);
}
