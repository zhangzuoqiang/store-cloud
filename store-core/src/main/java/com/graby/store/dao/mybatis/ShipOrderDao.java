package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrder;

@MyBatisRepository
public interface ShipOrderDao {
	
	void deleteOrder(Long orderId);
	void deleteDetailByOrderId(Long orderId);
	void deleteDetail(Long detailId);
	void sendEntryOrder(Long orderId);
	void setOrderStatus(Long orderId, String status);
	List<ShipOrder> findEntryOrderOnWay();
	List<ShipOrder> findSendOrderWaits();
	List<ShipOrder> findSendOrderSignWaits();
	public ShipOrder getShipOrderByTid(Long tid);
}
