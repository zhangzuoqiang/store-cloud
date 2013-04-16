package com.graby.store.web.jstl;

import java.util.HashMap;
import java.util.Map;

import org.drools.core.util.StringUtils;

public class TradeDesc {
	
	private static Map<String,String> typeMap = new HashMap<String,String>();
	private static Map<String,String> statusMap = new HashMap<String,String>();
	
	static {
		
		// ---------------交易类型---------------//
		typeMap.put("fixed", "一口价");
		typeMap.put("auction", "拍卖");
		typeMap.put("step", "分阶段付款");
		typeMap.put("guarantee_trade", "一口价、拍卖");
		typeMap.put("independent_simple_trade", "旺店入门版交易");
		typeMap.put("independent_shop_trade", "旺店标准版交易");
		typeMap.put("auto_delivery", "自动发货");
		typeMap.put("ec", "直冲");
		typeMap.put("cod", "货到付款");
		typeMap.put("fenxiao", "分销");
		typeMap.put("game_equipment", "游戏装备");
		typeMap.put("shopex_trade", "ShopEX交易");
		typeMap.put("netcn_trade", "万网交易");
		typeMap.put("external_trade", "统一外部交易");
		typeMap.put("instant_trade", "即时到账");
		typeMap.put("b2c_cod", "大商家货到付款");
		typeMap.put("hotel_trade", "酒店类型交易");
		typeMap.put("taohua", "商超交易");
		typeMap.put("waimai", "商超货到付款交易");
		typeMap.put("nopaid", "即时到帐/趣味猜交易类型");
		typeMap.put("eticket", "电子凭证");
		
		// --------------交易状态----------------//
		statusMap.put("TRADE_NO_CREATE_PAY", "没有创建支付宝交易");
		statusMap.put("WAIT_BUYER_PAY", "等待买家付款");
		statusMap.put("SELLER_CONSIGNED_PART", "卖家部分发货");
		statusMap.put("WAIT_SELLER_SEND_GOODS", "买家已付款，等待卖家发货");
		statusMap.put("WAIT_BUYER_CONFIRM_GOODS", "等待买家确认收货");
		statusMap.put("TRADE_BUYER_SIGNED", "买家已签收");
		statusMap.put("TRADE_FINISHED", "交易成功");
		statusMap.put("TRADE_CLOSED", "退款交易关闭");
		statusMap.put("TRADE_CLOSED_BY_TAOBAO", "付款以前，卖家或买家主动关闭交易");
	}
	
	public static String getType(String type) {
		if (StringUtils.isEmpty(type)) {
			return "";
		}
		return typeMap.get(type);
	}

	public static String getStatus(String status) {
		if (StringUtils.isEmpty(status)) {
			return "";
		}
		return statusMap.get(status);
	}
}
