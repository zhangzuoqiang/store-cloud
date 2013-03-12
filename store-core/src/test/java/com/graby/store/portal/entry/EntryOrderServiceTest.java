package com.graby.store.portal.entry;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.graby.store.entity.ShipOrder;
import com.graby.store.service.ShipOrderService;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class EntryOrderServiceTest extends SpringTransactionalTestCase{
	
	@Autowired
	private ShipOrderService service;
	
	@Test
	public void testFind() {
		List<ShipOrder> orders = service.findEntryOrderOnWay();
		for (ShipOrder entryOrder : orders) {
			System.out.println(entryOrder);
		}
	}
	
}
