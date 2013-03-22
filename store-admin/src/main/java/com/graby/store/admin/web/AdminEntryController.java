package com.graby.store.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.graby.store.entity.ShipOrder;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.service.InvAccountEntry;
import com.graby.store.service.InvAccountEntrys;
import com.graby.store.service.InvAccountTemplate;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/entry")
public class AdminEntryController {
	
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
	@RequestMapping
	public String welcome(HttpServletRequest request, HttpServletResponse response) throws ApiException {
		return "welcome";
	}
	
	/**
	 * 获取在途入库单列表
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "",  method=RequestMethod.GET)
	public String entry(Model model) throws ApiException {
		List<ShipOrder> entryOrders = shipOrderRemote.findEntryOrderOnWay();
		model.addAttribute("entryOrders", entryOrders);
		return "/admin/entryOnWay";
	}
	
	/**
	 * 跳转至入库单处理页面 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/operate/{id}", method=RequestMethod.GET)
	public String entryOperateForm(@PathVariable("id") Long id, Model model) throws ApiException {
		ShipOrder order = shipOrderRemote.getShipOrder(id);
		model.addAttribute("order", order);
		return "admin/entryOperate";
	}
	
	/**
	 * 入库操作
	 * @param form
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/operate", method=RequestMethod.POST)
	public String entryHandle(EntryForm form) throws ApiException {
		Map<String, String> map = form.getContent();
		Long centroId = Long.parseLong(map.get("centro_id"));
		Long userId = Long.parseLong(map.get("user_id"));
		Long orderId = Long.parseLong(map.get("order_id"));
		Set<Long> itemKeys = itemKeys(map, "item_");
		
		List<InvAccountEntrys> accountItems = new ArrayList<InvAccountEntrys>();
		// 库存记录
		for (Long itemId : itemKeys) {
			InvAccountEntrys entrys = new  InvAccountEntrys();
			InvAccountEntry[] entryArray = new InvAccountEntry[4];
			entryArray[0] = new InvAccountEntry(InvAccountTemplate.STORAGE_RECEIVED_SALEABLE, Long.parseLong(map.get("ok_" + itemId)));
			entryArray[1] = new InvAccountEntry(InvAccountTemplate.STORAGE_RECEIVED_BADNESS_DEFECT, Long.parseLong(map.get("err1_" + itemId)));
			entryArray[2] = new InvAccountEntry(InvAccountTemplate.STORAGE_RECEIVED_BADNESS_DEMAGE_MACHINE, Long.parseLong(map.get("err2_" + itemId)));
			entryArray[3] = new InvAccountEntry(InvAccountTemplate.STORAGE_RECEIVED_BADNESS_DEMAGE_BOX, Long.parseLong(map.get("err3_" + itemId)));
			entrys.setItemId(itemId);
			entrys.setEntrys(entryArray);
			entrys.setCentroId(centroId);
			entrys.setUserId(userId);
			accountItems.add(entrys);
		}
		shipOrderRemote.recivedEntryOrder(orderId, accountItems);
		return "redirect:/entry";
	}
	
	
	private Set<Long> itemKeys(Map<String,String> map, String prefix) {
		Set<Long> values = new HashSet<Long>(); 
		for (String key : map.keySet()) {
			if (key.startsWith(prefix)) {
				String itemId = key.substring(prefix.length(), key.length());
				values.add(Long.parseLong(itemId));
			}
		}
		return values;
	}
	
	public static class EntryForm {
		private Map<String, String> content = new HashMap<String,String>();
		public Map<String, String> getContent() {
			return content;
		}
		public void setContent(Map<String, String> content) {
			this.content = content;
		}
	}
	
}
