package com.demo.plp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.demo.plp.service.IRedisService;

import java.net.InetAddress;
import java.util.Set;
import java.util.concurrent.TimeUnit;



@Service
public class RedisServiceImpl implements IRedisService{

	@Autowired
	private RedisTemplate<String,Object> rt;
	//缓存标识
	private static final String cacheFlag = "pro#plp#cache#";
	
	@Override
	public boolean setValue(String key, Object value) {
		try{
			key = cacheFlag + key;
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
			key = cacheFlag + key;
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
			key = cacheFlag + key;
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
			key = cacheFlag + key;
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
	public boolean push(String key, Object value) {
		try{
			key = cacheFlag + key;
			ListOperations<String, Object> ops = rt.opsForList();
			ops.rightPush(key, value);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean push(String key, Object value, Long time) {
		try{
			ListOperations<String, Object> ops = rt.opsForList();
			ops.rightPush(key, value);
			rt.expire(key, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e) {
			System.out.println("- 缓存服务出错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public Object getValue(String key) {
		key = cacheFlag + key;
		ValueOperations<String, Object> ops = rt.opsForValue();
		Object value = ops.get(key);
		return value;
	}

	@Override
	public Object getHashValue(String key, String field) {
		key = cacheFlag + key;
		HashOperations<String, Object, Object> ops = rt.opsForHash();
		Object value = ops.get(key,field);
		return value;
	}

	@Override
	public Object get(String key, long index) {
		key = cacheFlag + key;
		ListOperations<String, Object> ops = rt.opsForList();
		Object value = ops.index(key, index);
		return value;
	}

	@Override
	public boolean remove(String key) {
		key = cacheFlag + key;
		return rt.delete(key);
	}


	@Override
	public Long removeHash(String key, Object...fields) {
		key = cacheFlag + key;
		return rt.opsForHash().delete(key, fields);
	}

	@Override
	public void clear() {
		String pattern = cacheFlag + "*";
		RedisConnection connection = rt.getConnectionFactory().getConnection();
		Set<byte[]> caches = connection.keys(pattern.getBytes());
		if(!caches.isEmpty()){
			connection.del(caches.toArray(new byte[][]{}));
		}
	}

}
