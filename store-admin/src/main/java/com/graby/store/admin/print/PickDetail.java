package com.graby.store.admin.print;

public class PickDetail {
	
	private String code;
	private String title;
	private String sku;
	private int num;
	private String position;

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getSku() {
		return sku;
	}

	public int getNum() {
		return num;
	}

	public String getPosition() {
		return position;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
