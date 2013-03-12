package com.graby.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.ItemJpaDao;
import com.graby.store.dao.jpa.ItemMappingJpaDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemMapping;
import com.graby.store.web.auth.AuthContextUtils;

@Component
@Transactional(readOnly = true)
public class ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemJpaDao itemJpaDao;
	
	@Autowired
	private ItemMappingJpaDao itemMappingJpaDao;
	
	public void saveItem(Item item) {
		setCurrentUserid(item);
		itemJpaDao.save(item);
	}

	private void setCurrentUserid(Item item) {
		if (item.getUserid() == null) {
			item.setUserid(AuthContextUtils.getUserid());	
		}
	}
	
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
//		Map<String, Object> params = new HashMap<String, Object>();
//		long start = (pageNo-1)*pageSize;
//		params.put("start", start);
//		params.put("offset", pageSize);
//		params.put("userId", userId);
//		List<Item> items = itemDao.getItems(params);
//		long total = itemDao.getTotalResults(userId);
//		PageRequest pageable = new PageRequest((int)pageNo, (int)pageSize);
//		Page<Item> page = new PageImpl<Item>(items, pageable, total);
//		return page;
		pageNo = pageNo -1;
		return itemJpaDao.findByUserid(userId, new PageRequest(pageNo, pageSize));
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
	public void relateItem(Long itemId, Long tbitemId, String tbItemTitle, String tbItemUrl) {
		if (getRelatedItemId(tbitemId) == null) {
			ItemMapping mapping = new ItemMapping();
			Item item = new Item();
			item.setId(itemId);
			mapping.setItem(item);
			mapping.setTbItemId(tbitemId);
			mapping.setTbItemTitle(tbItemTitle);
			mapping.setTbItemDetailurl(tbItemUrl);
			itemMappingJpaDao.save(mapping);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("itemId", itemId);
//			map.put("tbitemId", tbitemId);
//			map.put("tbItemTitle", tbItemTitle);
//			map.put("tbItemUrl", tbItemUrl);
//			itemDao.relate(map);
		}
	}
	
	/**
	 * 解除淘宝商品关联
	 * @param itemId
	 * @param numIid
	 */
	public void unRelateItem(Long itemId, Long numIid) {
		if (getRelatedItemId(numIid) != null) {
			itemDao.unRelate(itemId, numIid);
		}
	}	
	
	/**
	 * 淘宝商品关联的商品ID
	 * @param itemId
	 * @param numIid
	 * @return
	 */
	public Long getRelatedItemId(Long numIid) {
		return itemDao.getRelatedItemId(numIid);
	}	
	
	public void deleteItem(Long id) {
		itemDao.delete(id);
	}
	
	public List<Item> getUserItems(Long userid) {
		return null;
	}
	
}
