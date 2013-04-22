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

import com.graby.store.base.GroupMap;
import com.graby.store.entity.Item;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.service.inventory.Accounts;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ShipOrderService;
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
	 * 下单发货（单条老版 即将废弃）
	 * 
	 * @param trade
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send/confirm")
	public String sendConfirm(Trade trade, RedirectAttributes redirectAttributes) {
		tradeService.createTrade(trade, null);
		return "redirect:/trade/wait";
	}

	/**
	 * 批量查询淘宝交易订单（多条）
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/waits")
	public String waitsForward() throws ApiException {
		return "trade/waits";
	}

	/**
	 * 查询所有等待买家发货交易订单（新版）
	 * 
	 * useable : 可发送的<br> 
	 * related : 已由物流通处理的<br> 
	 * failed : 未关联或库存不足的<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/waits/fetch")
	public String fetch(@RequestParam(value = "preday") int preday, Model model) throws Exception {
		
		/* -1 查询最近一周， 其他指定天数*/
		GroupMap<String, Trade> tradeMap = preday == -1 ? 
				tradeService.fetchTopTrades(TopApi.TradeStatus.TRADE_WAIT_SELLER_SEND_GOODS, 0,1,2,3,4,5,6) :
				tradeService.fetchTopTrades(TopApi.TradeStatus.TRADE_WAIT_SELLER_SEND_GOODS, preday);
		model.addAttribute("useable", tradeMap.getList("useable"));
		model.addAttribute("related", tradeMap.getList("related"));
		model.addAttribute("failed", tradeMap.getList("failed"));
		//model.addAttribute("date", DateUtils.format(day));
		return "trade/waitsFetch";
	}

	/**
	 * 根据淘宝交易ID批量创建系统交易订单
	 * 
	 * @param tids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "/send")
	public String send(@RequestParam(value = "tids") String[] tids) throws NumberFormatException, ApiException {
		tradeService.createTradesFromTop(tids);
		return "redirect:/trade/waits";
	}

	/**
	 * 当前用户仓库已接收订单
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/received")
	public String received(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "status", defaultValue = "") String status, Model model) throws ApiException {
		Page<Trade> trades = tradeService.findUserTrades(ShiroContextUtils.getUserid(), status, page, 10);
		model.addAttribute("trades", trades);
		return "trade/received";
	}

	/**
	 * 淘宝订单处理(单条)
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/deal/tb/{tid}", method = RequestMethod.GET)
	public String deal(@PathVariable("tid") Long tid, Model model, RedirectAttributes redirectAttributes)
			throws ApiException {
		Long tradeId = tradeService.getRelatedTradeId(tid);
		if (tradeId != null) {
			redirectAttributes.addFlashAttribute("message", "该订单已被处理");
			return "redirect:/trade/wait";
		}

		com.taobao.api.domain.Trade trade = topApi.getFullinfoTrade(tid);
		Trade sTrade = tradeAdapter.adapter(trade);
		List<TradeOrder> orders = sTrade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long numIid = order.getNumIid();
			Long skuId = order.getSkuId();
			skuId = skuId == null ? 0L : skuId;
			Long itemId = itemServie.getRelatedItemId(numIid, skuId);
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
	 * 等待用户签收列表
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/notifys", method = RequestMethod.GET)
	public String notifyTrades(@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws ApiException {
		Page<Trade> trades = tradeService.findUserTrades(
				ShiroContextUtils.getUserid(),
				Trade.Status.TRADE_WAIT_EXPRESS_NOFITY, page, 15);
		model.addAttribute("trades", trades);
		return "trade/tradeNotifys";
	}

	/**
	 * 商铺方通知用户签收
	 * 
	 * @param tid
	 * @param redirectAttributes
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public String notifyUser(@RequestParam(value = "ids", defaultValue = "") Long[] tradeIds,
			RedirectAttributes redirectAttributes) throws ApiException {
		shipOrderService.batchNotifyUserSign(tradeIds);
		return "redirect:/trade/notifys";
	}

}
