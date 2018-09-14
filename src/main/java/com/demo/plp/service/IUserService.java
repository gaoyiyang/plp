package com.demo.plp.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.demo.plp.po.User;

public interface IUserService {
	public void addUser(String username, String password);
	public User getUser(String username, String password);
	public User getUser(String username);
	public void updateUser(User user);
	//是否在线
	public boolean isOnline(HttpServletRequest request);
	//用户登陆
	void login(HttpServletRequest request);
	//用户注销
	void logout(HttpServletRequest request);
	User getUserInfo(HttpServletRequest request);
	List<User> userList(int page,int pageSize);
}
