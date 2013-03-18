package com.graby.store.inventory;

import com.graby.store.inventory.Accounts.Account;

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
	 * 借:商家库存 
	 * 贷:在途 
	 */
	public static AccountTemplate SHOP_SEND =  new AccountTemplate(Accounts.SHOP_INVENTORY, Accounts.ONTHEWAY);

	/**
	 * 厂家发送入库单取消
	 * 借：在途
	 * 贷: 商家库存 
	 */
	public static AccountTemplate SHOP_SEND_CANCEL = new AccountTemplate(Accounts.ONTHEWAY, Accounts.SHOP_INVENTORY);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途   
	 * 待：可销售
	 */
	public static AccountTemplate STORAGE_RECEIVED_SALEABLE =  new AccountTemplate(Accounts.ONTHEWAY, Accounts.SALEABLE);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途
	 * 待：残次
	 */
	public static AccountTemplate STORAGE_RECEIVED_BADNESS_DEFECT =  new AccountTemplate(Accounts.ONTHEWAY, Accounts.BADNESS_DEFECT);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途 
	 * 待：机损
	 */
	public static AccountTemplate STORAGE_RECEIVED_BADNESS_DEMAGE_MACHINE =  new AccountTemplate(Accounts.ONTHEWAY, Accounts.BADNESS_DEMAGE_MACHINE);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途
	 * 待：箱损
	 */
	public static AccountTemplate STORAGE_RECEIVED_BADNESS_DEMAGE_BOX =  new AccountTemplate(Accounts.ONTHEWAY, Accounts.BADNESS_DEMAGE_BOX);
	
	
	/**
	 * 出库单确认(仓库发货) 
	 *
	 * 借:可销售 
	 * 贷:冻结
	 */
	public static AccountTemplate STORAGE_SEND =  new AccountTemplate(Accounts.SALEABLE, Accounts.FROZEN);
	
	/**
	 * 用户成功接收 (交易成功)
	 * 
	 * 借:冻结
	 * 贷:已销售
	 */
	public static AccountTemplate TRADE_FINISH =  new AccountTemplate(Accounts.FROZEN, Accounts.SALED);
	
	
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
