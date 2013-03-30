package com.graby.store.entity;

import java.io.Serializable;

/**
 * 物流公司
 * @author huabiao.mahb
 */

public class Express implements Serializable {

	private static final long serialVersionUID = 4749520819375302462L;
	
	// 编码
	private String code;
	
	// 公司名称
	private String name;
	
	// 运单规则
	private String regMailNo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegMailNo() {
		return regMailNo;
	}

	public void setRegMailNo(String regMailNo) {
		this.regMailNo = regMailNo;
	}
	
}
