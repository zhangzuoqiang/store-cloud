package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sc_item_mapping")
public class ItemMapping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5814544884361480265L;
	private Long id;
	// 本地商品
	private Item item;
	// 淘宝商品ID
	private Long numIid;
	// 淘宝商品标题
	private String title;
	// 淘宝商品详细URL
	private String detailUrl;
	// 淘宝商品SKU
	private String skuId;
	// 淘宝商品介绍(不持久化)
	private String desc;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}
	
	@Transient
	public String getDesc() {
		return desc;
	}
	
	public Long getNumIid() {
		return numIid;
	}

	public String getTitle() {
		return title;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
