package com.bridgeit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.AdminDao;
import com.bridgeit.model.Note;
import com.bridgeit.model.User;

@Service
@Transactional
public class AdminServiceImp implements AdminService {

	@Autowired
	AdminDao adminDao;
	
	
	@Override
	public List<User> getAllUsers() {
		return adminDao.getAllUsers();
	}


	@Override
	public List<Note> getAllNotes() {
		return adminDao.getAllNotes();
	}


	@Override
	public Integer getNoteCountById(int userId) {
		return adminDao.getNoteCountById(userId);
	}


	@Override
	public Integer getNoteCount() {
		return adminDao.getNoteCount();
	}


}
