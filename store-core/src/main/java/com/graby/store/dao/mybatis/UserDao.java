package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.User;

@MyBatisRepository
public interface UserDao {

	User findUserByUsername(String username);
	User get(long id);
	void save(User user);
	void delete(Long id);
	List<User> findAll();
	
}
