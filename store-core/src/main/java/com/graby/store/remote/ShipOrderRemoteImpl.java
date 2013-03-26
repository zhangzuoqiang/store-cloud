package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.GroupMap;
import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ShipOrder;
import com.graby.store.service.InvAccountEntrys;
import com.graby.store.service.ShipOrderService;
import com.taobao.api.ApiException;

@RemotingService(serviceInterface = ShipOrderRemote.class, serviceUrl = "/ship.call")
public class ShipOrderRemoteImpl implements ShipOrderRemote {
	
	@Autowired
	private	ShipOrderService shipOrderService;
	
	@Override
	public List<ShipOrder> findEntryOrderOnWay() {
		return shipOrderService.findEntryOrderOnWay();
	}

	@Override
	public void saveEntryOrder(ShipOrder order) {
		shipOrderService.saveEntryOrder(order);
	}

	@Override
	public void saveShipOrderDetail(Long orderId, Long itemId, long num) {
		shipOrderService.saveShipOrderDetail(orderId, itemId, num);
	}

	@Override
	public void deleteShipOrder(Long orderId) {
		shipOrderService.deleteShipOrder(orderId);
	}

	@Override
	public void deleteShipOrderDetail(Long id) {
		shipOrderService.deleteShipOrderDetail(id);
	}

	@Override
	public Page<ShipOrder> findEntrys(Long userid, String status, int page, int pageSize) {
		return shipOrderService.findEntrys(userid, status, page, pageSize);
	}

	@Override
	public ShipOrder getShipOrder(Long id) {
		return shipOrderService.getShipOrder(id);
	}

	@Override
	public boolean sendEntryOrder(Long id) {
		return shipOrderService.sendEntryOrder(id);
	}

	@Override
	public void recivedEntryOrder(Long id, List<InvAccountEntrys> entrys) {
		shipOrderService.recivedEntryOrder(id, entrys);
	}

	@Override
	public List<ShipOrder> findSendOrderWaits() {
		return shipOrderService.findSendOrderWaits();
	}

	@Override
	public List<ShipOrder> findSendOrderSignWaits() {
		return shipOrderService.findSendOrderSignWaits();
	}

	@Override
	public ShipOrder getShipOrderByTid(Long tid) {
		return shipOrderService.getShipOrderByTid(tid);
	}

	@Override
	public void createSendShipOrder(ShipOrder shipOrder) {
		shipOrderService.createSendShipOrder(shipOrder);
	}

	@Override
	public ShipOrder submitSendOrder(ShipOrder order) throws ApiException {
		return shipOrderService.submitSendOrder(order);
	}

	@Override
	public ShipOrder signSendOrder(Long orderId) {
		return shipOrderService.signSendOrder(orderId);
	}

	@Override
	public void updateShipOrder(ShipOrder order) {
		shipOrderService.updateShipOrder(order);		
	}

	@Override
	public GroupMap<String, ShipOrder> findGroupSendOrderWaits() {
		return shipOrderService.findGroupSendOrderWaits();
	}

}
