package com.graby.store.remote;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrder;

@ContextConfiguration(locations = { "/applicationContext-client.xml" })
public class TestRemote extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private CentroRemote centroRemote;
	
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
	@Test
	public void testFindAll() {
		List<Centro> centros = centroRemote.findCentros();
		for (Centro centro : centros) {
			System.out.println(centro.getAddress());
		}
	}
	
	@Test
	public void testFindOrder() {
		List<ShipOrder> orderMap = shipOrderRemote.findGroupSendOrderWaits(1L);
		for (ShipOrder shipOrder : orderMap) {
			System.out.println(shipOrder.getItems());
		}
	}
	
}
