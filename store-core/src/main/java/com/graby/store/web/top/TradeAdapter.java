package com.graby.store.web.top;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;

public class TradeAdapter {
	
	@Autowired
	private TopApi topApi;
	
	/**
	 * 将淘宝交易订单适配成本地订单结构
	 * @param trade
	 * @return
	 * @throws ApiException 
	 */
	public Trade adapterFromTop(com.taobao.api.domain.Trade from) throws ApiException {
		Trade trade = new Trade();
		// 主订单适配
		trade.setTradeFrom(from.getTradeFrom());
		trade.setTid(from.getTid());
		trade.setBuyerNick(from.getBuyerNick());
		trade.setBuyerEmail(from.getBuyerEmail());
		trade.setBuyerAlipayNo(from.getBuyerAlipayNo());
		trade.setPayTime(from.getPayTime());
		trade.setBuyerArea(from.getBuyerArea());
		trade.setShippingType(from.getShippingType());
		trade.setBuyerMemo(from.getBuyerMemo());
		trade.setReceiverAddress(from.getReceiverAddress());
		trade.setReceiverCity(from.getReceiverCity());
		trade.setReceiverDistrict(from.getReceiverDistrict());
		trade.setReceiverMobile(from.getReceiverMobile());
		trade.setReceiverName(from.getReceiverName());
		trade.setReceiverPhone(from.getReceiverPhone());
		trade.setReceiverState(from.getReceiverState());
		trade.setReceiverZip(from.getReceiverZip());
		trade.setLgAging(from.getLgAging());
		trade.setLgAgingType(from.getLgAgingType());
		trade.setBuyerMessage(from.getBuyerMessage());
		trade.setHasBuyerMessage(from.getHasBuyerMessage());
		
		// 子订单适配
		List<Order> orders = from.getOrders();
		if (CollectionUtils.isEmpty(orders)) {
			TradeOrder order = new TradeOrder();
			Item item = topApi.getItem(from.getNumIid());
			order.setTitle(item.getTitle());
			order.setOrderFrom(from.getTradeFrom());
			order.setNumIid(from.getNumIid());
			order.setAdjustFee(from.getAdjustFee());
			order.setDiscountFee(from.getDiscountFee());
			order.setTotalFee(from.getTotalFee());			
			order.setNum(from.getNum());
			trade.addOrder(order);
		} else {
			for (Order order : orders) {
				TradeOrder localOrder = new TradeOrder();
				localOrder.setBuyerNick(order.getBuyerNick());
				localOrder.setOrderFrom(order.getOrderFrom());
				localOrder.setNumIid(order.getNumIid());
				localOrder.setTitle(order.getTitle());
				localOrder.setAdjustFee(order.getAdjustFee());
				localOrder.setDiscountFee(order.getDiscountFee());
				localOrder.setTotalFee(order.getTotalFee());			
				localOrder.setNum(order.getNum());
				localOrder.setSkuId(order.getSkuId());
				localOrder.setSkuPropertiesName(order.getSkuPropertiesName());
				trade.addOrder(localOrder);
			}
			
		}
		return trade;
	}
}
