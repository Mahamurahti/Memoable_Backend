package com.memoable.backend.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.lang.IllegalArgumentException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.memoable.backend.security.SecurityConstants;
import com.memoable.backend.services.NoteService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.memoable.backend.model.Note;
import com.memoable.backend.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class for catching REST requests for getting, saving and deleting notes.
 * @author Jere Salmensaari
 */
@RestController
@RequestMapping("/api/notes")
public class NoteController {

	/**
	 * NoteService for communicating with the database
	 */
	@Autowired
	private NoteService service;

	/**
	 * Gets user's notes from the database. 
	 * <p>
	 * Request must have a valid token matching to the given user
	 * ID in the headers as Bearer token to get the user's notes.
	 * @param id ID of the user whose notes should be returned.
	 * @param headers Headers of the Http request.
	 * @return List of Notes beloging to the given user ID
	 */
	@GetMapping("/{id}")
	public List<Note> getUserNotes(@PathVariable String id, @RequestHeader Map<String, String> headers) {
		String token = headers.get("authorization");
		if (parseJWT(token, id)) {
			return service.findByUserId(id);
		}
		return null;
	}

	/**
	 * Saves a new Note or if the Note's id already exists updates the original Note
	 * <p>
	 * Must have a valid token mathcing to the user's id in the headers
	 * as a Bearer token.
	 * @param body Note body formatted as JSON.
	 * @param headers Headers of the Http request.
	 * @param id ID of the user whose note is going to be saved.
	 * @return Saved Note.
	 */
	@PostMapping("save/{id}")
	public Note save(@RequestBody Map<String, String> body, @RequestHeader Map<String, String> headers, @PathVariable String id) {
		String token = headers.get("authorization");

		if (body.get("title") == null || body.get("content") == null)
			return null;

		if (!parseJWT(token, id)) 
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
		Note note = new Note(body.get("title"), body.get("content"));
		note.setUserId(id);
		if (body.get("label") == null)
			note.setLabel("");
		note.setLabel(body.get("label"));
		try {
			note.setDate(sdf.parse(body.get("date")));
		} catch (ParseException e1) {
			System.out.println("Could not parse date");
			note.setDate(new Date());
		} catch (NullPointerException e) {
			note.setDate(new Date());
		}
		try {
			note.setTag(Tag.valueOf(body.get("tag")));
		} catch (IllegalArgumentException e) {
			System.out.println("No enum for "+ body.get("tag"));
			note.setTag(Tag.NONE);
		}

		if (body.get("id") != null && !body.get("id").equals("")) {
			note.setId(body.get("id"));
		}
		
		return service.save(note);
	}

	/**
	 * Deletes a note that mathces the given note_id
	 * <p>
	 * Must have a valid token matching to the user's 
	 * ID in the headers as a Bearer token.
	 * @param headers Headers of the Http request
	 * @param id user's id whose note is going to be deleted
	 * @param note_id id of the note to be deleted
	 * @return true if note can be deleted, otherwise false
	 */
	@DeleteMapping("delete/{id}/{note_id}")
	public Boolean delete(@RequestHeader Map<String, String> headers, @PathVariable String id, @PathVariable String note_id) {
		String token = headers.get("authorization");

		if (!parseJWT(token, id)) 
			return false;

		List<Note> notes = service.findByUserId(id);

		Boolean isOwner = false;
		for (Note n : notes) {
			if (n.getNoteId().equals(note_id)) {
				isOwner = true;
			}
		}
		if (!isOwner)
			return false;

		try {
			service.deleteById(note_id);
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
