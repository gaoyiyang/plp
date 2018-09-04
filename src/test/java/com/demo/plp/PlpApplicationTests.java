package com.demo.plp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.plp.mapper.UserMapper;
import com.demo.plp.po.User;
import com.demo.plp.service.IUserService;
import com.demo.plp.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlpApplicationTests {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void contextLoads() {
		System.out.println(userMapper.select("admin", "1"));
	}

}
