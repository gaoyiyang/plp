package com.demo.plp;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.plp.po.User;
import com.demo.plp.service.IRedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlpApplicationTests {

	@Autowired
	private IRedisService redisService;
	
	@Test
	public void contextLoads() {
		//redisService.setValue("test", "a");
//		redisService.setHashValue("a", "b", new User());
//		System.out.println(redisService.getHashValue("a", "b"));
//		redisService.setValue("test", "abc", 10L);
//		redisService.setValue("del", "123");
//		redisService.remove("del");
//		redisService.setHashValue("a", "b", "c");
		System.out.println(redisService.removeHash("a", "b"));
	}

}
