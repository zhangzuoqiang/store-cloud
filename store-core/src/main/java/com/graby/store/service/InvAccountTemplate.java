package com.graby.store.service;

import java.io.Serializable;

import com.graby.store.service.InvAccounts.Account;

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
public class InvAccountTemplate implements Serializable {
	
	private static final long serialVersionUID = -8711535452948265436L;

	/**
	 * 厂家发送入库单
	 * 
	 * 借:商家库存 
	 * 贷:在途 
	 */
	public static InvAccountTemplate SHIP_ENTRY_SEND =  new InvAccountTemplate(InvAccounts.SHOP_INVENTORY, InvAccounts.ONTHEWAY);
	
	/**
	 * 厂家取消发送入库单
	 * 
	 * 借:在途
	 * 贷:商家库存  
	 */
	public static InvAccountTemplate SHIP_ENTRY_CANCEL =  new InvAccountTemplate(InvAccounts.ONTHEWAY, InvAccounts.SHOP_INVENTORY);	

	
	/**
	 * 仓库入库
	 * 
	 * 借：在途   
	 * 待：可销售
	 */
	public static InvAccountTemplate STORAGE_RECEIVED_SALEABLE =  new InvAccountTemplate(InvAccounts.ONTHEWAY, InvAccounts.SALEABLE);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途
	 * 待：残次
	 */
	public static InvAccountTemplate STORAGE_RECEIVED_BADNESS_DEFECT =  new InvAccountTemplate(InvAccounts.ONTHEWAY, InvAccounts.BADNESS_DEFECT);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途 
	 * 待：机损
	 */
	public static InvAccountTemplate STORAGE_RECEIVED_BADNESS_DEMAGE_MACHINE =  new InvAccountTemplate(InvAccounts.ONTHEWAY, InvAccounts.BADNESS_DEMAGE_MACHINE);
	
	/**
	 * 仓库入库
	 * 
	 * 借：在途
	 * 待：箱损
	 */
	public static InvAccountTemplate STORAGE_RECEIVED_BADNESS_DEMAGE_BOX =  new InvAccountTemplate(InvAccounts.ONTHEWAY, InvAccounts.BADNESS_DEMAGE_BOX);
	
	
	/**
	 * 出库单确认(仓库发货) 
	 *
	 * 借:可销售 
	 * 贷:冻结
	 */
	public static InvAccountTemplate STORAGE_SEND =  new InvAccountTemplate(InvAccounts.SALEABLE, InvAccounts.FROZEN);
	
	/**
	 * 用户成功接收 (交易成功)
	 * 
	 * 借:冻结
	 * 贷:已销售
	 */
	public static InvAccountTemplate BUYER_RECEIVED =  new InvAccountTemplate(InvAccounts.FROZEN, InvAccounts.SALED);
	
	
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
	public InvAccountTemplate(Account credit, Account dibit) {
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
