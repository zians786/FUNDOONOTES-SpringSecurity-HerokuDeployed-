package com.bridgeit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;
import com.bridgeit.model.UserResponse;
import com.bridgeit.security.model.AuthenticatedUser;
import com.bridgeit.service.NoteService;
import com.bridgeit.service.Service;
import com.bridgeit.utility.JWT;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	JWT jwtObject;

	@RequestMapping(value = "/note", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> create(@RequestBody Note note) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String userId = Long.toString(authenticatedUser.getId());

		UserResponse message = new UserResponse();

		noteService.createNote(note, userId);
		message.setMessage("Note created Successfully.");
		return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/note/{noteId}", method = RequestMethod.DELETE)
	public ResponseEntity<UserResponse> delete(@PathVariable int noteId) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) (authenticatedUser.getId());
		UserResponse message = new UserResponse();

		if (noteService.validateAccess(userId, noteId)) {
			noteService.deleteNote(noteId);
			message.setMessage("Note Deleted Successfully.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		} else {
			message.setMessage("Invalid Access.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "/note", method = RequestMethod.PUT)
	public ResponseEntity<UserResponse> update(@RequestBody Note note) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();
		UserResponse message = new UserResponse();
		
		if (noteService.validateAccess(userId, note.getNoteId())) {
			noteService.updateNote(note);
			message.setMessage("Note Updated Successfully.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Access");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "/note", method = RequestMethod.GET)
	public ResponseEntity<List<Note>> read() {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();

		List<Note> resultNote = noteService.readNote(userId);
		return new ResponseEntity<List<Note>>(resultNote, HttpStatus.OK);

	}

	@RequestMapping(value = "note/archive/{noteId}/{status}", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> archive(@PathVariable int noteId, @PathVariable String status) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();

		UserResponse message = new UserResponse();

		if (noteService.validateAccess(userId, noteId)) {
			noteService.archiveNote(noteId, status);
			message.setMessage("Successfull.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		} else {
			message.setMessage("Invalid Access");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "note/trash/{noteId}/{status}", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> trash(@PathVariable int noteId, @PathVariable String status) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();
		UserResponse message = new UserResponse();

		if (noteService.validateAccess(userId, noteId)) {
			noteService.trashNote(noteId, status);
			message.setMessage("Successfull.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		} else {
			message.setMessage("Invalid Access");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "note/pin/{noteId}/{status}", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> pin(@PathVariable int noteId, @PathVariable String status) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();

		UserResponse message = new UserResponse();
		if ( noteService.validateAccess(userId, noteId)) {
			noteService.pinNote(noteId, status);
			message.setMessage("Successfull");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "note/color/{noteId}/{color}", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> color(@PathVariable String color, @PathVariable int noteId) {
		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();

		UserResponse message = new UserResponse();
		if (noteService.validateAccess(userId, noteId)) {
			noteService.colorNote(noteId, color);
			message.setMessage("color changed");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "notes/reminder", method = RequestMethod.POST)
	public ResponseEntity<String> reminder(@RequestBody Note note) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();

		if (noteService.validateAccess(userId, note.getNoteId())) {
			noteService.remindNote(note, userId);
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} else {

			return new ResponseEntity<String>("Discarded", HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "notes/label/{labelId}/note/{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<UserResponse> setLabel(@PathVariable int labelId, @PathVariable int noteId) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
			int userId = (int) (long) authenticatedUser.getId();

		UserResponse message = new UserResponse();
		if (noteService.validateAccess(userId, noteId)) {
			noteService.setLabel(labelId, noteId);
			message.setMessage("Label changed");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "notes/label/{labelId}/note/{noteId}", method = RequestMethod.DELETE)
	public ResponseEntity<UserResponse> deleteLabel(@PathVariable int labelId, @PathVariable int noteId) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
			int userId = (int) (long) authenticatedUser.getId();

		
		UserResponse message = new UserResponse();
		if (noteService.validateAccess(userId, noteId)) {
			noteService.deleteLabel(labelId, noteId);
			message.setMessage("Label changed");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Access");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "notes/share/email/{email}/note/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<?> shareNote(@PathVariable String email, @PathVariable int noteId) {

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();
		
		UserResponse message = new UserResponse();
		if (noteService.validateAccess(userId, noteId)) {
			Note note = noteService.shareNote(email, noteId, userId);
			if (note != null) {
				return new ResponseEntity<Note>(note, HttpStatus.OK);
			} else {
				message.setMessage("Email-Id is not Exist");
				return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
			}
		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "notes/removeShare/user/{sharedUserId}/note/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<?> removeShare(@PathVariable int sharedUserId, @PathVariable int noteId) {
		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int userId = (int) (long) authenticatedUser.getId();

		if (noteService.validateAccess(userId, noteId)) {
			Note note = noteService.removeSharedNote(sharedUserId, noteId);
			return new ResponseEntity<Note>(note, HttpStatus.OK);
		} else {
			UserResponse message = new UserResponse();
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}

}
