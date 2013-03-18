package com.graby.store.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class Express {
	
	public static final Map<String, String> 	expressCompanys = new LinkedHashMap<String, String>();
	
	static {
		expressCompanys.put("YUNDA", "韵达");
		expressCompanys.put("SF", "顺丰");
	}
}
