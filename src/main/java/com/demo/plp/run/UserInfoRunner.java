package com.demo.plp.run;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.demo.plp.po.User;
import com.demo.plp.service.IRedisService;
import com.demo.plp.service.IUserService;
import com.demo.plp.utils.superclass.LoggerSuper;

/**
 * 运行服务器时将用户加载到缓存
 * @author gaoyiyang
 *
 */
@Component
@Order(2)
public class UserInfoRunner extends LoggerSuper implements CommandLineRunner {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IRedisService cache;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("加载用户信息");
		int i = 0;
		while(true){
			List<User> list = userService.userList(i++, 10);
			if(list==null||list.size()==0)
				break;
			System.out.println(list.size());
			for(User user : list){
				cache.setHashValue(IRedisService.USER_INFO, user.getUsername(), user);
				log.info("用户名:"+user.getUsername()+"[保存成功]");
			}
		}
		log.info("用户信息已载入缓存");
		
	}

}
