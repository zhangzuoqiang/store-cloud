package com.graby.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.ItemJpaDao;
import com.graby.store.dao.jpa.ItemMappingJpaDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemMapping;
import com.graby.store.web.auth.ShiroContextUtils;

@Component
@Transactional(readOnly = true)
public class ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemJpaDao itemJpaDao;
	
	@Autowired
	private ItemMappingJpaDao itemMappingJpaDao;
	
	@Transactional(readOnly = false)
	public void saveItem(Item item) {
		setCurrentUserid(item);
		itemJpaDao.save(item);
	}

	private void setCurrentUserid(Item item) {
		if (item.getUserid() == null) {
			item.setUserid(ShiroContextUtils.getUserid());	
		}
	}
	
	@Transactional(readOnly = false)
	public void saveItems(List<Item> items) {
		for (Item item : items) {
			saveItem(item);
		}
	}
	
	public Item getItem(Long id) {
		return itemDao.get(id);
	}
	
	/**
	 * 获取用户商品
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Item> findPageUserItems(Long userId, int pageNo, int pageSize) {
		pageNo = pageNo -1;
		long start = pageNo*pageSize;
		List<Item> items = itemDao.getItems(userId, start, pageSize);
		long total = itemDao.getTotalResults(userId);
		PageRequest pageable = new PageRequest((int)pageNo, (int)pageSize);
		Page<Item> page = new PageImpl<Item>(items, pageable, total);
		return page;
	}
	
	public List<Item> findUserItems(Long userid) {
		return itemJpaDao.findByUserid(userid);
	}
	
	/**
	 * 关联淘宝商品
	 * @param itemId
	 * @param tbitemId
	 * @param tbItemTitle
	 * @param tbItemUrl
	 */
	@Transactional(readOnly = false)
	public void relateItem(Long itemId,	com.taobao.api.domain.Item tbItem,	String  skuid ) {
		if (getRelatedItemId(tbItem.getNumIid(), skuid) == null) {
			ItemMapping mapping = new ItemMapping();
			Item localItem = new Item();
			localItem.setId(itemId);
			mapping.setItem(localItem);
			mapping.setNumIid(tbItem.getNumIid());
			mapping.setSkuId(skuid);
			mapping.setTitle(tbItem.getTitle());
			mapping.setDetailUrl(tbItem.getDetailUrl());
			itemMappingJpaDao.save(mapping);			
		}
	}
	
	/**
	 * 解除淘宝商品关联
	 * @param itemId
	 * @param numIid
	 */
	public void unRelateItem(Long itemId, Long numIid, String skuId) {
		if (getRelatedItemId(numIid, skuId) != null) {
			itemDao.unRelate(itemId, numIid, skuId);
		}
	}	
	
	/**
	 * 淘宝商品关联的商品ID
	 * @param itemId
	 * @param numIid
	 * @return
	 */
	public Long getRelatedItemId(Long numIid, String skuId) {
		return itemDao.getRelatedItemId(numIid, skuId);
	}	
	
	public void deleteItem(Long id) {
		itemDao.delete(id);
	}
	
}
