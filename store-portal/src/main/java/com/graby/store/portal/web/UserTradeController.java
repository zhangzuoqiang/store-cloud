package com.graby.store.portal.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.graby.store.entity.Item;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.portal.inventory.Accounts;
import com.graby.store.portal.inventory.InventoryService;
import com.graby.store.portal.service.ItemService;
import com.graby.store.portal.service.TradeService;
import com.graby.store.portal.util.TopApi;
import com.graby.store.portal.util.TradeAdapter;
import com.taobao.api.ApiException;

/**
 * 用户交易
 * @author huabiao.mahb
 */
@Controller
@RequestMapping(value = "/trade")
public class UserTradeController {
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private TradeAdapter tradeAdapter;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private ItemService itemServie;
	
	/**
	 * 查询所有等待买家发货交易订单
	 * @return
	 * @throws ApiException 
	 */
	@RequestMapping(value = "wait")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page, 
			Model model) throws ApiException {
		Page<com.taobao.api.domain.Trade> trades = topApi.getTrades(TopApi.TradeStatus.TRADE_WAIT_SELLER_SEND_GOODS, page, 10);
		// 已关联系统的订单不处理 TODO
		for (com.taobao.api.domain.Trade tbTrade : trades) {
			Long sysTradeId = tradeService.getRelatedTradeId(tbTrade.getTid());
		}
		model.addAttribute("trades", trades);
		return "trade/wait";
	}
	
	/**
	 * 淘宝订单处理
	 * @param id
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/deal/tb/{tid}" ,method = RequestMethod.GET)
	public String deal(@PathVariable("tid") Long tid, Model model) throws ApiException {
		com.taobao.api.domain.Trade trade = topApi.getTrade(tid);
		Trade sTrade = tradeAdapter.adapterFromTop(trade);
		List<TradeOrder> orders = sTrade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long numIid = order.getNumIid();
			Long itemId = itemServie.getRelatedItemId(numIid);
			if (itemId == null) {
				order.setStockNum(-1);
			} else {
				long stockNum = inventoryService.getValue(1L, itemId, Accounts.CODE_SALEABLE);
				order.setStockNum(stockNum);
				Item item = itemServie.getItem(itemId);
				order.setItem(item);
			}
		}
		model.addAttribute("trade", sTrade);
		return "trade/deal";
	}
	
	/**
	 * 发货处理
	 * @param trade
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send/confirm")
	public String confirm(Trade trade, RedirectAttributes redirectAttributes) {
		tradeService.createTrade(trade);
		return "redirect:/trade/wait";
	}
	
	
}
