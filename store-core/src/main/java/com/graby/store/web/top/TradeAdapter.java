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
import com.graby.store.service.item.ItemService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Order;

/**
 * 交易订单适配
 * @author huabiao.mahb
 */
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
	public Trade adapter(com.taobao.api.domain.Trade tTrade) throws ApiException {
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
		
		// -------- 主订单适配 -------- //
		
		// 订单基本信息
		trade.setTradeFrom(String.valueOf(tTrade.getTid()));
		trade.setPayTime(tTrade.getPayTime());
		trade.setShippingType(tTrade.getShippingType());
		trade.setType(tTrade.getType());
		trade.setStatus(tTrade.getStatus());
		trade.setLgAging(tTrade.getLgAging());
		trade.setLgAgingType(tTrade.getLgAgingType());
		
		// 收货方信息
		trade.setReceiverAddress(tTrade.getReceiverAddress());
		trade.setReceiverCity(tTrade.getReceiverCity());
		trade.setReceiverDistrict(tTrade.getReceiverDistrict());
		trade.setReceiverMobile(tTrade.getReceiverMobile());
		trade.setReceiverName(tTrade.getReceiverName());
		trade.setReceiverPhone(tTrade.getReceiverPhone());
		trade.setReceiverState(tTrade.getReceiverState());
		trade.setReceiverZip(tTrade.getReceiverZip());
		
		// 卖家和买家相关信息
		trade.setBuyerArea(tTrade.getBuyerArea());
		trade.setBuyerAlipayNo(tTrade.getAlipayNo());
		trade.setBuyerEmail(tTrade.getBuyerEmail());
		trade.setBuyerAlipayNo(tTrade.getBuyerAlipayNo());
		trade.setBuyerNick(tTrade.getBuyerNick());
		trade.setBuyerMemo(tTrade.getBuyerMemo());
		trade.setBuyerMessage(tTrade.getBuyerMessage());
		trade.setHasBuyerMessage(tTrade.getHasBuyerMessage());
		trade.setSellerMemo(tTrade.getSellerMemo());
		trade.setSellerPhone(tTrade.getSellerPhone());
		trade.setSellerMobile(tTrade.getSellerMobile());
		trade.setTid(tTrade.getTid());
		
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
			order.setBuyerNick(order.getBuyerNick());
			trade.addOrder(order);
		} else {
			for (Order order : orders) {
				Long skuId = StringUtils.isEmpty(order.getSkuId()) ? 0L : Long.valueOf(order.getSkuId());
				TradeOrder tradeOrder = new TradeOrder();
				tradeOrder.setBuyerNick(order.getBuyerNick());
				tradeOrder.setOrderFrom(order.getOrderFrom());
				tradeOrder.setNumIid(order.getNumIid());
				tradeOrder.setTitle(order.getTitle());
				tradeOrder.setAdjustFee(order.getAdjustFee());
				tradeOrder.setDiscountFee(order.getDiscountFee());
				tradeOrder.setTotalFee(order.getTotalFee());			
				tradeOrder.setNum(order.getNum());
				tradeOrder.setSkuId(skuId);
				tradeOrder.setItem(relatedItem(order.getNumIid(), skuId));
				tradeOrder.setSkuPropertiesName(order.getSkuPropertiesName());
				tradeOrder.setBuyerNick(trade.getBuyerNick());
				trade.addOrder(tradeOrder);
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
