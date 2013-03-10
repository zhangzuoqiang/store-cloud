package com.graby.store.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sc_ship_order_detail")
public class ShipOrderDetail {

	private Long id;

	private ShipOrder order;

	private Item item;

	private long num;
	
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
	
}
