package com.graby.store.remote;

import com.graby.store.entity.User;

public interface AuthRemote {

	public User findUserByUsername(String username);

}