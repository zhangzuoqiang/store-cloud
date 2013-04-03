package com.graby.store.remote;

import com.graby.store.entity.User;

/**
 * 验证服务
 * serviceUrl = "/auth.call"
 */
public interface AuthRemote {

	/**
	 * 查询用户
	 * @param username 用户名
	 * @return 用户
	 */
	public User findUserByUsername(String username);
	
}