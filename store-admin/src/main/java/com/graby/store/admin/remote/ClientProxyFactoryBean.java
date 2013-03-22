package com.graby.store.admin.remote;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

public class ClientProxyFactoryBean extends UrlBasedRemoteAccessor implements FactoryBean, InitializingBean {

	private String hostUrl;

	private Object serviceProxy;

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		super.setServiceUrl(serviceUrl);
	}

	public void setServiceInterface(Class serviceInterface) {
		super.setServiceInterface(serviceInterface);
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public Object getObject() {
		return this.serviceProxy;
	}

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

	public void afterPropertiesSet() {
		serviceProxy = ProxyFactory.getProxy(getServiceInterface(),
				ProxyBeanFactory.getHttpInvoerProxyFactoryBean(this.getServiceInterface(), this.getServiceUrl()));
	}

}

class ProxyBeanFactory {

	public static HttpInvokerProxyFactoryBean getHttpInvoerProxyFactoryBean(Class serviceInterface, String serverUrl) {
		HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
		HttpInvokerRequestExecutor httpInvokerExRequestExecutor = new SimpleHttpInvokerRequestExecutor();
		factory.setHttpInvokerRequestExecutor(httpInvokerExRequestExecutor);
		factory.setServiceInterface(serviceInterface);
		factory.setServiceUrl(serverUrl);
		return factory;
	}
}
