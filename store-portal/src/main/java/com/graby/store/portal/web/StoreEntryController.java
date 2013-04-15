package com.graby.store.portal.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.web.auth.ShiroContextUtils;

/**
 * 入库相关
 */
@Controller
@RequestMapping(value = "/store/entry")
public class StoreEntryController {
	
	private static final int PAGE_SIZE = 15;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 入库单列表
	 * @param page
	 * @param status
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int page, 
			@RequestParam(value = "status", defaultValue = ShipOrder.EntryOrderStatus.ENTRY_WAIT_SELLER_SEND) String status,
			Model model, ServletRequest request) {
		Long userId = ShiroContextUtils.getUserid();
		Page<ShipOrder> orders = shipOrderService.findEntrys(userId, status, page, PAGE_SIZE);
		model.addAttribute("orders", orders);
		model.addAttribute("status", status);
		return "store/entryList";
	}
	
	/**
	 * 跳转至入库单创建页面
	 * @param page
	 * @param status
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createEntryForm(Model model) {
		ShipOrder order = new ShipOrder();
		order.setFetchDate(new Date());
		model.addAttribute("order", order);
		model.addAttribute("action", "create");
		return "store/entryForm";
	}

	/**
	 * 创建入库单
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createEntry(ShipOrder order, Model model) {
		shipOrderService.saveEntryOrder(order);
		model.addAttribute("order", order);
		return "redirect:/store/entry/item/" + order.getId();
	}
	
	/**
	 * 跳转至入库单更新页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("order", shipOrderService.getShipOrder(id));
		model.addAttribute("action", "update");
		return "store/entryForm";
	}
	
	/**
	 * 更新入库单
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("preloadEntryOrder") ShipOrder order) {
		shipOrderService.saveEntryOrder(order);
		return "redirect:/store/entry/list";
	}
	
	/**
	 * 更新入库单
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		shipOrderService.deleteShipOrder(id);
		return "redirect:/store/entry/list";
	}
	
	/**
	 * 入库单商品管理
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "item/{id}")
	public String item(
			@PathVariable("id") Long orderId,	
			@RequestParam(value = "q", defaultValue = "") String q, 
			Model model) {
		ShipOrder order = shipOrderService.getShipOrder(orderId);
		Long userId = ShiroContextUtils.getUserid();
		Page<Item> page = itemService.findPageUserItems(userId, q, 1, 3000);
		model.addAttribute("order", order);
		model.addAttribute("items", page.getContent());
		return "store/entryItem";
	}
	
	/**
	 * 入库单商品管理-增加商品
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ajax/item/add/{orderid}/{itemid}/{num}")
	public String itemAdd(
			@PathVariable("orderid") Long orderId,
			@PathVariable("itemid") Long itemId,
			@PathVariable("num") long num
			) {
		shipOrderService.saveShipOrderDetail(orderId, itemId, num);
		return "redirect:/store/entry/ajax/detail/" + orderId;
	}
	
	@RequestMapping(value = "ajax/detail/{id}")
	public String itemDetail(@PathVariable("id") Long orderId, Model model) {
		ShipOrder order = shipOrderService.getShipOrder(orderId);
		model.addAttribute("order", order);
		return "store/entryDetail";
	}
	
	/**
	 * 入库单商品管理-删除商品
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ajax/item/delete/{orderid}/{id}")
	public String itemDel(@PathVariable("orderid") Long orderId, @PathVariable("id") Long detailId) {
		shipOrderService.deleteShipOrderDetail(detailId);
		return "redirect:/store/entry/ajax/detail/" + orderId;
	}		
	
	/**
	 * 发送入库单
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/{id}", method = RequestMethod.GET)
	public String sendOrderEntry(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		if (!shipOrderService.sendEntryOrder(id)) {
			redirectAttributes.addAttribute("message", "发送失败，商品数量为0");
			return "redirect:/store/entry/item/" + id;
		}  else {
			redirectAttributes.addAttribute("message", "入库单发送成功，请等待仓库管理员审核。");
			return "redirect:/store/entry/list";
		}
		
	}	
	
	/**
	 * 取消入库单
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "cancel/{id}", method = RequestMethod.GET)
	public String cancelOrderEntry(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		if (!shipOrderService.cancelEntryOrder(id)) {
			redirectAttributes.addAttribute("message", "取消发送失败");
			return "redirect:/store/entry/item/" + id;
		}  else {
			redirectAttributes.addAttribute("message", "入库单取消发送成功。");
			return "redirect:/store/entry/list";
		}
		
	}
	
	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ModelAttribute("preloadEntryOrder")
	public ShipOrder getEntryOrder(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return shipOrderService.getShipOrder(id);
		}
		return null;
	}
}
