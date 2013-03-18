package com.graby.store.portal.trade;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.graby.store.dao.mybatis.TradeDao;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TradeDaoTest extends SpringTransactionalTestCase {
	
	@Autowired
	private TradeDao tradeDao;
	
	@Test
	public void test1() {
		System.out.println(tradeDao.getTotalResults(2L, "%%"));
		System.out.println(tradeDao.getTrades(2L, "%%", 0, 2));
	}
	
}
