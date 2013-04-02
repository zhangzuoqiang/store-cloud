package com.graby.store.portal.web;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.graby.store.base.GroupMap;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;
import com.graby.store.service.InvAccounts;
import com.graby.store.service.InventoryService;
import com.graby.store.service.ItemService;
import com.graby.store.service.ShipOrderService;
import com.graby.store.service.TradeService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.graby.store.web.top.TopApi;
import com.graby.store.web.top.TradeAdapter;
import com.taobao.api.ApiException;

/**
 * 用户交易
 * 
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
	private ItemService itemServie;
	
	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private TradeService tradeService;

	
	@Autowired
	private ShipOrderService shipOrderService;

	/**
	 * 查询所有等待买家发货交易订单
	 * 老版
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "wait")
	public String wait(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws ApiException {
		Page<com.taobao.api.domain.Trade> trades = topApi.getTrades(TopApi.TradeStatus.TRADE_WAIT_SELLER_SEND_GOODS, page, 10);
		if (CollectionUtils.isNotEmpty(trades.getContent())) {
			for (com.taobao.api.domain.Trade tbTrade : trades) {
				TradeMapping mapping =tradeService.getRelatedMapping(tbTrade.getTid());
				// 这里特殊用这个字段标注该订单状态
				tbTrade.setStatus(mapping == null ? "unrelated" : mapping.getStatus());
			}
		}
		model.addAttribute("trades", trades);
		return "trade/wait";
	}
	
	/**
	 * 查询所有等待买家发货交易订单（新版）
	 * 
 	 * useable   : 可发送的
	 * related   : 已由物流通处理的
	 * failed 	 : 未关联或库存不足的
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "waits")
	public String waits(Model model) throws ApiException {
		GroupMap<String,Trade> tradeMap = tradeService.groupFindTopTrades();
		model.addAttribute("useable", tradeMap.getList("useable"));
		model.addAttribute("related", tradeMap.getList("related"));
		model.addAttribute("failed", tradeMap.getList("failed"));
		return "trade/waits";
	}	
	
	@RequestMapping(value = "send")
	public String send(@RequestParam(value = "tids", defaultValue = "") String[] tids) throws NumberFormatException, ApiException {
		tradeService.createTradesFrom(tids);
		return "redirect:/trade/waits";
	}

	/**
	 * 淘宝订单处理
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/deal/tb/{tid}", method = RequestMethod.GET)
	public String deal(@PathVariable("tid") Long tid, Model model, RedirectAttributes redirectAttributes) throws ApiException {
		Long tradeId = tradeService.getRelatedTradeId(tid);
		if (tradeId != null) {
			redirectAttributes.addFlashAttribute("message", "该订单已被处理");
			return "redirect:/trade/wait";
		}

		com.taobao.api.domain.Trade trade = topApi.getTrade(tid);
		Trade sTrade = tradeAdapter.adapterFromTop(trade);
		List<TradeOrder> orders = sTrade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long numIid = order.getNumIid();
			Long skuId = order.getSkuId();
			skuId = skuId == null ? 0L : skuId;
			Long itemId = itemServie.getRelatedItemId(numIid ,skuId);
			if (itemId == null) {
				order.setStockNum(-1);
			} else {
				long stockNum = inventoryService.getValue(1L, itemId, InvAccounts.CODE_SALEABLE);
				order.setStockNum(stockNum);
				Item item = itemServie.getItem(itemId);
				order.setItem(item);
			}
		}
		model.addAttribute("trade", sTrade);
		return "trade/deal";
	}

	/**
	 * 下单发货
	 * 
	 * @param trade
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send/confirm")
	public String sendConfirm(Trade trade, RedirectAttributes redirectAttributes) {
		tradeService.createTrade(trade);
		return "redirect:/trade/wait";
	}
	
	/**
	 * 查询交易订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "list", method=RequestMethod.GET)
	public String trades(
			@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) throws ApiException {
		Page<Trade> trades = tradeService.findUserTrades(ShiroContextUtils.getUserid(), status, page, 10);
		model.addAttribute("trades", trades);
		return "trade/tradeList";
	}
	
	@RequestMapping(value = "notify/{tid}", method = RequestMethod.GET)
	public String notifyUser(@PathVariable("tid") Long tid, RedirectAttributes redirectAttributes) throws ApiException {
		ShipOrder order = shipOrderService.getShipOrderByTid(tid);
		topApi.tradeOfflineShipping(tid, order.getExpressOrderno(), order.getExpressCompany());
		StringBuffer successMessage = new StringBuffer();
		successMessage.append("交易号:").append(tid);
		successMessage.append("已通知用户等待签收<br>");
		successMessage.append("物流公司:").append(order.getExpressCompany());
		successMessage.append("运单号:").append(order.getExpressOrderno());
		redirectAttributes.addFlashAttribute("message", successMessage.toString());
		return "trade/notified";
	}
	
}
