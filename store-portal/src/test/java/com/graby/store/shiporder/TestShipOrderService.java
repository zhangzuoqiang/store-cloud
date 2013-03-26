package com.graby.store.shiporder;

import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.entity.ShipOrder;
import com.graby.store.service.ShipOrderService;

@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-rule.xml" })
public class TestShipOrderService extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Test
	@Rollback(false)
	public void batchAdd() {
		
		for (int i = 0; i < 6000; i++) {
			ShipOrder shipOrder = new ShipOrder();
			int j = new Random().nextInt(states.length);
			shipOrder.setReceiverState(states[j]);
			shipOrderService.createSendShipOrder(shipOrder);	
		}
		
	}
	
	static String[] states = {"湖南省", "广东省", "浙江省", "江苏省", "北京", "深圳", "上海", "广西省", "台湾"};
	
}
