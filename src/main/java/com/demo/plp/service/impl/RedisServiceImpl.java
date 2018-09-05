package com.demo.plp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.demo.plp.service.IRedisService;

import java.util.concurrent.TimeUnit;



@Service
public class RedisServiceImpl implements IRedisService{

	@Autowired
	private RedisTemplate<String,Object> rt;
	
	@Override
	public boolean setValue(String key, Object value) {
		try{
			ValueOperations<String, Object> operations = rt.opsForValue();
			operations.set(key, value);
			return true;
		}catch (Exception e) {
//			System.out.println("- 缓存服务出错：" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setValue(String key, Object value, Long time) {
		try{
			ValueOperations<String, Object> operations = rt.opsForValue();
			operations.set(key, value);
			//设置缓存时间
			rt.expire(key, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean setHashValue(String key, String field, Object value) {
		try{
			HashOperations<String, Object, Object> map = rt.opsForHash();
			map.put(key, field, value);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean setHashValue(String key, String field, Object value, Long time) {
		try{
			HashOperations<String, Object, Object> map = rt.opsForHash();
			map.put(key, field, value);
			rt.expire(key, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean push(String list, Object value) {
		try{
			ListOperations<String, Object> ops = rt.opsForList();
			ops.rightPush(list, value);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean push(String list, Object value, Long time) {
		try{
			ListOperations<String, Object> ops = rt.opsForList();
			ops.rightPush(list, value);
			rt.expire(list, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public Object getValue(String key) {
		ValueOperations<String, Object> ops = rt.opsForValue();
		Object value = ops.get(key);
		return value;
	}

	@Override
	public Object getHashValue(String key, String field) {
		HashOperations<String, Object, Object> ops = rt.opsForHash();
		Object value = ops.get(key,field);
		return value;
	}

	@Override
	public Object get(String key, long index) {
		ListOperations<String, Object> ops = rt.opsForList();
		Object value = ops.index(key, index);
		return value;
	}

	@Override
	public boolean remove(String key) {
		return rt.delete(key);
	}


	@Override
	public Long removeHash(String key, Object...fields) {
		return rt.opsForHash().delete(key, fields);
	}

}
