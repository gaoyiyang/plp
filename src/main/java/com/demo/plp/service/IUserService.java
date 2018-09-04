package com.demo.plp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.plp.mapper.UserMapper;
import com.demo.plp.po.User;

public interface IUserService {
	public void addUser(String username, String password);
	public User getUser(String username, String password);
	public User getUser(String username);
	public void updateUser(User user);
}
