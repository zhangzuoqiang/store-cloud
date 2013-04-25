package com.graby.store.service.inventory;

import java.io.Serializable;

/**
 * 多商铺分类记账
 * @author huabiao.mahb
 */
public class AccountEntryArray implements Serializable {

	private static final long serialVersionUID = -5991439870190044027L;
	private Long centroId;
	private Long userId;
	private Long itemId;
	private AccountEntry[] entrys;

	public Long getItemId() {
		return itemId;
	}

	public AccountEntry[] getEntrys() {
		return entrys;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setEntrys(AccountEntry[] entrys) {
		this.entrys = entrys;
	}

	public Long getCentroId() {
		return centroId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setCentroId(Long centroId) {
		this.centroId = centroId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
