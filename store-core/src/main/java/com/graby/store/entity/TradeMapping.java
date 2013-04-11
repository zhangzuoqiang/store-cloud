package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * 淘宝交易订单映射
 * 多个淘宝交易对应一个系统交易（根据收货方地址合并）
 * @author huabiao.mahb
 */
@Entity
@Table(name = "sc_trade_mapping")
public class TradeMapping implements Serializable{
	
	
	private static final long serialVersionUID = -5858869718493852851L;


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

	@Index(name="idx_trade_id")
	public Long getTradeId() {
		return tradeId;
	}

	@Index(name="idx_tid")
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
