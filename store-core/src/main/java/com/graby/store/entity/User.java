package com.graby.store.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Index;

import com.google.common.collect.ImmutableList;

/**
 * 内部用户
 * 
 * @author huabiao.mahb
 */

@Entity
@Table(name = "sc_user")
public class User implements Serializable {

	private static final long serialVersionUID = 2617282959202191953L;
	
	private Long id;
	private String username;
	private String password;
	private String plainPassword;
	private String salt;
	private String roles;
	private String description;
	
	// 店铺名称
	private String shopName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Transient
	public List<String> getRoleList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(roles, ","));
	}

	@Index(name="idx_username")
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	@Index(name="idx_roles")
	public String getRoles() {
		return roles;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
