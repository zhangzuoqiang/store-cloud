package com.graby.store.remote;

public interface InventoryRemote {

	/**
	 * 按科目统计库存值
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @return
	 */
	public Long getValue(Long centroId, Long itemId, String account);

}