package com.bridgeit.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.LabelDao;
import com.bridgeit.dao.NoteDao;
import com.bridgeit.dao.UserDao;
import com.bridgeit.model.Label;
import com.bridgeit.model.Note;
import com.bridgeit.model.User;
import com.bridgeit.model.UserResponse;
import com.bridgeit.utility.JWT;

@Component
@Transactional
public class NoteServiceImp implements NoteService {

	@Autowired
	NoteDao noteDao;

	@Autowired
	LabelDao labelDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	JWT jwtObject;

	public void createNote(Note note, String token) {
		int userId = Integer.parseInt(token);
		User user = new User();
		user.setUserId(userId);
		note.setUser(user);
		Date date=new Date();
		note.setCreatedDate(date);
		note.setModifiedDate(date);
		noteDao.create(note);

	}

	public void deleteNote(int noteId) {
		noteDao.delete(noteId);
	}

/*	public void updateNote(Note note, String token) {
		int userId = Integer.parseInt(token);
		
		Note note1=noteDao.read(note);
		note1.setTitle(note.getTitle());
		note1.setDescription(note.getDescription());
		note1.setImage(note.getImage());
		Date date=new Date();
		note1.setModifiedDate(date);
		
		
		User user = new User();
		user.setUserId(userId);
		note.setUser(user);
		Date date=new Date();
		
		note.setModifiedDate(date);
		noteDao.update(note);

	}
*/
	public List<Note> readNote(int userId) {
		User user=new User();
		user.setUserId(userId);
		return noteDao.read(user);
	}

	@Override
	public void archiveNote(int noteId,String status) {
		 noteDao.archive(noteId,status);
	}

	@Override
	public void trashNote(int noteId,String status) {
		 noteDao.trash(noteId,status);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pinNote(int noteId,String status) {
		 noteDao.pin(noteId,status);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colorNote(int noteId,String color) {
		color="#"+color;
		noteDao.color(noteId,color);
	}

	@Override
	public void remindNote(Note note, int userId) {
		noteDao.remind(note, userId);
	}
	
	public void setLabel(int labelId,int noteId) {
		Note note=noteDao.read(noteId);
			Label label = labelDao.read(labelId);
			Set set=note.getLabel();
			set.add(label);
			note.setLabel(set);
			noteDao.update(note);	
	}

	@Override
	public void deleteLabel(int labelId, int noteId) {
		Note note=noteDao.read(noteId);
		Label label = labelDao.read(labelId);
		
		Set set=note.getLabel();
		set.remove(label);
		note.setLabel(set);
		noteDao.update(note);
	}
	@Override
	public Note shareNote(String email, int noteId, int userId) {
		if(userDao.emailValidaton(email)) {
			Note note=noteDao.read(noteId);
			User user=userDao.getUserByEmailId(email);
			Set<User> userSet=note.getSharedUser();
			userSet.add(user);
			note.setSharedUser(userSet);
			noteDao.update(note);
			return note;
		}else {
			return null;
		}

	}

	@Override
	public Note removeSharedNote(int sharedUserId, int noteId) {
		Note note = noteDao.read(noteId);
		User user = userDao.getUserByUserId(sharedUserId);
		Set<User> userSet=note.getSharedUser();
		userSet.remove(user);
		note.setSharedUser(userSet);
		noteDao.update(note);
		return note;
	}

	@Override
	public void updateNote(Note note) {
			Date date=new Date();
			note.setModifiedDate(date);
			noteDao.update(note);		
	}
	
	public Boolean validateAccess(int userId,int noteId) {
		Note note=noteDao.read(noteId);
		User user=note.getUser();
		if(userId!=user.getUserId()) {
			return false;
		}else {
			return true;
		}
	}
	
}
