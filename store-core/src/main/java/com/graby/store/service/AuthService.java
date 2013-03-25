package com.graby.store.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.base.ServiceException;
import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.User;
import com.graby.store.util.Digests;
import com.graby.store.util.Encodes;
import com.graby.store.util.EncryptUtil;
import com.graby.store.web.auth.ShiroDbRealm.ShiroUser;

@Component
@Transactional(readOnly = true)
public class AuthService {
	
	private static Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 同步淘宝用户
	 * @param nick
	 * @return
	 */
	public Long addUserIfNecessary(String nick, String shopName) {
		User user = userDao.findUserByUsername(nick);
		if (user == null) {
			User newUser = new User();
			newUser.setShopName(shopName);
			newUser.setRoles("user");
			newUser.setUsername(nick);
			String passwd = EncryptUtil.md5(nick);
			newUser.setPlainPassword(passwd);
			entryptPassword(newUser);
			newUser.setDescription("淘宝卖家账号");
			userDao.save(newUser);
			return newUser.getId();
		}
		return user.getId();
	}
	
	@Transactional(readOnly = false)
	public void registerUser(User user) {
		entryptPassword(user);
		if (user.getRoles() == null) {
			user.setRoles("user");
		}
		userDao.save(user);
	}
	
	@Transactional(readOnly = false)
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}
	
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public User getUser(Long id) {
		return userDao.get(id);
	}

	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}


	@Transactional(readOnly = false)
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.getUsername();
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
}
