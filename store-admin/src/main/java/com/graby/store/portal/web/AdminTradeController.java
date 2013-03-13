package com.graby.store.portal.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.graby.store.common.Express;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.inventory.Accounts;
import com.graby.store.inventory.InventoryService;
import com.graby.store.service.ItemService;
import com.graby.store.service.ShipOrderService;
import com.graby.store.service.TradeService;
import com.taobao.api.ApiException;


@Controller
@RequestMapping(value = "/trade/")
public class AdminTradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	/**
	 * 查询所有待处理订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "waits", method=RequestMethod.GET)
	public String waits(Model model) throws ApiException {
		List<Trade> trades = tradeService.findWaitAuditTrades();
		model.addAttribute("trades", trades);
		return "/admin/tradeWaits";
	}
	
	/**
	 * 审核订单页面
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "audit/{id}", method=RequestMethod.GET)
	public String audit(@PathVariable("id") Long id, Model model) throws ApiException {
		Trade trade = tradeService.getTrade(id);
		List<TradeOrder> orders = trade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long itemId = order.getItem().getId();
			if (itemId == null) {
				order.setStockNum(-1);
			} else {
				long stockNum = inventoryService.getValue(1L, itemId, Accounts.CODE_SALEABLE);
				order.setStockNum(stockNum);
				Item item = itemService.getItem(itemId);
				order.setItem(item);
			}
		}
		model.addAttribute("trade", trade);
		return "/admin/tradeAudit";
	}
	
	/**
	 * 审核通过，创建出库单。
	 */
	@RequestMapping(value = "mkship/{id}")
	public String ship(@PathVariable("id") Long id, Model model) {
		ShipOrder sendOrder = tradeService.createSendShipOrderByTradeId(id);
		model.addAttribute("sendOrder", sendOrder);
		return "redirect:/admin/trade/send/do/" + sendOrder.getId();
	}
	
	/**
	 * 查询所有待处理出库单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/waits", method=RequestMethod.GET)
	public String sendWaits(Model model) throws ApiException {
		List<ShipOrder> sendOrders  = shipOrderService.findSendOrderWaits();
		model.addAttribute("orders", sendOrders);
		return "/admin/sendOrderWaits";
	}
	
	/**
	 * 出库单处理页面 (打印分拣单、审核分拣单、打印快递运单、出库确认)
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/do/{id}", method=RequestMethod.GET)
	public String doSendOrderForm(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderService.getShipOrder(orderId);
		model.addAttribute("order", sendOrder);
		model.addAttribute("express", Express.expressCompanys);
		return "/admin/sendOrderForm";
	}
	
	/**
	 * 出库单提交，等待用户签收。
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/submit", method=RequestMethod.POST)
	public String submitOrder(ShipOrder order, Model model) {
		ShipOrder entity = shipOrderService.getShipOrder(order.getId());
		entity.setExpressCompany(order.getExpressCompany());
		entity.setExpressOrderno(order.getExpressOrderno());
		entity.setLastUpdateDate(new Date());
		entity.setLastUpdateUser(order.getLastUpdateUser());
		entity.setStatus(ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED);
		shipOrderService.updateShipOrder(entity);
		return "redirect:/admin/trade/send/waits";
	}
	
}
