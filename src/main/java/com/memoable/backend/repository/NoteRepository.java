package com.memoable.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.memoable.backend.model.Note;

/**
 * Interface for connecting to the Note collection
 * @author Jere Salmensaari
 */
@Component("NoteRepository")
public interface NoteRepository extends MongoRepository<Note, String> {

	/**
	 * Finds notes by title from the database
	 * @param title title to find
	 * @return List of found Notes
	 */
	public List<Note> findByTitle(String title);

	/**
	 * Finds notes by user id
	 * @param uId user id to use in the query
	 * @return List of found notes
	 */
	public List<Note> findByUserId(String uId);

	/**
	 * Finds a single note by it's id
	 */
	public Optional<Note> findById(String id);
}
