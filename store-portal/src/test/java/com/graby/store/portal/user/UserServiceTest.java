package com.graby.store.portal.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.graby.store.entity.User;
import com.graby.store.portal.service.AuthService;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserServiceTest extends SpringTransactionalTestCase {
	
	@Autowired
	private AuthService userService;
	
	@Test
	@Rollback(false)
	public void testRegister() {
		User user = new User();
		user.setUsername("admin");
		user.setPlainPassword("admin");
		user.setPassword("admin");
		user.setRoles("admin");
		user.setDescription("超级管理员");
		userService.registerUser(user);
	}
}
