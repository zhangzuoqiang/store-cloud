package com.graby.store.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sc_item")
public class Item {

	public final String TYPE_NORMAL="normal";
	
	private Long id;
	private String code;
	private Long userid;
	private String title;
	private Long weight;
	private String type;
	private String description;
	private Set<ItemMapping> mapping = new HashSet<ItemMapping>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToMany(mappedBy="item")
	public Set<ItemMapping> getMapping() {
		return mapping;
	}	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMapping(Set<ItemMapping> tbItemMapping) {
		this.mapping = tbItemMapping;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	

}
