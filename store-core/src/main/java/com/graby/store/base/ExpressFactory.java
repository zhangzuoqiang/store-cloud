package com.graby.store.base;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExpressFactory {
	
	public static final Map<String, String> 	expressCompanys = new LinkedHashMap<String, String>();
	
	static {
		expressCompanys.put("YUNDA", "韵达");
		expressCompanys.put("SF", "顺丰");
	}
}
