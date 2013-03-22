package com.graby.store.service;

import java.io.Serializable;

public class InvAccountEntry implements Serializable  {
	
	private static final long serialVersionUID = -5972335242487621493L;
	private InvAccountTemplate accountTemplate;
	private long num;

	public InvAccountEntry(InvAccountTemplate template, long num) {
		this.accountTemplate = template;
		this.num = num;
	}

	public long getNum() {
		return num;
	}

	public InvAccountTemplate getAccountTemplate() {
		return accountTemplate;
	}
}
