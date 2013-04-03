package com.graby.store.admin.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.graby.store.entity.User;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.UserRemote;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/stock")
public class InventoryController {

	@Autowired
	private UserRemote userRemote;

	@Autowired
	private InventoryRemote inventoryRemote;

	@RequestMapping(value = "")
	public String stock(@RequestParam(value = "userid", defaultValue = "0") Long userId, Model model) throws ApiException {
		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);
		if (userId != 0L) {
			List<Map<String, Long>> stat = inventoryRemote.stat(1L, userId);
			model.addAttribute("stat", stat);
		}
		return "admin/inventory";
	}
}
