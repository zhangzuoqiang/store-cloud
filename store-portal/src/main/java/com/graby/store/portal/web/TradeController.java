package com.graby.store.portal.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.graby.store.base.GroupMap;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.service.trade.TradeService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Refund;

/**
 * 用户交易
 * 
 * @author huabiao.mahb
 */
@Controller
@RequestMapping(value = "/trade")
public class TradeController {

	@Autowired
	private TradeService tradeService;
	
	/**
	 * 活动专场 用于大批量团购
	 * @return
	 */
	@RequestMapping(value = "/special")
	public String special() {
		return "trade/special";
	}
	
	@RequestMapping(value = "/special/fetch/ajax")
	public String specialResult(@RequestParam(value = "preday") int preday, Model model) throws Exception {
		GroupMap<String,Long> results = tradeService.fetchWaitSendTopTradeTotalResults(preday);
		model.addAttribute("related", results.getList("related"));
		List<Long> unRelated = results.getList("unrelated");
		model.addAttribute("unrelated", unRelated == null ? new ArrayList<Long>() : unRelated);
		return "trade/specialFetch";
	}
	
	/**
	 * 批量查询淘宝交易订单（多条）
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/waits")
	public String waits() {
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

		/* -1 查询最近一周， 其他指定天数 */
		GroupMap<String, Trade> tradeMap = preday == -1 ? 
			tradeService.fetchWaitSendTopTrades( 0, 1, 2, 3, 4) : 
			tradeService.fetchWaitSendTopTrades(preday);
		model.addAttribute("useable", tradeMap.getList("useable"));
		model.addAttribute("related", tradeMap.getList("related"));
		model.addAttribute("failed", tradeMap.getList("failed"));
		model.addAttribute("refund", tradeMap.getList("refund"));
		return "trade/waitsFetch";
	}
	
	/**
	 * 退款列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/refunds")
	public String refunds(Model model) throws Exception {
		List<Refund> refunds = tradeService.fetchRefunds();
		List<RefundEntry> entrys = new ArrayList<RefundEntry>();
		for (Refund refund : refunds) {
			TradeMapping mapping = tradeService.getRelatedMapping(refund.getTid());
			entrys.add(new RefundEntry(refund, mapping));
		}
		model.addAttribute("refunds", entrys);
		return "trade/refunds";
	}
	
	@RequestMapping(value = "/delete/{tradeId}")
	public String delete(@PathVariable("tradeId") Long tradeId) {
		tradeService.deleteTrade(tradeId);
		return "redirect:/trade/refunds";
	}
	
	public class RefundEntry {
		public RefundEntry(Refund refund, TradeMapping mapping) {
			this.refund = refund;
			this.mapping = mapping;
		}
		private Refund refund;
		private TradeMapping mapping;
		public Refund getRefund() {	return refund;}
		public TradeMapping getMapping() {return mapping;}
		public void setRefund(Refund refund) {this.refund = refund;}
		public void setMapping(TradeMapping mapping) {this.mapping = mapping;}
	}
	
	/**
	 * 当前用户仓库已接收订单列表
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
	 * 等待用户签收列表
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/notifys", method = RequestMethod.GET)
	public String notifyTrades(@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws ApiException {
		Page<Trade> trades = tradeService.findUserTrades(ShiroContextUtils.getUserid(), Trade.Status.TRADE_WAIT_EXPRESS_NOFITY,
				page, 15);
		model.addAttribute("trades", trades);
		return "trade/tradeNotifys";
	}

//	/**
//	 * 根据淘宝交易ID批量创建系统交易订单
//	 * 库存记账: 可销售->冻结
//	 * @param tids
//	 * @return
//	 * @throws NumberFormatException
//	 * @throws ApiException
//	 */
//	@RequestMapping(value = "/send",  method = RequestMethod.POST)
//	public String send(@RequestParam(value = "tids") String[] tids) throws NumberFormatException, ApiException {
//		tradeService.createTradesFromTop(tids);
//		return "redirect:/trade/waits";
//	}
//	/**
//	 * 商铺方通知用户签收
//	 * 库存记账: 冻结->已销售
//	 * @param tid
//	 * @param redirectAttributes
//	 * @return
//	 * @throws ApiException
//	 */
//	@RequestMapping(value = "/notify", method = RequestMethod.POST)
//	public String notifyUser(@RequestParam(value = "ids", defaultValue = "") Long[] tradeIds,
//			RedirectAttributes redirectAttributes) throws ApiException {
//		shipOrderService.batchNotifyUserSign(tradeIds);
//		return "redirect:/trade/notifys";
//	}

}
