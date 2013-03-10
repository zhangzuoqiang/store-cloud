package com.graby.store.portal.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.graby.store.entity.Item;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.portal.inventory.Accounts;
import com.graby.store.portal.inventory.InventoryService;
import com.graby.store.portal.service.ItemService;
import com.graby.store.portal.service.TradeService;
import com.taobao.api.ApiException;


@Controller
@RequestMapping(value = "/admin/trade/")
public class AdminTradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 查询所有待处理订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/waits", method=RequestMethod.GET)
	public String waits(Model model) throws ApiException {
		List<Trade> trades = tradeService.findWaitAuditTrades();
		model.addAttribute("trades", trades);
		return "/admin/tradeWaits";
	}
	
	/**
	 * 查询所有待处理订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/audit/{id}", method=RequestMethod.GET)
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

	
	
}
