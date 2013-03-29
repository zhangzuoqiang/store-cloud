package com.graby.store.remote;

import java.util.List;

import org.springframework.data.domain.Page;

import com.graby.store.entity.Item;

/**
 * 商品服务
 * serviceUrl = "/item.call"
 */
public interface ItemRemote {

	/**
	 * 获取商品
	 * @param id
	 * @return
	 */
	public Item getItem(Long id);

	/**
	 * 获取用户商品 分页
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Item> findPageUserItems(Long userId, int pageNo, int pageSize);

	/**
	 * 获取用户商品
	 * 
	 * @param userid
	 * @return
	 */
	public List<Item> findUserItems(Long userid);

	/**
	 * 淘宝商品关联的商品ID
	 * 
	 * @param itemId
	 * @param numIid
	 * @return
	 */
	public Long getRelatedItemId(Long numIid, String skuId);

}