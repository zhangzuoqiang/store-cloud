package com.graby.store.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sc_item_mapping")
public class ItemMapping {

	private Long id;
	private Item item;
	private Long tbItemId;
	private String tbItemTitle;
	private String tbItemDetailurl;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="sc_item_id")
	public Item getItem() {
		return item;
	}

	public Long getTbItemId() {
		return tbItemId;
	}

	public String getTbItemTitle() {
		return tbItemTitle;
	}

	public String getTbItemDetailurl() {
		return tbItemDetailurl;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setTbItemId(Long tbItemId) {
		this.tbItemId = tbItemId;
	}

	public void setTbItemTitle(String tbItemTitle) {
		this.tbItemTitle = tbItemTitle;
	}

	public void setTbItemDetailurl(String tbItemDetailurl) {
		this.tbItemDetailurl = tbItemDetailurl;
	}
}
