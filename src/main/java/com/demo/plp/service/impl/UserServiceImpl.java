package com.demo.plp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.demo.plp.mapper.UserMapper;
import com.demo.plp.po.User;
import com.demo.plp.service.IRedisService;
import com.demo.plp.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IRedisService cache;
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
		User user = userMapper.exists(username);
		if(password==null||!password.equals(user.getPassword()))
			return null;
		return user;
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
	public boolean isOnline(User user, String ip) {
		if(cache.getHashValue(user.getId(), ip)==null){
			return false;
		}
		//刷新缓存时间
		login(user,ip);
		return true;
	}

	@Override
	public void login(User user, String ip) {
		cache.setHashValue(user.getId(), ip, "online", 30 * 60L);
	}

}
