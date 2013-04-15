package com.graby.store.remote;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.User;
import com.graby.store.service.base.AuthService;

@RemotingService(serviceInterface = AuthRemote.class, serviceUrl = "/auth.call")
public class AuthRemoteImpl implements AuthRemote {

	@Autowired
	private AuthService authService;

	public User findUserByUsername(String username) {
		return authService.findUserByUsername(username);
	}

}