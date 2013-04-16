package com.graby.store.web.jstl;

import org.drools.core.util.StringUtils;

public class TradeDesc {

	/**
	 * 可选值 fixed(一口价) auction(拍卖) guarantee_trade(一口价、拍卖) auto_delivery(自动发货)
	 * independent_simple_trade(旺店入门版交易) independent_shop_trade(旺店标准版交易) ec(直冲)
	 * cod(货到付款) fenxiao(分销) game_equipment(游戏装备) shopex_trade(ShopEX交易)
	 * netcn_trade(万网交易) external_trade(统一外部交易) step (万人团)
	 * 
	 * @param type
	 * @return
	 */
	public static String getType(String type) {
		if (StringUtils.isEmpty(type)) {
			return "";
		}
		if (type.equals("fixed")) {
			return "一口价";
		}
		if (type.equals("auction")) {
			return "拍卖";
		}
		if (type.equals("guarantee_trade")) {
			return "一口价、拍卖";
		}
		if (type.equals("auto_delivery")) {
			return "自动发货";
		}
		if (type.equals("independent_simple_trade")) {
			return "旺店入门版交易";
		}
		if (type.equals("independent_shop_trade")) {
			return "independent_shop_trade";
		}
		if (type.equals("ec")) {
			return "直冲";
		}
		if (type.equals("netcn_trade")) {
			return "万网交易";
		}
		if (type.equals("external_trade")) {
			return "统一外部交易";
		}
		if (type.equals("step")) {
			return "万人团";
		}
		return null;
	}

	/**
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) * WAIT_BUYER_PAY(等待买家付款) *
	 * SELLER_CONSIGNED_PART(卖家部分发货) * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) *
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) *
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) * TRADE_FINISHED(交易成功) *
	 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) *
	 * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
	 * 
	 * @param status
	 * @return
	 */
	public static String getStatus(String status) {
		if (StringUtils.isEmpty(status)) {
			return "";
		}
		if (status.equals("TRADE_NO_CREATE_PAY")) {
			return "没有创建支付宝交易";
		}
		if (status.equals("WAIT_BUYER_PAY")) {
			return "等待买家付款";
		}
		if (status.equals("SELLER_CONSIGNED_PART")) {
			return "卖家部分发货";
		}
		if (status.equals("WAIT_SELLER_SEND_GOODS")) {
			return "等待卖家发货";
		}
		if (status.equals("WAIT_BUYER_CONFIRM_GOODS")) {
			return "等待买家确认收货";
		}
		if (status.equals("TRADE_BUYER_SIGNED")) {
			return "买家已签收";
		}
		if (status.equals("TRADE_FINISHED")) {
			return "交易成功";
		}
		if (status.equals("TRADE_CLOSED")) {
			return "付款以后用户退款成功，交易自动关闭";
		}
		if (status.equals("TRADE_CLOSED_BY_TAOBAO")) {
			return "付款以前，卖家或买家主动关闭交易";
		}
		return "";
	}
}
