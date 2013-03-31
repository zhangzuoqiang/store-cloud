package com.graby.store.top;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.web.top.TopApi;
import com.taobao.api.ApiException;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TopAPITest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private TopApi topApi;
	
	@Test
	public void testItemGet() throws ApiException {
		topApi.getItems("", 20);
	}
}
