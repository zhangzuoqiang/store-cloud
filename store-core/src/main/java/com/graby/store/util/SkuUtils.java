package com.graby.store.util;

import org.apache.commons.lang3.StringUtils;

public class SkuUtils {
	
	/**
	 * 截取淘宝的sku名称
	 * @param val
	 * @return
	 */
	public static String convert(String val) {
		if (StringUtils.isBlank(val)) {
			return "";
		}
		String[] col = val.split(";");
		StringBuffer result = new StringBuffer();
		for (String s : col) {
			String[] pair = s.split(":");
			result.append(pair[2]).append(":").append(pair[3]).append("; ");
		}
		return result.toString();
	}
	
}
