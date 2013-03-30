package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrder;

@MyBatisRepository
public interface ShipOrderDao {

	public ShipOrder getShipOrderByTid(Long tid);
	public ShipOrder getShipOrder(Long id);
	
	void deleteOrder(Long orderId);
	void deleteDetailByOrderId(Long orderId);
	void deleteDetail(Long detailId);
	void sendEntryOrder(Long orderId);
	void setOrderStatus(Long orderId, String status);
	void setSendOrderExpress(Map<String,String> orders);
	
	List<ShipOrder> findEntryOrderOnWay();
	List<ShipOrder> findSendOrderWaits(Long centroId, int rownum);
	List<ShipOrder> findSendOrderSignWaits();

	
}
