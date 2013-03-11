package com.graby.store.web.top;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.graby.store.web.auth.AuthContextUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Shop;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.UserGetResponse;

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



	private String appKey;
	private String appSecret;
	private String serverUrl;

	private DefaultTaobaoClient client;

	public void init() {
		client = new DefaultTaobaoClient(serverUrl, appKey, appSecret, "json");
	}

	// 商品属性
	private static final String ITEM_PROPS = "num_iid,title,detail_url,props,valid_thru";

	/**
	 * 获取当前用的nick
	 * 
	 * @return
	 * @throws ApiException
	 */
	public String getNick(String sessionKey) throws ApiException {
		UserGetRequest req = new UserGetRequest();
		req.setFields("nick");
		UserGetResponse resp = client.execute(req, sessionKey);
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
		return resp.getShop();
	}

	/**
	 * 获取当前所有商品(库存+出售)
	 * 最大600条
	 * 
	 * @return
	 * @throws ApiException
	 */
	public List<Item> getTopItems(String q) throws ApiException {
		Page<Item> onsaleItems = getOnsaleItems(q, 1L, 300);
		Page<Item> inventoryItems = getInventoryItems(q, 1L, 300);
		List<Item> items = new ArrayList<Item>();
		items.addAll(onsaleItems.getContent());
		items.addAll(inventoryItems.getContent());
		return items;
	}
	
	/**
	 * 获取出售中的商品列表
	 * @param q
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApiException
	 */
	public Page<Item> getOnsaleItems(String q, long pageNo, long pageSize) throws ApiException {
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields(ITEM_PROPS);
		req.setQ(q);
		req.setPageNo(pageNo);
		ItemsOnsaleGetResponse resp = client.execute(req, session());
		PageRequest pageable = new PageRequest((int) (pageNo - 1), (int) pageSize);
		Page<Item> page = new PageImpl<Item>(resp.getItems(), pageable, resp.getTotalResults());
		return page;
	}
	
	/**
	 * 获取库存中的商品列表 
	 */
	public Page<Item> getInventoryItems(String q, long pageNo, long pageSize) throws ApiException {
		ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
		req.setFields(ITEM_PROPS);
		req.setQ(q);
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		ItemsInventoryGetResponse resp = client.execute(req, session());
		PageRequest pageable = new PageRequest((int) (pageNo - 1), (int) pageSize);
		Page<Item> page = new PageImpl<Item>(resp.getItems(), pageable, resp.getTotalResults());
		return page;
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
		PageRequest pageable = new PageRequest((int) (pageNo - 1), (int) pageSize);
		Page<Trade> page = new PageImpl<Trade>(resp.getTrades(), pageable, resp.getTotalResults());
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
				+ "receiver_address,receiver_zip,receiver_mobile,receiver_phone,has_buyer_message,orders";
		req.setFields(props);
		req.setTid(tid);
		TradeFullinfoGetResponse resp = client.execute(req, session());
		return resp.getTrade();
	}

	private String session() {
		return AuthContextUtils.getSessionKey();
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

}
