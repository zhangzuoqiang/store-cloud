package com.graby.store.base.remote.client;

import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

public class ClientFactoryBean extends UrlBasedRemoteAccessor implements FactoryBean<Object>, InitializingBean {

	private boolean zip = false;

	private String type;

	private String hostUrl;

	private Object serviceProxy;

	public void setZip(boolean zip) {
		this.zip = zip;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		super.setServiceUrl(serviceUrl);
	}

	@SuppressWarnings("rawtypes")
	public void setServiceInterface(Class serviceInterface) {
		super.setServiceInterface(serviceInterface);
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public Object getObject() {
		return this.serviceProxy;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getObjectType() {
		return getServiceInterface();
	}

	public String getServiceUrl() {
		String url = StringUtils.isNotBlank(getHostUrl()) ? getHostUrl() + super.getServiceUrl() : super.getServiceUrl();
		return url;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setType(String type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() {
		try {
			if (type.equalsIgnoreCase(TYPE_HESSAIN)) {
				serviceProxy = ProxyFactory.getProxy(getServiceInterface(),
						ProxyBeanFactory.getHessianProxyFactoryBean(this.getServiceInterface(), this.getServiceUrl(), zip));
			} else if (type.equalsIgnoreCase(TYPE_HTTPINVOKER)) {
				serviceProxy = ProxyFactory.getProxy(getServiceInterface(),
						ProxyBeanFactory.getHttpInvoerProxyFactoryBean(this.getServiceInterface(), this.getServiceUrl(), zip));
			} else {
				throw new RuntimeException("不支持的类型: " + type);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private static final String TYPE_HTTPINVOKER = "httpinvoker";
	private static final String TYPE_HESSAIN = "hessian";

}

class ProxyBeanFactory {

	@SuppressWarnings("rawtypes")
	public static HessianProxyFactoryBean getHessianProxyFactoryBean(Class serviceInterface, String serverUrl, boolean zip)
			throws MalformedURLException {
		HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
		HessianExProxyFactory hessianProxyFactory = new HessianExProxyFactory();
		factory.setProxyFactory(hessianProxyFactory);
		factory.setServiceInterface(serviceInterface);
		factory.setServiceUrl(serverUrl);
		factory.prepare();
		return factory;
	}

	@SuppressWarnings("rawtypes")
	public static HttpInvokerProxyFactoryBean getHttpInvoerProxyFactoryBean(Class serviceInterface, String serverUrl, boolean zip) {
		HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
		HttpInvokerExRequestExecutor httpInvokerExRequestExecutor = new HttpInvokerExRequestExecutor();
		httpInvokerExRequestExecutor.setZip(zip);
		factory.setHttpInvokerRequestExecutor(httpInvokerExRequestExecutor);
		factory.setServiceInterface(serviceInterface);
		factory.setServiceUrl(serverUrl);
		return factory;
	}
}
