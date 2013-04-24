package com.graby.store.web.context;



public class MessageContextHolder {

	private static final ThreadLocal<MessageContext> messageContextHolder = new ThreadLocal<MessageContext>();
	
	public static void resetMessageContext() {
		messageContextHolder.remove();
	}
	
	public static void setMessageContext(MessageContext context) {
		messageContextHolder.set(context);
	}
	
	public static MessageContext getMessageContext() {
		return messageContextHolder.get();
	}
	
	public static String getMessage() {
		return getMessageContext().getMessage();
	}
	
	public static void appendMessage(String message) {
		getMessageContext().append(message);
	}
	
}
