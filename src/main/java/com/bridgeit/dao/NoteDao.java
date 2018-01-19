package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;

public interface NoteDao {
	
 void create(Note note);
 void update(Note note);
 Note read(Note note);
 Note read(int noteId);
 List<Note> read(User user);
 void delete(int noteId);
 void archive(int noteId,String status);
 void trash(int noteId,String status);
 void pin(int noteId,String status);
 void color(int noteId,String color);
 void remind(Note note,int userId);
 
}
