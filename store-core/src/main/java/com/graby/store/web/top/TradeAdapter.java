package com.graby.store.web.top;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.entity.Centro;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.entity.User;
import com.graby.store.service.ItemService;
import com.graby.store.web.auth.ShiroContextUtils;
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
	public Trade adapterFromTop(com.taobao.api.domain.Trade tTrade) throws ApiException {
		Trade trade = new Trade();
		Long userid = ShiroContextUtils.getUserid();
		if (userid != null) {
			User user = new User();
			user.setId(userid);
			trade.setUser(user);
		}
		// 目前只支持单仓库
		Centro centro =new Centro();
		centro.setId(1L);
		trade.setCentro(centro);
		
		// 主订单适配
		trade.setTradeFrom(tTrade.getTradeFrom());
		trade.setTid(tTrade.getTid());
		trade.setBuyerNick(tTrade.getBuyerNick());
		trade.setBuyerEmail(tTrade.getBuyerEmail());
		trade.setBuyerAlipayNo(tTrade.getBuyerAlipayNo());
		trade.setPayTime(tTrade.getPayTime());
		trade.setBuyerArea(tTrade.getBuyerArea());
		trade.setShippingType(tTrade.getShippingType());
		trade.setBuyerMemo(tTrade.getBuyerMemo());
		trade.setReceiverAddress(tTrade.getReceiverAddress());
		trade.setReceiverCity(tTrade.getReceiverCity());
		trade.setReceiverDistrict(tTrade.getReceiverDistrict());
		trade.setReceiverMobile(tTrade.getReceiverMobile());
		trade.setReceiverName(tTrade.getReceiverName());
		trade.setReceiverPhone(tTrade.getReceiverPhone());
		trade.setReceiverState(tTrade.getReceiverState());
		trade.setReceiverZip(tTrade.getReceiverZip());
		trade.setLgAging(tTrade.getLgAging());
		trade.setLgAgingType(tTrade.getLgAgingType());
		trade.setBuyerMessage(tTrade.getBuyerMessage());
		trade.setHasBuyerMessage(tTrade.getHasBuyerMessage());
		
		// 子订单适配
		List<Order> orders = tTrade.getOrders();
		if (CollectionUtils.isEmpty(orders)) {
			TradeOrder order = new TradeOrder();
			Item item = topApi.getItem(tTrade.getNumIid());
			order.setTitle(item.getTitle());
			order.setOrderFrom(tTrade.getTradeFrom());
			order.setNumIid(tTrade.getNumIid());
			order.setAdjustFee(tTrade.getAdjustFee());
			order.setDiscountFee(tTrade.getDiscountFee());
			order.setTotalFee(tTrade.getTotalFee());			
			order.setNum(tTrade.getNum());
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
