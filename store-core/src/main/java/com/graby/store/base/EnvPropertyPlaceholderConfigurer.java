package com.graby.store.base;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Environment PropertyPlaceholderConfigurer.
 * 
 * Example properties file:
 * 
 * dev.prop1=value1 
 * DEV.prop2=value2 
 * TEST.prop1=value1 
 * TEST.prop2=value2
 * online.prop1=value1 
 * ONLINE.prop2=value2
 * 
 * @author huabiao.mahb
 */
public class EnvPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {


	private String curMode = null;

	@Override
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
		String propVal = null;
		if (curMode != null) {
			String replaceHolder = placeholder;
			if (curMode.equalsIgnoreCase("online")) {
				replaceHolder = "online." + placeholder;
			} else if (curMode.equalsIgnoreCase("test")) {
				replaceHolder = "test." + placeholder;
			} else if (curMode.equalsIgnoreCase("dev")) {
				replaceHolder = "dev." + placeholder;
			}
			propVal = super.resolvePlaceholder(replaceHolder, props, systemPropertiesMode);
		}
		
		if (propVal == null) {
			propVal = super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
		}
		
		// default dev mode
		if (propVal == null) {
			placeholder = "dev." + placeholder;
			propVal = super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
		}
		return propVal;
	}
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		curMode = resolveSystemProperty("mode");		
		super.processProperties(beanFactoryToProcess, props);
	}
	
}
