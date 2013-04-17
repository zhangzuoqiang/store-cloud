package com.graby.store.web.jstl;

import java.util.HashMap;
import java.util.Map;

public class Desc {
	
	private static Map<String,String> tradeTypeMap = new HashMap<String,String>();
	private static Map<String,String> tradeStatusMap = new HashMap<String,String>();
	private static Map<String,String> shipStatusMap = new HashMap<String,String>();
	
	static {
		
		// ---------------交易类型---------------//
		tradeTypeMap.put("fixed", "一口价");
		tradeTypeMap.put("auction", "拍卖");
		tradeTypeMap.put("step", "分阶段付款");
		tradeTypeMap.put("guarantee_trade", "一口价、拍卖");
		tradeTypeMap.put("independent_simple_trade", "旺店入门版交易");
		tradeTypeMap.put("independent_shop_trade", "旺店标准版交易");
		tradeTypeMap.put("auto_delivery", "自动发货");
		tradeTypeMap.put("ec", "直冲");
		tradeTypeMap.put("cod", "货到付款");
		tradeTypeMap.put("fenxiao", "分销");
		tradeTypeMap.put("game_equipment", "游戏装备");
		tradeTypeMap.put("shopex_trade", "ShopEX交易");
		tradeTypeMap.put("netcn_trade", "万网交易");
		tradeTypeMap.put("external_trade", "统一外部交易");
		tradeTypeMap.put("instant_trade", "即时到账");
		tradeTypeMap.put("b2c_cod", "大商家货到付款");
		tradeTypeMap.put("hotel_trade", "酒店类型交易");
		tradeTypeMap.put("taohua", "商超交易");
		tradeTypeMap.put("waimai", "商超货到付款交易");
		tradeTypeMap.put("nopaid", "即时到帐/趣味猜交易类型");
		tradeTypeMap.put("eticket", "电子凭证");
		
		// --------------淘宝交易订单状态----------------//
		tradeStatusMap.put("TRADE_NO_CREATE_PAY", "没有创建支付宝交易");
		tradeStatusMap.put("WAIT_BUYER_PAY", "等待买家付款");
		tradeStatusMap.put("SELLER_CONSIGNED_PART", "卖家部分发货");
		tradeStatusMap.put("WAIT_SELLER_SEND_GOODS", "买家已付款，等待卖家发货");
		tradeStatusMap.put("WAIT_BUYER_CONFIRM_GOODS", "等待买家确认收货");
		tradeStatusMap.put("TRADE_BUYER_SIGNED", "买家已签收");
		tradeStatusMap.put("TRADE_FINISHED", "交易成功");
		tradeStatusMap.put("TRADE_CLOSED", "退款交易关闭");
		tradeStatusMap.put("TRADE_CLOSED_BY_TAOBAO", "付款以前，卖家或买家主动关闭交易");

		// --------------仓库交易订单状态----------------//
		tradeStatusMap.put("TRADE_WAIT_CENTRO_AUDIT", "等待物流通审核");
		tradeStatusMap.put("TRADE_WAIT_EXPRESS_SHIP", "等待快递配送");
		tradeStatusMap.put("TRADE_WAIT_EXPRESS_NOFITY", "等待通知用户签收");
		tradeStatusMap.put("TRADE_WAIT_BUYER_RECEIVED", "等待买家签收");

		// --------------发货单状态----------------//
		shipStatusMap.put("WAIT_EXPRESS_RECEIVED", "创建成功，等待物流接收");
		shipStatusMap.put("WAIT_EXPRESS_PICKING", "运单打印成功，等待拣货");
		shipStatusMap.put("WAIT_BUYER_RECEIVED", "物流方已发货，等待买家签收");
		shipStatusMap.put("SEND_FINISH", "出库单发货完成");
	}
	
	public static String getTradeType(String type) {
		return tradeTypeMap.get(type);
	}

	public static String getTradeStatus(String status) {
		return tradeStatusMap.get(status);
	}
	
	public static String getShipStatus(String status) {
		return shipStatusMap.get(status);
	}	
	
}
