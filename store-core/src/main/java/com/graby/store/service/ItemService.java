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
import com.taobao.api.ApiException;

@Component
@Transactional(readOnly = true)
public class ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemJpaDao itemJpaDao;
	
	@Autowired
	private ItemMappingJpaDao itemMappingJpaDao;
	
	@Autowired
	private ItemTopSync itemTopSync;
	
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
	 * @param q TODO
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Item> findPageUserItems(Long userId, String q, int pageNo, int pageSize) {
		pageNo = pageNo -1;
		long start = pageNo*pageSize;
		q = "%"+q+"%";
		List<Item> items = itemDao.getItems(userId, q, start, pageSize);
		long total = itemDao.getTotalResults(userId, q);
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
	 * @param skuTitle TODO
	 * @param tbitemId
	 * @param tbItemTitle
	 * @param tbItemUrl
	 */
	@Transactional(readOnly = false)
	public void relateItem(Long itemId,	com.taobao.api.domain.Item tbItem,	Long  skuid, String skuTitle ) {
		if (getRelatedItemId(tbItem.getNumIid(), skuid) == null) {
			ItemMapping mapping = new ItemMapping();
			Item localItem = new Item();
			localItem.setId(itemId);
			mapping.setItem(localItem);
			mapping.setNumIid(tbItem.getNumIid());
			mapping.setSkuId(skuid);
			mapping.setSkuTitle(skuTitle);
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
	public void unRelateItem(Long itemId, Long numIid, Long skuId) {
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
	public Long getRelatedItemId(Long numIid, Long skuId) {
		return itemDao.getRelatedItemId(numIid, skuId);
	}	
	
	/**
	 * 删除商品
	 * @param id
	 */
	public void deleteItem(Long id) {
		itemDao.unRelateAll(id);
		itemDao.delete(id);
	}

	/**
	 * 设置商品库位
	 * @param itemId
	 * @param position
	 */
	public void position(Long itemId, String position) {
		itemDao.updatePositin(itemId, position);
	}
	
	/**
	 * 同步所有淘宝商品
	 * @throws ApiException
	 */
	public void syncTop() throws ApiException {
		itemTopSync.sync();
	}
	
	/**
	 * 同步淘宝商品
	 * @param numIid
	 * @param skuId
	 * @throws ApiException
	 */
	public void syncTop(Long numIid,Long skuId) throws ApiException {
		itemTopSync.sync(numIid, skuId);
	}	
	
}
