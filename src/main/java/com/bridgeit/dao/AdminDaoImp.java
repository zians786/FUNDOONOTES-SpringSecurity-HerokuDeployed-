package com.bridgeit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;

@Component
public class AdminDaoImp implements AdminDao {

	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<User> getAllUsers() {
		Query query=getSession().createQuery("from User");
		List<User> users=query.list();
		return users;
	}

	@Override
	public List<Note> getAllNotes() {
		Query query=getSession().createQuery("from Note");
		List<Note> notes=query.list();
		return notes;
		}

	@Override
	public Integer getNoteCountById(int userId) {
		Query query=getSession().createQuery("select count(*) from Note where userId='"+userId+"'");
		int count=(int)(long) query.uniqueResult();
		return count;
	}

	@Override
	public Integer getNoteCount() {
		Query query=getSession().createQuery("select count(*) from Note");
		int count=(int)(long) query.uniqueResult();
		return count;
	}

}
