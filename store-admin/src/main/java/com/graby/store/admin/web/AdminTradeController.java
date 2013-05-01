package com.graby.store.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.remote.ExpressRemote;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.service.inventory.Accounts;
import com.taobao.api.ApiException;


@Controller
@RequestMapping(value = "/trade/")
public class AdminTradeController {
	
	@Autowired
	private TradeRemote tradeRemote;
	
	@Autowired
	private InventoryRemote inventoryRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
	@Autowired
	private ExpressRemote expressRemote;
	
	/**
	 * 查询所有待审核订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "waits", method=RequestMethod.GET)
	public String waitAudits(Model model) throws ApiException {
		List<Trade> trades = tradeRemote.findWaitAuditTrades();
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
		Trade trade = tradeRemote.getTrade(id);
		List<TradeOrder> orders = trade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long itemId = order.getItem().getId();
			if (itemId == null) {
				order.setStockNum(-1);
			} else {
				// 已关联的设置库存
				long stockNum = inventoryRemote.getValue(1L, itemId, Accounts.CODE_SALEABLE);
				order.setStockNum(stockNum);
				Item item = itemRemote.getItem(itemId);
				order.setItem(item);
			}
		}
		model.addAttribute("trade", trade);
		return "/admin/tradeAudit";
	}
	
	/**
	 * 审核通过，创建出库单。
	 */
	@RequestMapping(value = "mkship", method=RequestMethod.POST)
	public String mkship(@RequestParam("tradeId") Long tradeId, Model model) {
		ShipOrder sendOrder = tradeRemote.createSendShipOrderByTradeId(tradeId);
		model.addAttribute("sendOrder", sendOrder);
		return "redirect:/trade/waits";
	}
	
	/**
	 * 活动专场 用于大批量团购
	 * @return
	 */
	@RequestMapping(value = "special/waits")
	public String special() {
		return "/admin/tradeSpecialAudit";
	}
	
	/**
	 * 活动专场 审核所有待处理交易订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "mkship/all", method=RequestMethod.GET)
	public String auditAll() throws ApiException {
		tradeRemote.createAllSendShipOrder(1L);
		return "redirect:/trade/waits";
	}
	
	/**
	 * 查询所有待处理出库单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/waits", method=RequestMethod.GET)
	public String sendWaits(Model model) throws ApiException {
		List<ShipOrder> sendOrders  = shipOrderRemote.findSendOrderWaits();
		model.addAttribute("orders", sendOrders);
		return "/admin/sendOrderWaits";
	}	
	
	/**
	 * 手工设置运单
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/do/{id}", method=RequestMethod.GET)
	public String doSendOrderForm(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.getShipOrder(orderId);
		model.addAttribute("order", sendOrder);
		model.addAttribute("expressCompanys", expressRemote.getExpressMap());
		return "/admin/sendOrderForm";
	}	
	
	/**
	 * 查询所有待拣货出库单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/pickings", method=RequestMethod.GET)
	public String pickingList(Model model) {
		List<ShipOrder> sendOrders  = shipOrderRemote.findSendOrderByStatus(1L, ShipOrder.SendOrderStatus.WAIT_EXPRESS_PICKING);
		model.addAttribute("orders", sendOrders);
		return "/admin/sendOrderPickings";
	}
	
	/**
	 * 重置拣货单为运单打印状态
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/express")
	public String reExpress(@RequestParam(value = "ids", defaultValue = "") Long[] ids)  {
		shipOrderRemote.reExpressShipOrder(ids);
		return "redirect:/trade/send/pickings";
	}
	
	/**
	 * 输出拣货单(PDF)
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/pick/report")
	public ModelAndView pickReport(
			@RequestParam(value = "ids", defaultValue = "") Long[] ids,
			@RequestParam(value = "format", defaultValue = "pdf") String format) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ShipOrder> orders = shipOrderRemote.findSendOrders(ids);
		model.put("data", orders);
		model.put("format", format);
		return new ModelAndView("minPickReport", model);
	}	
	
	public String pickAll() {
		return null;
	}
	
	/**
	 * 订单已拣货，审核通过提交到系统。（批量 不建议）
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "send/submits")
	public String submits(
			@RequestParam(value = "ids", defaultValue = "") Long[] ids,
			@RequestParam(value = "action", defaultValue = "send/pickings") String action) throws NumberFormatException, ApiException {
		shipOrderRemote.submits(ids);
		return "redirect:/trade/" + action;
	}	
	
	@RequestMapping(value = "ship/audit")
	public String auditForm() {
		return "admin/shipAuditForm";
	}
	
	@RequestMapping(value = "ship/audit/ajax")
	public String auditOrder(@RequestParam(value = "q", defaultValue = "")String q,	Model model) {
		List<ShipOrder> orders = shipOrderRemote.findSendOrderByQ(q);
		List<Entry> entrys = new ArrayList<Entry>();
		for (ShipOrder shipOrder : orders) {
			Entry entry = new Entry();
			Trade tarde = tradeRemote.getTrade(shipOrder.getTradeId());
			entry.setOrder(shipOrder);
			entry.setTrade(tarde);
			entrys.add(entry);
		}
		model.addAttribute("entrys", entrys);
		return "admin/shipAuditOrder";
	}
	
	@RequestMapping(value = "ship/audit/done")
	public String auditdone() {
		return "admin/shipAuditDone";
	}	
	
	
	/**
	 * (仓库方)提交出库单，等待用户签收。
	 * @param orderId
	 * @param model
	 * @return
	 * @throws ApiException 
	 */
	@RequestMapping(value = "send/submit", method=RequestMethod.POST)
	public String submitOrder(ShipOrder order, Model model) throws ApiException {
		shipOrderRemote.submitSendOrder(order);
		return "redirect:/trade/send/waits";
	}
	
	/**
	 * 等待用户签收订单列表
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sign/waits")
	public String signWaits(
			@RequestParam(value = "q", defaultValue = "") String q,
			Model model) {
		List<ShipOrder> sendOrders  = shipOrderRemote.findSendOrderSignWaits();
		model.addAttribute("orders", sendOrders);
		return "/admin/signWaits";
	}
	
	/**
	 * 用户签收页面
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/sign/{id}", method=RequestMethod.GET)
	public String signSendOrder(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.getShipOrder(orderId);
		model.addAttribute("order", sendOrder);
		return "/admin/signForm";
	}
	
	/**
	 * 点击用户签收
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sign/submit/{id}", method=RequestMethod.GET)
	public String submitSign(@PathVariable(value = "id")Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.signSendOrder(orderId);
		model.addAttribute("order", sendOrder);
		return "admin/signSuccess";
	}
	
	/**
	 * 查询所有未关闭订单
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "unfinish", method=RequestMethod.GET)
	public String unfinish(
			@RequestParam(value = "page", defaultValue = "1") int page, 
			Model model) throws ApiException {
		Page<Trade> trades = tradeRemote.findUnfinishedTrades(page, 10);
		model.addAttribute("trades", trades);
		return "/admin/tradeUnfinishs";
	}
	
	@RequestMapping(value = "delete/{id}", method=RequestMethod.GET)
	public String deleteTrade(@PathVariable(value = "id")Long tradeId) {
		tradeRemote.deleteTrade(tradeId);
		return "redirect:/trade/unfinish";
	}	
	
	public class Entry {
		private Trade trade;
		private ShipOrder order;
		public Trade getTrade() {
			return trade;
		}
		public ShipOrder getOrder() {
			return order;
		}
		public void setTrade(Trade trade) {
			this.trade = trade;
		}
		public void setOrder(ShipOrder order) {
			this.order = order;
		}
	}	
}
