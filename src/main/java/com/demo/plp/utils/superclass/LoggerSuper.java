package com.demo.plp.utils.superclass;

import org.slf4j.Logger;

import com.demo.plp.utils.LoggerUtil;

public abstract class LoggerSuper {
	protected Logger log = LoggerUtil.getLogger(this);
}
