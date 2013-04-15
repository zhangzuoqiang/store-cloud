package com.graby.store.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.InventoryDao;
import com.graby.store.service.InvAccounts.Account;

@Component
@Transactional(readOnly = true)
public class InventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	/**
	 * 库存记账 同向增加，反向减少
	 * 
	 * @param centroId
	 *            仓库ID
	 * @param itemId
	 *            商品ID
	 * @param num
	 *            数量
	 * @param template
	 *            记账模板
	 */
	public void input(Long centroId, Long userId, Long itemId, long num, InvAccountTemplate template) {
		// 借方
		Account credit = template.getCredit();
		increase(centroId, userId, itemId, credit.getCode(), credit.getDirection().isCredit() ? num : -num);
		// 贷方
		Account dibit = template.getDibit();
		increase(centroId, userId, itemId, dibit.getCode(), dibit.getDirection().isDebit() ? num : -num);
	}

	/**
	 * 记账
	 * 
	 * @param centroId
	 * @param userId
	 * @param itemId
	 * @param entrys
	 */
	public void inputs(Long centroId, Long userId, Long itemId, InvAccountEntry[] entrys) {
		for (InvAccountEntry e : entrys) {
			input(centroId, userId, itemId, e.getNum(), e.getAccountTemplate());
		}
	}

	/**
	 * 记账
	 * 
	 * @param itemId
	 *            商品ID
	 * @param type
	 *            科目类型
	 * @param num
	 *            数量
	 */
	private void increase(Long centroId, Long userId, Long itemId, String account, long num) {
		int exist = inventoryDao.existAccount(centroId, itemId, account);
		if (exist == 0) {
			inventoryDao.insert(centroId, userId, itemId, account, num);
		} else {
			inventoryDao.increase(centroId, itemId, account, num);
		}
	}

	/**
	 * 库存统计
	 * @param centroId
	 * @param userId
	 * @return
	 */
	public List<Map<String, Long>> stat(Long centroId, Long userId) {
		return inventoryDao.stat(centroId, userId);
	}
	
	/**
	 * 按科目统计库存值
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @return
	 */
	public Long getValue(Long centroId, Long itemId, String account) {
		return inventoryDao.getValue(centroId, itemId, account);
	}
	
	
}
