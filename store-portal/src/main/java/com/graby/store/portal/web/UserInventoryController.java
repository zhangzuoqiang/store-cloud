package com.graby.store.portal.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.service.InventoryService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;

/**
 * 仓库相关
 */
@Controller
@RequestMapping(value = "/store/info")
public class UserInventoryController {
	
	@Autowired
	private InventoryService inventoryService;

	@RequestMapping(value = "")
	public String welcome(Model model) throws ApiException {
		Long userId = ShiroContextUtils.getUserid();
		List<Map<String, Long>> values = inventoryService.stat(1L, userId);
		model.addAttribute("userId", userId);
		model.addAttribute("centro", "湘潭高新仓");
		model.addAttribute("values", values);
		return "store/inventory";
	}
	
}
