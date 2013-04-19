//package com.graby.store.remote;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//@ContextConfiguration(locations = { "/applicationContext-client.xml" })
//public class TestShipOrder extends AbstractJUnit4SpringContextTests {
//
//	@Autowired
//	private ShipOrderRemote shipOrderRemote;
//
//	@Test
//	public void testSetExpress() {
//		Map<String,String> foo1 = gene("18", "SF", "9000001");
//		Map<String,String> foo2 = gene("19", "SF", "9000002");
//		Map<String,String> foo3 = gene("20", "SF", "9000003");
//		List list = new ArrayList();
//		list.add(foo1);
//		list.add(foo2);
//		list.add(foo3);
//		shipOrderRemote.setSendOrderExpress(list);
//	}
//
//	private Map<String, String> gene(String orderId, String expressCompany, String expressOrderno) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("id", orderId);
//		map.put("expressCompany", expressCompany);
//		map.put("expressOrderno", expressOrderno);
//		return map;
//	}
//}
