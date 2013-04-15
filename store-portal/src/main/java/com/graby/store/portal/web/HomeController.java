package com.graby.store.portal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.service.trade.sync.TradeSync;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	@Autowired
	private TradeSync tradeSync;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//tradeSync.startSync();
		return "welcome";
	}
	
}
