package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Item;


@MyBatisRepository
public interface ItemDao {
	
	Item get(Long id);
	
	void delete(Long id);
	
	// 获取用户商品
	long getTotalResults(Long userId);
	
	// 用户获取商品(分页)
	List<Item> getItems(Long userId, long start, long offset);
	
	// 解除关联淘宝商品
	void unRelate(Long itemId, Long numIid, String skuId);	
	
	//查询淘宝商品已关联的系统商品ID
	Long getRelatedItemId(Long numIid, String skuId);
	
	// 查询已关联的淘宝商品
	Map<String,Object> related(Long itemId);
	
}
