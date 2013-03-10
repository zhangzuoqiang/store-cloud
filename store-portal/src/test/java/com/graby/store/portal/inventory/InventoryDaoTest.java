//package com.graby.store.portal.inventory;
//
//import java.util.Map;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springside.modules.test.spring.SpringTransactionalTestCase;
//
//import com.graby.store.dao.mybatis.InventoryDao;
//
//@ContextConfiguration(locations = { "/applicationContext.xml" })
//public class InventoryDaoTest extends SpringTransactionalTestCase{
//	
//	@Autowired
//	private InventoryDao inventoryDao;
//
//	@Test
//	public void testStat() {
//		Map<String, Long> result = inventoryDao.statByUser(1L, 1L);
//		System.out.println(result);
//	}
//
//	
//}
