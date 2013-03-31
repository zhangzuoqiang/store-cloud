package com.graby.store.portal.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.service.ItemService;
import com.taobao.api.ApiException;


@Controller
@RequestMapping(value = "/rest/item")
public class ItemRestController {
		
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> syncTop() throws ApiException {
		itemService.syncTop();
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	 
}
