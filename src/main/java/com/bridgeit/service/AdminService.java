package com.bridgeit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;


public interface AdminService {
	
	public List<User> getAllUsers();

	public List<Note> getAllNotes();

	public Integer getNoteCountById(int userId);

	public Integer getNoteCount();
	
}
