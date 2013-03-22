package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * 商品库存
 */
@Entity
@Table(name = "sc_item_inventory")
public class ItemInventory implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1962047713857439779L;
	// 序号
	private Long id;
	// 商品
	private Item item;
	// 库存科目
	private String account;
	// 数量
	private int num;
	// 所属用户
	private User user;
	// 所属仓库
	private Centro centro;

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

	@Index(name="idx_account")
	public String getAccount() {
		return account;
	}

	public int getNum() {
		return num;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="centro_id")
	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

}
