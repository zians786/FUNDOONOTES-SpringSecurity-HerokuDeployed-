package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;

public interface AdminDao {
	
	List<User> getAllUsers();

	List<Note> getAllNotes();

	Integer getNoteCountById(int userId);

	Integer getNoteCount();

}
