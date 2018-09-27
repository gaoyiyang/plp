package com.demo.plp.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.demo.plp.mapper.UserMapper;
import com.demo.plp.po.User;
import com.demo.plp.service.IRedisService;
import com.demo.plp.service.IUserService;
import com.demo.plp.utils.GetTimeId;
import com.demo.plp.utils.MD5Util;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IRedisService cache;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void addUser(String username, String password) {
		User user = new User();
		password = MD5Util.getMD5(password);
		user.setId(GetTimeId.getInstance().next());
		user.setUsername(username);
		user.setPassword(password);
		userMapper.insert(user);
	}

	@Override
	public User getUser(String username, String password) {
		User user = getUser(username);
		password = MD5Util.getMD5(password);
		if(user==null)
			return null;
		if(password==null||!password.equals(user.getPassword()))
			return null;
		return user;
	}
	
	/**
	 * 获取当前在线用户
	 * @param request
	 * @return
	 */
	@Override
	public User getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) cache.getHashValue(IRedisService.IS_ONLINE, session.getId());
	}

	@Override
	public void updateUser(User user) {
		cache.removeHash(IRedisService.USER_INFO, user.getUsername());
		userMapper.update(user);
	}

	@Override
	public User getUser(String username) {
		User user = (User) cache.getHashValue(IRedisService.USER_INFO, username);
		if(user == null){
			user = userMapper.exists(username);
			cache.setHashValue(IRedisService.USER_INFO, username, user);
		}
		return userMapper.exists(username);
	}

	@Override 
	public boolean isOnline(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(cache.getHashValue(IRedisService.IS_ONLINE, session.getId())==null){
			return false;
		}
		//刷新缓存时间
		login(request);
		return true;
	}

	@Override
	public void login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cache.setHashValue(IRedisService.IS_ONLINE, session.getId(), session.getAttribute("user"), 30 * 60L);
	}

	@Override
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		cache.removeHash(IRedisService.IS_ONLINE, session.getId());
	}

	@Override
	public List<User> userList(int page, int pageSize) {
		return userMapper.selectAll(page*pageSize, pageSize);
	}

}
