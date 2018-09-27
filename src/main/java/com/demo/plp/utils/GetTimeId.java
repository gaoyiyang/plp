package com.demo.plp.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GetTimeId {
	private static final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final DecimalFormat decimalFormat = new DecimalFormat("000");
	private static final int MAX_FLAG = 999;
	private static Integer flag = 0;
	private int rand;
	private static GetTimeId gti = null;
	private Long timestamp = null;
	private Long oldTimestamp = null;

	private GetTimeId(int rand) {
		this.rand = rand;
	}

	public static GetTimeId getInstance() {
		if (gti == null) {
			gti = new GetTimeId(new Random().nextInt(900) + 100);
		}
		return gti;
	}

	public synchronized String next() {
		Date ts = new Date(getTimeStamp());
		String id = df.format(ts) + rand + decimalFormat.format(++flag);
		return id;
	}

	private Long getTimeStamp() {
		if (timestamp == null) {
			timestamp = new Date().getTime();
			oldTimestamp = timestamp;
		} else if (timestamp.equals(oldTimestamp) && flag >= MAX_FLAG) {
			timestamp = timestamp + 1;
			flag = 0;
		} else if (!timestamp.equals(oldTimestamp)) {
			oldTimestamp = timestamp;
		} else if(flag >= MAX_FLAG){
			flag = 0;
		}
		long now = new Date().getTime();
		if(now>timestamp)
			timestamp = now;
		return timestamp;
	}
}
