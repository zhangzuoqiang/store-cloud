package com.graby.store.base.remote.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

	public static final String REMOTE_EXPORTER = "RemoteExporter";
	public static final String HESSIAN_SERVICE_EXPORTER = "HessianServiceExporter";
	public static final String HTTP_INVOKER_SERVICE_EXPORTER = "HttpInvokerServiceExporter";

	private HttpInvokerServiceExporter httpInvoker = new HttpInvokerServiceExporter();
	private HessianServiceExporter hessian = new HessianServiceExporter();

	public void setService(Object service) {
		super.setService(service);
		httpInvoker.setService(service);
		hessian.setService(service);
	}

	@SuppressWarnings("rawtypes")
	public void setServiceInterface(Class serviceInterface) {
		super.setServiceInterface(serviceInterface);
		httpInvoker.setServiceInterface(serviceInterface);
		hessian.setServiceInterface(serviceInterface);
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpRequestHandler handle = getHandle(request);
		handle.handleRequest(request, response);
		return null;
	}

	private HttpRequestHandler getHandle(HttpServletRequest request) {
		String remotingType = request.getHeader(REMOTE_EXPORTER);
		if (StringUtils.isNotEmpty(remotingType)) {
			if (remotingType.equalsIgnoreCase(HESSIAN_SERVICE_EXPORTER)) {
				return hessian;
			} else if (remotingType.equalsIgnoreCase(HTTP_INVOKER_SERVICE_EXPORTER)) {
				return httpInvoker;
			}
		}
		return httpInvoker;
	}

	public void afterPropertiesSet() {
		httpInvoker.afterPropertiesSet();
		hessian.afterPropertiesSet();
	}
}
