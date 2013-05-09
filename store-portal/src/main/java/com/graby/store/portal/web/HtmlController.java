package com.graby.store.portal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/")
public class HtmlController {
	
	@RequestMapping(value = "/xtaoAuth.html", method = RequestMethod.GET)
	public String xtaoAuth(HttpServletRequest request, HttpServletResponse response) throws ApiException {
		return "redirect:/html/xtaoAuth.html";
	}
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) throws ApiException {
		return "redirect:/html/index.html";
	}
	
}
