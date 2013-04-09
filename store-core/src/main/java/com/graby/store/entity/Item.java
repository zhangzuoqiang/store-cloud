package com.graby.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "sc_item")
public class Item implements Serializable {

	private static final long serialVersionUID = 981755267881595757L;

	public final String TYPE_NORMAL = "normal";

	private Long id;
	private String code;
	private Long userid;
	private String title;
	private Long weight;
	private String type;
	private String sku;
	private String position;
	private String description;
	private List<ItemMapping> mapping = new ArrayList<ItemMapping>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToMany(mappedBy = "item")
	public List<ItemMapping> getMapping() {
		return mapping;
	}

	@Index(name = "idx_userid")
	public Long getUserid() {
		return userid;
	}

	@Index(name = "idx_code")
	public String getCode() {
		return code;
	}

	@Index(name = "idx_type")
	public String getType() {
		return type;
	}

	@Index(name = "idx_title")
	public String getTitle() {
		return title;
	}
	
	@Index(name = "idx_position")
	public String getPosition() {
		return position;
	}

	public Long getWeight() {
		return weight;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMapping(List<ItemMapping> tbItemMapping) {
		this.mapping = tbItemMapping;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
