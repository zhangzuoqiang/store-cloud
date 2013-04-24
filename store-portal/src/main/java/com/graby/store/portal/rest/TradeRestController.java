package com.graby.store.portal.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.graby.store.service.trade.TradeService;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/rest/trade")
public class TradeRestController {
	
	@Autowired
	private TradeService tradeService;
	
	@RequestMapping(value = "/send",  method = RequestMethod.POST)
	public ResponseEntity<String> send(@RequestParam(value = "tids") String[] tids) throws NumberFormatException, ApiException {
		tradeService.createTradesFromTop(tids);
		return new ResponseEntity<String>("成功提交"+tids.length+"条交易订单", HttpStatus.OK);
	}
	
}
