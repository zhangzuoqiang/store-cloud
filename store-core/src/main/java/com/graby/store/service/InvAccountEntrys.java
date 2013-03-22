package com.graby.store.service;

import java.io.Serializable;

public class InvAccountEntrys implements Serializable {

	private static final long serialVersionUID = -5991439870190044027L;
	private Long centroId;
	private Long userId;
	private Long itemId;
	private InvAccountEntry[] entrys;

	public Long getItemId() {
		return itemId;
	}

	public InvAccountEntry[] getEntrys() {
		return entrys;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setEntrys(InvAccountEntry[] entrys) {
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
