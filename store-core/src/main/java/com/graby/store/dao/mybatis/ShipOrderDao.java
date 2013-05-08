package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrder;

@MyBatisRepository
public interface ShipOrderDao {

	ShipOrder getSendShipOrderByTradeId(Long tid);
	ShipOrder getShipOrder(Long id);
	Long getSendOrderIdByTradeId(Long tradeId);
	
	void deleteOrder(Long orderId);
	void deleteDetailByOrderId(Long orderId);
	void deleteDetail(Long detailId);
	void sendEntryOrder(Long orderId);
	void setOrderStatus(Long orderId, String status);
	void setTradeStatus(Long orderId, String status);
	void setSendOrderExpress(Map<String,String> orders);
	void chooseExpress(Long orderId, String expressCompany);
	void increaseEntryOrderDetail(Long detailId, long num);
	
	Long getEntryOrderDetail(Long orderId, Long itemId);
	
	
	List<ShipOrder> findEntryOrderOnWay();
	List<ShipOrder> findSendOrderWaits(Long centroId, int rownum);
	List<ShipOrder> findSendOrderByStatus(Long centroId, String status, int rownum);
	List<ShipOrder> findSendOrderSignWaits();
	List<ShipOrder> findSendOrders(Long[] orderIds);
	List<Map<String,Object>> findSendOrdersGroup(Long[] orderIds);
	List<ShipOrder> findSendOrderByQ(String q);
	
}
