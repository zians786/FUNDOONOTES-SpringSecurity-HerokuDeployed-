package com.bridgeit.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.LabelDao;
import com.bridgeit.model.Label;
import com.bridgeit.model.User;

@Component
@Transactional
public class LabelServiceImp implements LabelService {

	@Autowired
	LabelDao labelDao;
	
	
	
	@Override
	public void createLabel(Label label, int userId) {
		User user = new User();
		user.setUserId(userId);
		label.setUser(user);
		labelDao.create(label);
	}

	@Override
	public List<Label> readLabel(int userId) {
		User user=new User();
		user.setUserId(userId);
		return labelDao.read(user);
	}

	@Override
	public void updateLabel(Label label, int userId) {
		
		User user = new User();
		user.setUserId(userId);
		label.setUser(user);
		labelDao.update(label);

	}

	@Override
	public void deletelabel(Label label) {
		labelDao.delete(label);

	}

	@Override
	public Boolean validateAccess(int userId, int labelId) {
		int userId1=labelDao.getUserIdByLabelId(labelId);
		if(userId1!=userId) {
			return false;
		}else {
			return true;
		}

	}

}
