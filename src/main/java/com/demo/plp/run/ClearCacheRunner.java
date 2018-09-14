package com.demo.plp.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.demo.plp.service.IRedisService;
import com.demo.plp.utils.superclass.LoggerSuper;

/**
 * 运行时清空缓存
 * @author gaoyiyang
 *
 */
@Component
@Order(1)
public class ClearCacheRunner extends LoggerSuper implements CommandLineRunner {
	
	@Autowired
	private IRedisService cache;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("清空服务器缓存开始");
		cache.clear();
		log.info("清空服务器缓存完成");
	}

}
