package com.graby.store.portal.inventory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.graby.store.inventory.AccountTemplate;
import com.graby.store.inventory.InventoryService;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class InventoryServiceTest extends SpringTransactionalTestCase{
	
	@Autowired
	private InventoryService inventoryService;

	@Test
	public void testIncrease() {
		inventoryService.input(1L, 1L, 1L, 20, AccountTemplate.SHOP_SEND);
	}
	
	
}
