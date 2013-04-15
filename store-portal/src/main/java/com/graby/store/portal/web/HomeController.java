package com.graby.store.portal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "welcome";
	}
	
}
