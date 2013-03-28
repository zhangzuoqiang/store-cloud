package com.graby.store.remote;

import com.graby.store.entity.User;

public interface AuthRemote {

	/**
	 * 查询用户
	 * @param username 用户名
	 * @return 用户
	 */
	public User findUserByUsername(String username);

}