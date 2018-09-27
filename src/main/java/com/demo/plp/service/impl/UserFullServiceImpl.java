package com.demo.plp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.plp.mapper.UserFullMapper;
import com.demo.plp.po.User;
import com.demo.plp.po.UserFull;
import com.demo.plp.service.IUserFullService;
import com.demo.plp.utils.GetTimeId;

@Service
public class UserFullServiceImpl implements IUserFullService {
	
	@Autowired
	private UserFullMapper userFullMapper;
	
	@Override
	public void addUserFull(UserFull userFull) {
		userFullMapper.insert(userFull);
	}

	@Override
	public UserFull getUserFull(String userId) {
		User user = new User();
		user.setId(userId);
		return userFullMapper.select(user.getId());
	}

	@Override
	public void updateUserFull(UserFull userFull) {
		userFullMapper.update(userFull);
	}

	@Override
	public void addUserFull(User user) {
		UserFull userFull = new UserFull();
		userFull.setId(GetTimeId.getInstance().next());
		userFull.setUserId(user.getId());
		addUserFull(userFull);
	}

}
