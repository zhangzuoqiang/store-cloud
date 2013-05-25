package com.graby.store.portal.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.graby.store.base.MessageContextHelper;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ShipOrderService;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/rest/trade")
public class TradeRestController {

	@Autowired
	private TradeService tradeService;

	@Autowired
	private ShipOrderService shipOrderService;

	/**
	 * 根据淘宝交易ID批量创建系统交易订单 
	 * 库存记账: 可销售->冻结
	 * 
	 * @param tids
	 * @return
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResponseEntity<String> send(@RequestParam(value = "tids") String[] tids) throws NumberFormatException, ApiException {
		synchronized (this) {
			tradeService.createTradesFromTop(tids);
		}
		return new ResponseEntity<String>(MessageContextHelper.getMessage(), HttpStatus.OK);
	}

	/**
	 * 商铺方通知用户签收 库存记账: 冻结->已销售
	 * 
	 * @param tid
	 * @param redirectAttributes
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public ResponseEntity<String> notifyUser(@RequestParam(value = "tradeIds") Long[] tradeIds,
			RedirectAttributes redirectAttributes) throws ApiException {
		shipOrderService.batchNotifyUserSign(tradeIds);
		return new ResponseEntity<String>(MessageContextHelper.getMessage(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<String> test(@RequestParam(value = "tids") String[] tids) throws NumberFormatException, ApiException {
//		synchronized (this) {
			System.out.println("thread:" + Thread.currentThread().getId());
			for (int i = 0; i < tids.length; i++) {
				System.out.print(i + ",");
			}
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("\ndone");
//		}
		
		return new ResponseEntity<String>(MessageContextHelper.getMessage(), HttpStatus.OK);
	}

}
