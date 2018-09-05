package com.demo.plp.service;

public interface IRedisService {
	public String USER_INFO = "userinfo&plp";
	public String IS_ONLINE = "isonline&plp";
	boolean setValue(String key, Object value);
	boolean setValue(String key, Object value, Long time);
	boolean setHashValue(String key, String field, Object value);
	boolean setHashValue(String key, String field, Object value, Long time);
	boolean push(String list, Object value);
	boolean push(String list, Object value, Long time);
	boolean remove(String key);
	Long removeHash(String key, Object...fields);
	Object getValue(String key);
	Object getHashValue(String key, String field);
	Object get(String key, long index);
}
