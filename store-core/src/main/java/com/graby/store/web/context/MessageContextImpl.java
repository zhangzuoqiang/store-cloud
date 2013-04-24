package com.graby.store.web.context;

public class MessageContextImpl implements MessageContext {

	private StringBuffer message = new StringBuffer();
	
	@Override
	public void append(String message) {
		this.message.append(message).append("\n");
	}

	@Override
	public String getMessage() {
		return this.message.toString();
	}

}
