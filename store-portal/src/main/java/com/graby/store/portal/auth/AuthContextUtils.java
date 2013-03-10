package com.graby.store.portal.auth;

import org.apache.shiro.SecurityUtils;

import com.graby.store.portal.auth.ShiroDbRealm.ShiroUser;

public class AuthContextUtils {
	
	public static ShiroUser getCurrentUser() {
		ShiroUser sUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return sUser;
	}
	
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getSessionKey() {
		try {
			return getCurrentUser().getSession();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getNickname() {
		try {
			return getCurrentUser().getUsername();
		} catch (Exception e) {
			return null;
		}
	}	
	
	public static Long getUserid() {
		try {
			return getCurrentUser().getUserid();
		} catch (Exception e) {
			return null;
		}
	}
}
