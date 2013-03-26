package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物流公司
 * @author huabiao.mahb
 */
@Entity
@Table(name = "sc_express")
public class Express implements Serializable {

	private static final long serialVersionUID = 4749520819375302462L;
	
	// 序号
	private Long id;
	
	// 编码
	private String code;
	
	// 公司名称
	private String companyName;
	
	// 递增规则
	private String increaseRule;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getIncreaseRule() {
		return increaseRule;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setIncreaseRule(String increaseRule) {
		this.increaseRule = increaseRule;
	}
	
}
