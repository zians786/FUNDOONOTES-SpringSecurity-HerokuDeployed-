package com.bridgeit.service;

import java.util.List;

import com.bridgeit.model.Label;

public interface LabelService {
	

	void createLabel(Label label,int userId);
	List<Label> readLabel(int userId);
	void updateLabel(Label label,int userId);
	void deletelabel(Label label);
	Boolean validateAccess(int userId,int labelId);

}
