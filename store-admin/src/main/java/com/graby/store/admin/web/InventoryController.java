package com.graby.store.admin.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.graby.store.entity.Item;
import com.graby.store.entity.User;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.UserRemote;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/stock")
public class InventoryController {

	@Autowired
	private UserRemote userRemote;

	@Autowired
	private InventoryRemote inventoryRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@RequestMapping(value = "")
	public String show(@RequestParam(value = "userid", defaultValue = "0") Long userId, Model model) throws ApiException {
		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);
		return "admin/shopInventory";
	}
	
	@RequestMapping(value = "/ajax/detail")
	public String detail(@RequestParam(value = "userid", defaultValue = "0") Long userId, Model model) throws ApiException {
		if (userId != 0L) {
			List<Map<String, Long>> stat = inventoryRemote.stat(1L, userId);
			model.addAttribute("stat", stat);
		}
		return "admin/shopInventoryDetail";
	}	
	
	@RequestMapping(value = "/position")
	public String position(Model model) throws ApiException {
		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);
		return "admin/itemPosition";
	}	
	
	@RequestMapping(value = "/ajax/position/detail")
	public String positionDetail(@RequestParam(value = "userid", defaultValue = "0") Long userId, Model model) throws ApiException {
		List<Item> items = itemRemote.findUserItems(userId);
		model.addAttribute("items", items);
		return "admin/itemPositionDetail";
	}	
	
}
