package com.graby.store.inventory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.InventoryDao;
import com.graby.store.inventory.Accounts.Account;

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
	public void input(Long centroId, Long userId, Long itemId, long num, AccountTemplate template) {
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
	public void inputs(Long centroId, Long userId, Long itemId, AccountEntry[] entrys) {
		for (AccountEntry e : entrys) {
			input(centroId, userId, itemId, e.getNum(), e.getAccountTemplate());
		}
	}

	/**
	 * 增加库存记录
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
	 * @param itemId
	 * @return
	 */
	public List<Map<String, Long>> stat(Long centroId, Long itemId) {
		return inventoryDao.stat(centroId, itemId);
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
	
	
	/**
	 * 记账条目
	 */
	public static class AccountEntry {

		private AccountTemplate accountTemplate;
		private long num;

		public AccountEntry(AccountTemplate template, long num) {
			this.accountTemplate = template;
			this.num = num;
		}

		public long getNum() {
			return num;
		}

		public AccountTemplate getAccountTemplate() {
			return accountTemplate;
		}
	}

	/**
	 * 批量记账条目，一般为1个订单的明细对应多种记账模板。
	 */
	public static class AccountEntrys {

		private Long centroId;
		private Long userId;
		private Long itemId;
		private AccountEntry[] entrys;

		public Long getItemId() {
			return itemId;
		}

		public AccountEntry[] getEntrys() {
			return entrys;
		}

		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}

		public void setEntrys(AccountEntry[] entrys) {
			this.entrys = entrys;
		}

		public Long getCentroId() {
			return centroId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setCentroId(Long centroId) {
			this.centroId = centroId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}
	}

}
