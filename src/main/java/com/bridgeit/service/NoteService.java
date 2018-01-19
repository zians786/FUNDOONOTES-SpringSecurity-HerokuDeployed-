package com.bridgeit.service;

import java.util.List;

import com.bridgeit.model.Note;

public interface NoteService {


		void createNote(Note note,String token);
		List<Note> readNote(int userId);
		void updateNote(Note note);
		void deleteNote(int noteId);
		void archiveNote(int noteId,String status);
		void trashNote(int noteId,String status);
		void pinNote(int noteId,String status);
		void colorNote(int noteId,String color);
		void remindNote(Note note,int userId);
		void setLabel(int labelId,int noteId);
		void deleteLabel(int labelId,int noteId);
		Note shareNote(String email,int noteId,int userId);
		Note removeSharedNote(int sharedUserId,int noteId);
		Boolean validateAccess(int userId,int noteId);
	}


