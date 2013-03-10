package com.graby.store.portal.inventory;

import com.graby.store.portal.inventory.Accounts.Account;

/**
 * 库存科目记账模板
 * 
1. 入库单发送

在途 + 
商铺库存 -

-------------------------
2. 仓库接收，入库单确认

在途 - 
可销售  +
  残次  +  
  机损  + 
  箱损  +
  
------------------------- 
2.2 入库单取消发送

在途 - 
商铺库存 +

------------------------- 
3. 出库单确认(发货)

可销售 -
冻结 +

-------------------------
4.用户成功接收
冻结 -
已销售 +

-------------------------
4.2 退货（出库单接收失败）
冻结 -
不良品  （总数）+ 
   残次  +  
   机损  + 
   箱损  +
-------------------------
 * @author huabiao.mahb
 */
public class AccountTemplate {
	
	/**
	 * 厂家发送入库单
	 * 
	 * 借:在途 (借方)
	 * 贷:商家库存 (待方)
	 */
	public static AccountTemplate SHOP_SEND =  new AccountTemplate(Accounts.ONTHEWAY, Accounts.SHOP_INVENTORY);

	/**
	 * 厂家发送入库单取消
	 * 借：商家库存 (待方)
	 * 贷：在途 (借方)
	 */
	public static AccountTemplate SHOP_SEND_CANCEL = new AccountTemplate(Accounts.SHOP_INVENTORY, Accounts.ONTHEWAY);
	
	/**
	 * 仓库入库
	 * 
	 * 借：可销售 (借方)  
	 * 待：在途 (借方)
	 */
	public static AccountTemplate STORAGE_RECEIVED_SALEABLE =  new AccountTemplate(Accounts.SALEABLE, Accounts.ONTHEWAY);
	
	/**
	 * 仓库入库
	 * 
	 * 借：残次(借方)  
	 * 待：在途 (借方)
	 */
	public static AccountTemplate STORAGE_RECEIVED_BADNESS_DEFECT =  new AccountTemplate(Accounts.BADNESS_DEFECT, Accounts.ONTHEWAY);
	
	/**
	 * 仓库入库
	 * 
	 * 借： 机损(借方)  
	 * 待：在途 (借方)
	 */
	public static AccountTemplate STORAGE_RECEIVED_BADNESS_DEMAGE_MACHINE =  new AccountTemplate(Accounts.BADNESS_DEMAGE_MACHINE, Accounts.ONTHEWAY);
	
	/**
	 * 仓库入库
	 * 
	 * 借： 箱损(借方)  
	 * 待：在途 (借方)
	 */
	public static AccountTemplate STORAGE_RECEIVED_BADNESS_DEMAGE_BOX =  new AccountTemplate(Accounts.BADNESS_DEMAGE_BOX, Accounts.ONTHEWAY);
	
	
	/**
	 * 出库单确认(仓库发货) 
	 *
	 * 借:冻结  (借)
	 * 贷:可销售 (借)
	 */
	public static AccountTemplate STORAGE_SEND =  new AccountTemplate(Accounts.FROZEN, Accounts.SALEABLE);
	
	/**
	 * 用户成功接收 (交易成功)
	 * 
	 * 借:已销售(借)
	 * 贷:冻结 (借)
	 */
	public static AccountTemplate TRADE_FINISH =  new AccountTemplate(Accounts.SALED, Accounts.FROZEN);
	
	
	/**
	 * ---------- 模板构造  ----------
	 */
	
	private Account  credit;
	private Account  dibit;
	
	/**
	 * 记账模板
	 * @param credit 借方
	 * @param dibit 贷方
	 */
	public AccountTemplate(Account credit, Account dibit) {
		this.credit = credit;
		this.dibit = dibit;
	}

	public Account getCredit() {
		return credit;
	}

	public Account getDibit() {
		return dibit;
	}
	
}
