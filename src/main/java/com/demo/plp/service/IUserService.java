package com.demo.plp.service;


import com.demo.plp.po.User;

public interface IUserService {
	public void addUser(String username, String password);
	public User getUser(String username, String password);
	public User getUser(String username);
	public void updateUser(User user);
	//是否在线
	public boolean isOnline(User user, String ip);
	//用户登陆
	public void login(User user, String ip);
}
