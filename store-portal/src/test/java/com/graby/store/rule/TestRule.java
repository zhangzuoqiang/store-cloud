package com.graby.store.rule;

import java.util.List;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.base.GroupMap;
import com.graby.store.entity.ShipOrder;

@ContextConfiguration(locations = { "/applicationContext-rule.xml" })
public class TestRule extends AbstractJUnit4SpringContextTests {

	@Autowired
	private StatefulKnowledgeSession ksession;
	
	private GroupMap<String, ShipOrder> results =new GroupMap<String,ShipOrder>();
	
	@Test
	public void test1() {
		ksession.setGlobal("results", results);
		ShipOrder o = new ShipOrder();
		o.setReceiverState("广东省");
		ShipOrder o2 = new ShipOrder();
		o2.setReceiverState("北京");
		ShipOrder o3 = new ShipOrder();
		o3.setReceiverState("新疆");
		
		ksession.insert(o);
		ksession.insert(o2);
		ksession.insert(o3);
		ksession.fireAllRules();
		System.out.println(o.getExpressCompany());
		System.out.println(results.getKeySet());
		
		for (String key : results.getKeySet()) {
			List<ShipOrder> list = results.getList(key);
			System.out.println(key + ":" + list);
		}
		ksession.dispose();
	}
	
}
