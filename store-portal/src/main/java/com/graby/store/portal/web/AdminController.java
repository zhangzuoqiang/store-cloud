package com.graby.store.portal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@RequestMapping
	public String welcome(HttpServletRequest request, HttpServletResponse response) throws ApiException {
		return "welcome";
	}
	
	
}
