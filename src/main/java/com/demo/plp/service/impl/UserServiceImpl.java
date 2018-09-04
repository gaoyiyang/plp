package com.demo.plp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.plp.mapper.UserMapper;
import com.demo.plp.po.User;
import com.demo.plp.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void addUser(String username, String password) {
		User user = new User();
		user.setId(UUID.randomUUID().toString().replace("-", ""));
		user.setUsername(username);
		user.setPassword(password);
		userMapper.insert(user);
	}

	@Override
	public User getUser(String username, String password) {
		return userMapper.select(username, password);
	}

	@Override
	public void updateUser(User user) {
		userMapper.update(user);
	}

	@Override
	public User getUser(String username) {
		return userMapper.exists(username);
	}

}
