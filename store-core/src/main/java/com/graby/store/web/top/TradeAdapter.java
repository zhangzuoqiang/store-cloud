package com.graby.store.web.top;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.service.ItemService;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;

@Component
public class TradeAdapter {
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 将淘宝交易订单适配成本地订单结构
	 * TODO 商品
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
				Long skuId = StringUtils.isEmpty(order.getSkuId()) ? 0L : Long.valueOf(order.getSkuId());
				TradeOrder localOrder = new TradeOrder();
				localOrder.setBuyerNick(order.getBuyerNick());
				localOrder.setOrderFrom(order.getOrderFrom());
				localOrder.setNumIid(order.getNumIid());
				localOrder.setTitle(order.getTitle());
				localOrder.setAdjustFee(order.getAdjustFee());
				localOrder.setDiscountFee(order.getDiscountFee());
				localOrder.setTotalFee(order.getTotalFee());			
				localOrder.setNum(order.getNum());
				localOrder.setSkuId(skuId);
				localOrder.setItem(relatedItem(order.getNumIid(), skuId));
				localOrder.setSkuPropertiesName(order.getSkuPropertiesName());
				trade.addOrder(localOrder);
			}
			
		}
		return trade;
	}
	
	private com.graby.store.entity.Item relatedItem(Long numIid, Long skuId) {
		com.graby.store.entity.Item e = new com.graby.store.entity.Item();
		Long itemId = itemService.getRelatedItemId(numIid, skuId);
		e.setId(itemId);
		return e;
	}
}
