package com.demo.plp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.plp.mapper.UserFullMapper;
import com.demo.plp.po.User;
import com.demo.plp.po.UserFull;
import com.demo.plp.service.IUserFullService;

public class UserFullServiceImpl implements IUserFullService {
	
	@Autowired
	private UserFullMapper ufm;
	
	@Override
	public void addUserFull(UserFull userFull) {
		ufm.insert(userFull);
	}

	@Override
	public UserFull getUserFull(String userId) {
		User user = new User();
		user.setId(userId);
		return ufm.select(user);
	}

	@Override
	public void updateUserFull(UserFull userFull) {
		ufm.update(userFull);
	}

	@Override
	public void addUserFull(User user) {
		UserFull userFull = new UserFull();
		userFull.setId(UUID.randomUUID().toString().replace("-", ""));
		userFull.setUserId(user.getId());
		addUserFull(userFull);
	}

}
