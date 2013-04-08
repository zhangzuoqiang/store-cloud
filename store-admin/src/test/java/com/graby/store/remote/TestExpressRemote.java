package com.graby.store.remote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "/applicationContext-client.xml" })
public class TestExpressRemote extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private ExpressRemote expressRemote;
	
	@Test
	public void testFindAll() {
		System.out.println(expressRemote.validate("SF", "123331123"));
		System.out.println(expressRemote.getExpressMap());
		System.out.println(expressRemote.getExpressCompanyName("SF"));
	}
	
}
