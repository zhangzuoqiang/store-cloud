package com.graby.store.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 淘宝交易订单映射
 * 
 * @author huabiao.mahb
 */
@Entity
@Table(name = "sc_trade_mapping")
public class TradeMapping {
	
	
	public TradeMapping() {
		
	}
	
	public TradeMapping(Long tid, Long tradeId) {
		this.tid = tid;
		this.tradeId = tradeId;
	}
	
	private Long id;
	
	// 本地系统交易ID
	private Long tradeId;
	
	// 淘宝交易ID
	private Long tid;
	
	// 交易状态
	private String status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public Long getTid() {
		return tid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
