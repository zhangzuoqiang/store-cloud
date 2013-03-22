package com.graby.store.base.remote.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 远程服务端导航器
 * 
 * @author colt
 */
public class RemotingServiceExporter extends RemoteExporter implements Controller, InitializingBean {

	private HttpInvokerServiceExporter httpInvoker = new HttpInvokerServiceExporter();

	public void setService(Object service) {
		super.setService(service);
		httpInvoker.setService(service);
	}

	public void setServiceInterface(Class serviceInterface) {
		super.setServiceInterface(serviceInterface);
		httpInvoker.setServiceInterface(serviceInterface);
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpRequestHandler handle = getHandle(request);
		handle.handleRequest(request, response);
		return null;
	}

	private HttpRequestHandler getHandle(HttpServletRequest request) {
		return httpInvoker;
	}

	public void afterPropertiesSet() {
		httpInvoker.afterPropertiesSet();
	}
}
