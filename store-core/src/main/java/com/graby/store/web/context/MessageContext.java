package com.graby.store.web.context;

/**
 * Web Request 附带信息 
 * @author huabiao.mahb
 */
public interface MessageContext {
	
	/**
	 * 添加信息
	 * @param message
	 */
	void append(String message);
	
	/**
	 * 获取信息
	 * @return
	 */
	String getMessage();
}
