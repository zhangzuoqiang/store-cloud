package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ShipOrder;
import com.graby.store.service.inventory.AccountEntryArray;
import com.graby.store.service.wms.ShipOrderService;
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
		shipOrderService.saveShipOrderDetail(orderId, itemId, num, null);
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
	public void recivedEntryOrder(Long id, List<AccountEntryArray> entrys) {
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
		return shipOrderService.getSendShipOrderByTradeId(tid);
	}

	@Override
	public ShipOrder submitSendOrder(ShipOrder order) throws ApiException {
		return shipOrderService.submitSendOrder(order);
	}

	@Override
	public ShipOrder signSendOrder(Long orderId) {
		return shipOrderService.closeOrder(orderId);
	}


	@Override
	public List<ShipOrder> findGroupSendOrderWaits(Long centroId) {
		return shipOrderService.findGroupSendOrderWaits(centroId);
	}
	
	@Override
	public void setSendOrderExpress(List<Map<String,String>> orderMaps) {
		shipOrderService.setSendOrderExpress(orderMaps);
	}

	@Override
	public List<ShipOrder> findSendOrderByStatus(Long centroId, String status) {
		return shipOrderService.findSendOrderByStatus(centroId, status);
	}

	@Override
	public void reExpressShipOrder(Long[] orderids) {
		shipOrderService.reExpressShipOrder(orderids);
	}	
	
	@Override
	public List<ShipOrder> findSendOrderByQ(String q) {
		return shipOrderService.findSendOrderByQ(q);
	}	

	@Override
	public List<ShipOrder> findSendOrders(Long[] orderIds) {
		return shipOrderService.findSendOrders(orderIds);
	}
	
	
	@Override
	public void submits(Long[] orderids) {
		shipOrderService.submits(orderids);
	}

	@Override
	public void chooseExpress(Long orderId, String expressCompany) {
		shipOrderService.chooseExpress(orderId, expressCompany);
	}

	@Override
	public List<Map<String, Object>> findSendOrdersGroup(Long[] orderIds) {
		return shipOrderService.findSendOrdersGroup(orderIds);
	}

}
