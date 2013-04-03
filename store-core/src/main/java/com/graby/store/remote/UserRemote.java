package com.graby.store.remote;

import java.util.List;

import com.graby.store.entity.User;

public interface UserRemote {
	
	public List<User> findAll();
	
}
