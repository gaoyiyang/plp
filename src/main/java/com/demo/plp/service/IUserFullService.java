package com.demo.plp.service;

import com.demo.plp.po.User;
import com.demo.plp.po.UserFull;

public interface IUserFullService {
	void addUserFull(UserFull userFull);
	void addUserFull(User user);
	UserFull getUserFull(String userId);
	void updateUserFull(UserFull userFull);
}
