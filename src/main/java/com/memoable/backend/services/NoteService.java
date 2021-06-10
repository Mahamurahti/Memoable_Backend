package com.memoable.backend.services;

import java.util.List;
import java.util.Optional;

import com.memoable.backend.model.Note;
import com.memoable.backend.repository.NoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class for communicating to the note database
 * @author Jere Salmensaari
 */
@Service
public class NoteService {
	
	/**
	 * Note repository
	 */
	@Autowired
	private NoteRepository repository;

	/**
	 * Rerutns all notes, for testing purposes only
	 * @return List of all Notes
	 */
	public List<Note> findAll() {
		return repository.findAll();
	}

	/**
	 * Finds notes by user id
	 * @param uId user id
	 * @return List of Notes correspondind to the user id
	 */
	public List<Note> findByUserId(String uId) {
		return repository.findByUserId(uId);
	}

	/**
	 * Saves the given Note to the repository
	 * @param note Note to save
	 * @return saved Note
	 */
	public Note save(Note note) {
		return repository.save(note);
	}

	/**
	 * Tries to delete a note corresponding to the id
	 * @param id id of the note
	 */
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	/**
	 * Finds a note corresponding to the given id
	 * @param id id of the note
	 * @return Optional that contains the Note object if it exists
	 */
	public Optional<Note> findById(String id) {
		return repository.findById(id);
	}

}
