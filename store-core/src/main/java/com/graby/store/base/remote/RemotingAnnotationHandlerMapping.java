/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.graby.store.base.remote;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.base.remote.service.RemotingServiceExporter;


public class RemotingAnnotationHandlerMapping extends AbstractDetectingUrlHandlerMapping {

	private boolean useDefaultSuffixPattern = true;

	private final Map<Class<?>, RemotingService> cachedMappings = new HashMap<Class<?>, RemotingService>();


	/**
	 * Set whether to register paths using the default suffix pattern as well:
	 * i.e. whether "/users" should be registered as "/users.*" too.
	 * <p>Default is "true". Turn this convention off if you intend to interpret
	 * your <code>@RequestMapping</code> paths strictly.
	 * <p>Note that paths which include a ".xxx" suffix already will not be
	 * transformed using the default suffix pattern in any case.
	 */
	public void setUseDefaultSuffixPattern(boolean useDefaultSuffixPattern) {
		this.useDefaultSuffixPattern = useDefaultSuffixPattern;
	}


	/**
	 * Checks for presence of the {@link org.springframework.web.bind.annotation.RequestMapping}
	 * annotation on the handler class and on any of its methods.
	 */
	protected String[] determineUrlsForHandler(String beanName) {
		
		ApplicationContext context = getApplicationContext();
		Class<?> handlerType = context.getType(beanName);
		RemotingService mapping = AnnotationUtils.findAnnotation(handlerType, RemotingService.class);
		if (mapping == null && context instanceof ConfigurableApplicationContext &&
				context.containsBeanDefinition(beanName)) {
			ConfigurableApplicationContext cac = (ConfigurableApplicationContext) context;
			BeanDefinition bd = cac.getBeanFactory().getMergedBeanDefinition(beanName);
			if (bd instanceof AbstractBeanDefinition) {
				AbstractBeanDefinition abd = (AbstractBeanDefinition) bd;
				if (abd.hasBeanClass()) {
					Class<?> beanClass = abd.getBeanClass();
					mapping = AnnotationUtils.findAnnotation(beanClass, RemotingService.class);
				}
			}
		}

		if (mapping != null) {
			this.cachedMappings.put(handlerType, mapping);
			Set<String> urls = new LinkedHashSet<String>();
			String path = mapping.serviceUrl();
			if (path != null) {
				addUrlsForPath(urls, path);
				return StringUtils.toStringArray(urls);
			} else {
				return null;
			}
		}
		else {
			return null;
		}
	}	
	/**
	 * Add URLs and/or URL patterns for the given path.
	 * @param urls the Set of URLs for the current bean
	 * @param path the currently introspected path
	 */
	protected void addUrlsForPath(Set<String> urls, String path) {
		urls.add(path);
		if (this.useDefaultSuffixPattern && path.indexOf('.') == -1) {
			urls.add(path + ".*");
		}
	}
	
	// 转换成RemotingServiceExporter
	protected void registerHandler(String[] urlPaths, String beanName) throws BeansException, IllegalStateException {
		Assert.notNull(urlPaths, "URL path array must not be null");
		for (int j = 0; j < urlPaths.length; j++) {
			Object bean = getApplicationContext().getBean(beanName);
			registerHandler(urlPaths[j], createRemotingBean(bean));
		}
	}
	
	private Object createRemotingBean(Object bean) {
		Object resultBean = bean;
		RemotingService service = AnnotationUtils.findAnnotation(bean.getClass(), RemotingService.class);
		if (service != null) {
			RemotingServiceExporter serviceExporter = new RemotingServiceExporter();
			serviceExporter.setService(bean);
			serviceExporter.setServiceInterface(service.serviceInterface());
			serviceExporter.afterPropertiesSet();
			resultBean =serviceExporter;
		}
		return resultBean;
	}
}
