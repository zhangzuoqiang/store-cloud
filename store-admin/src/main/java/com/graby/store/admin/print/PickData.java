package com.graby.store.admin.print;
import java.util.List;

import com.graby.store.base.remote.client.ClientFactoryBean;
import com.graby.store.entity.ShipOrder;
import com.graby.store.remote.ShipOrderRemote;


public class PickData {
	
	public PickData() {
		init();
	}
	
	private void init() {
		ClientFactoryBean remoteFactoryBean = new ClientFactoryBean();
		remoteFactoryBean.setHostUrl("http://www.wlpost.com/");
		remoteFactoryBean.setServiceInterface(ShipOrderRemote.class);
		remoteFactoryBean.setServiceUrl("ship.call");
		remoteFactoryBean.setType("httpinvoker");
		remoteFactoryBean.afterPropertiesSet();
		shipOrderRemote = (ShipOrderRemote)remoteFactoryBean.getObject();
	}
	
	private ShipOrderRemote shipOrderRemote;
	
	public List<ShipOrder> findWaitPickingOrders(Long centroId) {
		return shipOrderRemote.findSendOrderByStatus(1L, ShipOrder.SendOrderStatus.WAIT_EXPRESS_PICKING);
	}
	
	public static void main(String[] args) {
		PickData data = new PickData();
		System.out.println(data.findWaitPickingOrders(1L));
	}
}
