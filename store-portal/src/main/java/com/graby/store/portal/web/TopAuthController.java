package com.graby.store.portal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.cache.Cache;
import com.graby.store.service.AuthService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.graby.store.web.top.TopApi;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;

@Controller
@RequestMapping(value = "/top_auth")
public class TopAuthController {
	
	@Autowired
	private Cache<String,String> userCache;
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private AuthService userService;
	
	@RequestMapping
	public String auth(HttpServletRequest request, HttpServletResponse response, Model model) throws ApiException {
		String sessionKey = request.getParameter("top_session");
		if (sessionKey != null) {
			String nickName = topApi.getNick(sessionKey);
			Shop shop = topApi.getShop(nickName);
			// 同步淘宝用户, 密码为用户名
			userService.addUserIfNecessary(nickName, shop.getTitle());
			userCache.put(nickName, sessionKey);
			model.addAttribute("username", nickName);
			model.addAttribute("password", nickName);
			ShiroContextUtils.logout();
		}
		return "auth/post";
	}
}
