package com.graby.store.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.web.top.TopApi;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;

/**
 * 淘宝商品同步
 * @author huabiao.mahb
 */
@Component
public class ItemTopSync {
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 同步所有淘宝商品（库存+在售）
	 * @throws ApiException
	 */
	public void sync() throws ApiException {
		List<Item> items = topApi.getItems("", 3000);
		if (CollectionUtils.isEmpty(items)) return;
		for (Item item : items) {
			// 如果未关联则插入商品
			// 并关联淘宝商品
			List<Sku> skus = item.getSkus();
			if (CollectionUtils.isEmpty(skus)) {
				sync(item, null);
			} else {
				for (Sku sku : skus) {
					sync(item, sku);
				}
			}
		}
	}
	
	private void sync(Item item, Sku sku) {
		Long relatedId = itemService.getRelatedItemId(item.getNumIid(), sku == null ? 0L : sku.getSkuId());
		if (relatedId != null) {
			return;
		}
		com.graby.store.entity.Item copy = new com.graby.store.entity.Item();
		copy.setCode("000000000");
		String title = item.getTitle();
		if (sku != null) {
			title += " " + spl(sku.getPropertiesName());
		}
		copy.setTitle(title);
		copy.setWeight(0L);
		copy.setDescription(item.getDesc());
		itemService.saveItem(copy);
		itemService.relateItem(copy.getId(), item, sku == null ? 0L : sku.getSkuId());
	}
	
	/**
	 * 同步淘宝商品（单挑）
	 * @param numIid
	 * @param skuId
	 * @throws ApiException
	 */
	public void sync(Long numIid, Long skuId) throws ApiException {
		Item item = topApi.getItem(numIid);
		Sku sku = topApi.getSku(numIid, skuId);
		sync(item, sku);
	}
	
	private static String spl(String val) {
		if (StringUtils.isBlank(val)) {
			return "";
		}
		String[] ss = val.split(";");
		String[] cc;
		String result = "";
		for (String s : ss) {
			cc = s.split(":");
			result = cc[2] + ":" + cc[3];
		}
		return result;
	}
}
