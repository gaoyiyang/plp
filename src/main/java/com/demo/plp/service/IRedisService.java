package com.demo.plp.service;

public interface IRedisService {
	public String USER_INFO = "userinfo&plp";
	public String IS_ONLINE = "isonline&plp";
	public String WS = "websocket&plp";
	public String MSG = "websocket_message&plp";
	boolean setValue(String key, Object value);
	boolean setValue(String key, Object value, Long time);
	boolean setHashValue(String key, String field, Object value);
	boolean setHashValue(String key, String field, Object value, Long time);
	boolean push(String key, Object value);
	boolean push(String key, Object value, Long time);
	boolean remove(String key);
	//清空缓存
	void clear();
	Long removeHash(String key, Object...fields);
	Object getValue(String key);
	Object getHashValue(String key, String field);
	Object get(String key, long index);
}
