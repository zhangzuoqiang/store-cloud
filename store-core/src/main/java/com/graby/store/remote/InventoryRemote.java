package com.graby.store.remote;

import java.util.List;
import java.util.Map;

/**
 * 库存服务
 * serviceUrl = "/inventory.call"
 */
public interface InventoryRemote {

	/**
	 * 按科目统计库存值
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @return
	 */
	public Long getValue(Long centroId, Long itemId, String account);

	/**
	 * 库存统计
	 * @param centroId
	 * @param userId
	 * @return
	 */
	List<Map<String, Long>> stat(Long centroId, Long userId); 
}