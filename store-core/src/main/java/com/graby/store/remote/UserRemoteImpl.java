package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.User;
import com.graby.store.service.base.UserService;

@RemotingService(serviceInterface = UserRemote.class, serviceUrl = "/user.call")
public class UserRemoteImpl implements UserRemote {
	
	@Autowired
	private UserService userService;

	@Override
	public List<User> findAll() {
		return userService.findAll();
	}

}
