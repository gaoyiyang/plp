package com.demo.plp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	public static Logger getLogger(Object obj){
		return LoggerFactory.getLogger(obj.getClass());
	}
}
