package com.graby.store.shiporder;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.entity.User;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ShipOrderService;

@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-rule.xml" })
public class TestShipOrderService extends AbstractJUnit4SpringContextTests {
	
	
	@Autowired
	private TradeService tradeService;
	
	@Test
	@Rollback(false)
	public void batchAdd() {
		
		for (int i = 0; i < 3000; i++) {
			int j = new Random().nextInt(states.length);
			Trade trade = createTrade(states[j]);
			trade = tradeService.createTrade(trade, null);
			tradeService.createSendShipOrderByTradeId(trade.getId());
		}
		
	}
	
	
	private Trade createTrade(String state) {
		Trade trade = new Trade();
		
		Centro centro =new Centro();
		centro.setId(2L);
		trade.setCentro(centro);
		
		User user = new User();
		user.setId(9L);
		trade.setUser(user);
		
		trade.setShippingType("express");
		trade.setReceiverState(state);
		trade.setPayTime(new Date());
		trade.setReceiverMobile("13588889999");
		trade.setReceiverPhone("0732-87622999");
		trade.setReceiverName("李小姐");
		trade.setReceiverZip("411101");
		trade.setReceiverCity("某某市");
		trade.setReceiverDistrict("某某区");
		trade.setReceiverAddress("时代广场星光大道11号 江南名苑");
		trade.setBuyerMemo("给我39加半码");
		
		TradeOrder o1 = new TradeOrder();
		Item i1 = new Item();
		i1.setId(10L);
		o1.setItem(i1);
		o1.setNum(1);
		
		TradeOrder o2 = new TradeOrder();
		Item i2 = new Item();
		i2.setId(11L);
		o2.setItem(i2);
		o2.setNum(1);		
		
		trade.addOrder(o1);
		trade.addOrder(o2);
		
		return trade;
	}
	
	static String[] states = {"湖南省", "广东省", "浙江省", "江苏省", "北京", "深圳", "上海", "广西省", "台湾", "江西省", "陕西省", "湖北省", "南京"};
	
	public static void main(String[] args) {
		for (String string : states) {
			System.out.println("receiverState not contains " + string + ",");
		}
	}
}
