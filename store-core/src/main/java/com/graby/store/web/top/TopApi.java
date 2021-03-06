package com.graby.store.web.top;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.graby.store.base.AppException;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Shop;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.LogisticsOfflineSendRequest;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.LogisticsOfflineSendResponse;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.UserSellerGetResponse;

public class TopApi {
	
	/**
	 * 交易状态
	 */
	public interface TradeStatus {
		
		/** 没有创建支付宝交易  */
		String TRADE_NO_CREATE_PAY = "TRADE_NO_CREATE_PAY";
		
		/** 等待买家付款  */
		String TRADE_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
		
		/** 等待卖家发货,即:买家已付款 */
		String TRADE_WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS"; 
		
		/** 等待买家确认收货,即:卖家已发货  */
		String TRADE_WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";
		
		/** 买家已签收,货到付款专用 */
		String TRADE_BUYER_SIGNED = "TRADE_BUYER_SIGNED";
		
		/** 交易成功  */
		String TRADE_FINISHED = "TRADE_FINISHED";
		
		/** 交易关闭 */
		String TRADE_CLOSED = "TRADE_CLOSED";
		
		/** 交易被淘宝关闭 */
		String TRADE_CLOSED_BY_TAOBAO = "TRADE_CLOSED_BY_TAOBAO";
		
		/** 包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY */
		String TRADE_ALL_WAIT_PAY = "ALL_WAIT_PAY";
		
		/** 包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO */
		String TRADE_ALL_CLOSED = "ALL_CLOSED";
		
	}
	
	/**
	 * 物流公司
	 * @author huabiao.mahb
	 *
	 */
	public interface CompanyCode {
		
		/** 韵达 */
		String YUNDA = "YUNDA";

		/** 顺丰 */
		String SF = "SF";
	}

	// 默认开发环境
	
	private String appKey = "1021395257";
	private String appSecret = "sandbox0475ca7f0a4a47a3d5303014e";
	private String serverUrl = "http://gw.api.tbsandbox.com/router/rest";

	private DefaultTaobaoClient client;

	public void init() {
		client = new DefaultTaobaoClient(serverUrl, appKey, appSecret, "json");
	}

	// 默认商品页面大小
	private static final int ITEM_PAGE_SIZE = 10;
	
	// 商品属性
	private static final String ITEM_PROPS = "num_iid,title,detail_url,props,valid_thru,sku,skus";

	/**
	 * 获取当前卖家nick
	 * 
	 * @return
	 * @throws ApiException
	 */
	public String getNick(String sessionKey) throws ApiException {
		UserSellerGetRequest req=new UserSellerGetRequest();
		req.setFields("nick");
		UserSellerGetResponse resp = client.execute(req, sessionKey);
		errorMsgConvert(resp);
		return resp.getUser().getNick();
	}

	/**
	 * 获取当前用户店铺信息
	 * 
	 * @return
	 * @throws ApiException
	 */
	public Shop getShop(String nick) throws ApiException {
		ShopGetRequest req = new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,bulletin,pic_path,created,modified");
		req.setNick(nick);
		ShopGetResponse resp = client.execute(req);
		errorMsgConvert(resp);
		return resp.getShop();
	}

	/**
	 * 获取当前所有商品(库存+出售)
	 * 最大20条
	 * 
	 * @return
	 * @throws ApiException
	 */
	public List<Item> getTopItems(String q) throws ApiException {
		List<Item> onsaleItems = getOnsaleItems(q, 1, ITEM_PAGE_SIZE);
		List<Item> inventoryItems = getInventoryItems(q, 1, ITEM_PAGE_SIZE);
		List<Item> items = new ArrayList<Item>();
		if (CollectionUtils.isNotEmpty(onsaleItems)) {
			items.addAll(onsaleItems);
		}
		if (CollectionUtils.isNotEmpty(inventoryItems)) {
			items.addAll(inventoryItems);
		}
		List<Item> results = new ArrayList<Item>();
		for (Item item : items) {
			Item e = getItem(item.getNumIid());
			// TODO 正则去掉sku propnames
			results.add(e);
		}
		return results;
	}
	
	/**
	 * 获取出售中的商品列表
	 * @param q
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApiException
	 */
	private List<Item> getOnsaleItems(String q, long pageNo, long pageSize) throws ApiException {
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid");
		req.setQ(q);
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		ItemsOnsaleGetResponse resp = client.execute(req, session());
		errorMsgConvert(resp);
		return resp.getItems();
	}
	
	/**
	 * 获取库存中的商品列表 
	 */
	private List<Item> getInventoryItems(String q, long pageNo, long pageSize) throws ApiException {
		ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
		req.setFields("num_iid");
		req.setQ(q);
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		ItemsInventoryGetResponse resp = client.execute(req, session());
		errorMsgConvert(resp);
		return resp.getItems();
	}


	/**
	 * 获取单个商品
	 * 
	 * @param numId
	 * @return
	 * @throws ApiException
	 */
	public Item getItem(Long numId) throws ApiException {
		ItemGetRequest req = new ItemGetRequest();
		req.setFields(ITEM_PROPS);
		req.setNumIid(numId);
		ItemGetResponse resp = client.execute(req, session());
		errorMsgConvert(resp);
		return resp.getItem();
	}

	/**
	 * 获取当前用户交易数据
	 * 
	 * @param status
	 *            交易状态
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            页面大小
	 * @return Page<Trade>
	 * @throws ApiException
	 */
	public Page<Trade> getTrades(String status, long pageNo, long pageSize) throws ApiException {
		TradesSoldGetRequest req = new TradesSoldGetRequest();
		String props = "tid,num_iid,num,total_fee,status, cod_status,shipping_type,is_lgtype,is_force_wlb,is_force_wlb,lg_aging,lg_aging_type,created,pay_time,alipay_no,"
				+ "buyer_nick,seller_nick,buyer_area,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,"
				+ "receiver_address,receiver_zip,receiver_mobile,receiver_phone";
		req.setFields(props);
		req.setStatus(status);
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		TradesSoldGetResponse resp = client.execute(req, session());
		errorMsgConvert(resp);
		PageRequest pageable = new PageRequest((int) (pageNo - 1), (int) pageSize);
		List<Trade> trades = resp.getTrades();
		Long totalResults = resp.getTotalResults();
		trades = trades == null ? new ArrayList<Trade>() : trades;
		totalResults = totalResults == null ? 0L : totalResults;
		Page<Trade> page = new PageImpl<Trade>(trades, pageable, totalResults);
		return page;
	}

	/**
	 * 获取交易详细信息
	 * 
	 * @param tid
	 * @return
	 * @throws ApiException
	 */
	public Trade getTrade(Long tid) throws ApiException {
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		String props = "tid,num_iid,type,status,num,total_fee,cod_status,shipping_type,is_lgtype,is_force_wlb,is_force_wlb,lg_aging,lg_aging_type,created,pay_time,alipay_no,"
				+ "buyer_nick,seller_nick,buyer_area,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,buyer_memo,"
				+ "receiver_address,receiver_zip,receiver_mobile,receiver_phone,has_buyer_message,buyer_message,orders";
		req.setFields(props);
		req.setTid(tid);
		TradeFullinfoGetResponse resp = client.execute(req, session());
		errorMsgConvert(resp);
		return resp.getTrade();
	}
	
	/**
	 * 用户调用该接口可实现自己联系发货（线下物流），使用该接口发货，交易订单状态会直接变成卖家已发货。不支持货到付款、在线下单类型的订单。
	 * @param tid 交易号
	 * @param outSid 运单号 like 1200722815552 
	 * @param companyCode 物流公司编号 like YUNDA
	 * @throws ApiException 
	 */
	public Shipping tradeOfflineShipping(Long tid, String outSid, String companyCode) throws ApiException {
		LogisticsOfflineSendRequest req=new LogisticsOfflineSendRequest();
		req.setTid(tid);
		req.setOutSid(outSid);
		req.setCompanyCode(companyCode);
		LogisticsOfflineSendResponse resp = client.execute(req , session());
		errorMsgConvert(resp);
		return resp.getShipping();
	}

	private String session() {
		return ShiroContextUtils.getSessionKey();
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	private void errorMsgConvert(TaobaoResponse resp) {
		if (StringUtils.isNotEmpty(resp.getErrorCode())) {
			throw new AppException(resp.getSubMsg());
		}
	}
	
	public static void main(String[] args) throws ApiException {
		String s = "12345:12345:风格:蓝色;12345:32654:风格:红色;"; 
		String regex = "[:].+?;";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
	}
}
