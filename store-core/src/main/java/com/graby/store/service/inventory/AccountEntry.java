package com.graby.store.service.inventory;

import java.io.Serializable;

public class AccountEntry implements Serializable  {
	
	private static final long serialVersionUID = -5972335242487621493L;
	private AccountTemplate accountTemplate;
	private long num;

	public AccountEntry(AccountTemplate template, long num) {
		this.accountTemplate = template;
		this.num = num;
	}

	public long getNum() {
		return num;
	}

	public AccountTemplate getAccountTemplate() {
		return accountTemplate;
	}
}
