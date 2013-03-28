package com.graby.store.remote;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.graby.store.entity.User;
import com.graby.store.remote.AuthRemote;

public class TestHttpInvoker {
	public static void main(String[] args) {
		t1();
	}

	private static void t1() {
		HttpInvokerProxyFactoryBean bean = new HttpInvokerProxyFactoryBean();
		bean.setServiceUrl("http://127.0.0.1/auth.call");
		bean.setServiceInterface(AuthRemote.class);
		bean.afterPropertiesSet();
		AuthRemote remote = (AuthRemote) bean.getObject();
		User user = remote.findUserByUsername("admin");
		System.out.println(user);
	}
}
