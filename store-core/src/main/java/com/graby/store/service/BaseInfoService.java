package com.graby.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.User;

@Component
@Transactional(readOnly = true)
public class BaseInfoService {
	
	@Autowired
	private UserDao userDao;
	
	public List<User> findAllUser() {
		return userDao.findAll();
	}
}
