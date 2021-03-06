package com.graby.store.portal.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.graby.store.entity.Item;
import com.graby.store.portal.util.ItemExcelReader;
import com.graby.store.service.ItemService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.graby.store.web.top.TopApi;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

	private static final int PAGE_SIZE = 10;
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private ItemService itemService;

	// 商品列表
	@RequestMapping(value = "list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
		Long userId = ShiroContextUtils.getUserid();
		Page<Item> items = itemService.findPageUserItems(userId, pageNumber, PAGE_SIZE);
		model.addAttribute("items", items);
		return "item/itemList";
	}

	 // 淘宝商品列表
	@RequestMapping(value = "mapping/{id}")
	public String tblist(
			@RequestParam(value = "q", defaultValue = "") String q,
			@PathVariable("id") Long id,
			Model model, 
			ServletRequest request) throws ApiException {
		List<com.taobao.api.domain.Item> tbItems = topApi.getTopItems(q);
		Item item = itemService.getItem(id);
		model.addAttribute("tbitems", tbItems);
		model.addAttribute("item", item);
		return "item/tbitemList";
	}
	
	// 关联淘宝商品
	@RequestMapping(value = "relate/{itemid}/{tbitemid}/{skuid}")
	public String relate(@PathVariable("itemid") Long itemid, 
						@PathVariable("tbitemid") Long tbitemid,
						@PathVariable("skuid") String skuid) throws ApiException {
		com.taobao.api.domain.Item tbItem = topApi.getItem(tbitemid);
		itemService.relateItem(itemid, tbItem, skuid);
		return "redirect:/item/list";
	}
	
	// 关联淘宝商品
	@RequestMapping(value = "unrelate/{itemid}/{tbitemid}/{skuid}")
	public String unrelate(
			@PathVariable("itemid") Long itemid, 
			@PathVariable("tbitemid") Long tbitemid, 
			@PathVariable("skuid") String skuid) 
					throws ApiException {
		itemService.unRelateItem(itemid, tbitemid, skuid);
		return "redirect:/item/list";
	}	
	
	// 上传商品页面
	@RequestMapping(value = "add" ,method = RequestMethod.GET)
	public String upload() throws ApiException {
		return "/item/itemAdd";
	}
	
	// 导入商品
	@RequestMapping(value = "upload", method=RequestMethod.POST)
	public String uploadItems(@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model) 
			throws FileNotFoundException, IOException {
		ItemExcelReader reader = new ItemExcelReader(file.getInputStream());
		List<Item> items = reader.getItems();
		itemService.saveItems(items);
		return "redirect:/item/list";
	}

	// 创建商品
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("action", "create");
		return "item/itemForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(Item newItem, RedirectAttributes redirectAttributes) {
		newItem.setUserid(ShiroContextUtils.getUserid());
		itemService.saveItem(newItem);
		redirectAttributes.addFlashAttribute("message", "创建商品成功");
		return "redirect:/item/list";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("item", itemService.getItem(id));
		model.addAttribute("action", "update");
		return "item/itemForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("preloadItem") Item item, RedirectAttributes redirectAttributes) {
		itemService.saveItem(item);
		redirectAttributes.addFlashAttribute("message", "更新商品成功");
		return "redirect:/item/list";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		itemService.deleteItem(id);
		redirectAttributes.addFlashAttribute("message", "删除商品成功");
		return "redirect:/item/list";
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Item对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("preloadItem")
	public Item getItem(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return itemService.getItem(id);
		}
		return null;
	}

}
