package com.graby.store.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 添加信息
 * @author huabiao.mahb
 *
 */
public class MessageContextHelper {
	
	private static final String MESSAGE_REQUEST_ATTRIBUTE = "message";
	
	public static void append(String message) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return;
		}
		String msg = (String)requestAttributes.getAttribute(MESSAGE_REQUEST_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		StringBuffer buf = new StringBuffer();
		buf.append(StringUtils.isEmpty(msg)? "" : msg+"\n<br>");
		buf.append(message);
		requestAttributes.setAttribute(MESSAGE_REQUEST_ATTRIBUTE, buf.toString(), RequestAttributes.SCOPE_REQUEST);
	}

	
	public static String getMessage() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return "";
		}
		return (String) requestAttributes.getAttribute(MESSAGE_REQUEST_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
	}

}
