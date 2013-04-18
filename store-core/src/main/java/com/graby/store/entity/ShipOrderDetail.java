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
@Table(name = "sc_ship_order_detail")
public class ShipOrderDetail implements Serializable{

	private static final long serialVersionUID = -7428439750730206579L;

	private Long id;

	private ShipOrder order;

	private Item item;

	private long num;
	
	private String skuPropertiesName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name="order_id")
	public ShipOrder getOrder() {
		return order;
	}

	@ManyToOne
	@JoinColumn(name="item_id")
	public Item getItem() {
		return item;
	}

	@Transient
	public String getItemTitle() {
		return item.getTitle();
	}
	
	@Transient
	public String getItemCode() {
		return item.getCode();
	}
	
	public long getNum() {
		return num;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public void setOrder(ShipOrder entryOrder) {
		this.order = entryOrder;
	}

	public String getSkuPropertiesName() {
		if (skuPropertiesName == null) return "";
		return skuPropertiesName;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}
	
}
