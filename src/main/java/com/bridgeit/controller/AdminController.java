package com.bridgeit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;
import com.bridgeit.security.model.AuthenticatedUser;
import com.bridgeit.service.AdminService;

@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
	public String welcome() {
		return "Welcome Admin";
	}

	
	@RequestMapping(value= {"/users"},method=RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers(){
		
		return new ResponseEntity<List<User>>(adminService.getAllUsers(),HttpStatus.OK);
	}
	
	@RequestMapping(value= {"/notes"},method=RequestMethod.GET)
	public ResponseEntity<List<Note>> getNotes(){
		
		return new ResponseEntity<List<Note>>(adminService.getAllNotes(),HttpStatus.OK);
	}
	
	@RequestMapping(value= {"/note-count/{userId}"},method=RequestMethod.POST)
	public ResponseEntity<Integer> getNoteCount(@PathVariable int userId){
		
		return new ResponseEntity<Integer>(adminService.getNoteCountById(userId),HttpStatus.OK);
	}
	
	@RequestMapping(value= {"/note-count"},method=RequestMethod.GET)
	public ResponseEntity<Integer> getNoteCount(){
		
		return new ResponseEntity<Integer>(adminService.getNoteCount(),HttpStatus.OK);
	}
	
	
	
	
	
}
