package com.graby.store.portal.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;

public class MyBatisUtils {
	
	public static String underScore2CamelCase(String strs) {  
	    String[] elems = strs.split("_");  
	    for ( int i = 0 ; i < elems.length ; i++ ) {  
	        elems[i] = elems[i].toLowerCase();  
	        if (i != 0) {  
	            String elem = elems[i];  
	            char first = elem.toCharArray()[0];  
	            elems[i] = "" + (char) (first - 32) + elem.substring(1);  
	        }  
	    }  
	    for ( String e : elems ) {  
	        System.out.print(e);  
	    }  
	    return elems.toString();  
	}  
	  
	/** 
	 * @param param 
	 *        待转换字符串 
	 * @return 
	 * @author estone 
	 * @description 驼峰格式字符串 转换成 下划线格式字符串 
	 *              eg: playerId -> player_id;<br> 
	 *              playerName -> player_name; 
	 */  
	public static String camelCase2Underscore(String param) {  
	    Pattern p = Pattern.compile("[A-Z]");  
	    if (param == null || param.equals("")) {  
	        return "";  
	    }  
	    StringBuilder builder = new StringBuilder(param);  
	    Matcher mc = p.matcher(param);  
	    int i = 0;  
	    while (mc.find()) {  
	        builder.replace(mc.start() + i,mc.end() + i,"_" + mc.group().toLowerCase());  
	        i++;  
	    }  
	    if ('_' == builder.charAt(0)) {  
	        builder.deleteCharAt(0);  
	    }  
	    return builder.toString();  
	}  
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		desc1(new ShipOrder());
	}

	private static void conv1(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String,Object> map = BeanUtils.describe(object);
		StringBuffer buf = new StringBuffer();
		for (String p : map.keySet()) {
			buf.append("<result property=\"").append(p).append("\" column=\"").append(camelCase2Underscore(p)).append("\"/>\n");
		}
		System.out.println(buf.toString());
	}

	private static void desc1(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String,Object> map = BeanUtils.describe(object);
		for (String p : map.keySet()) {
			System.out.print(camelCase2Underscore(p) + " as \"" + p +  "\" ,\n");
		}
		System.out.println(0%2);
	}
}
