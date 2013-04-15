package com.graby.store.service.trade;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.base.GroupMap;
import com.graby.store.dao.jpa.TradeJpaDao;
import com.graby.store.dao.jpa.TradeOrderJpaDao;
import com.graby.store.dao.mybatis.TradeDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;
import com.graby.store.service.inventory.InvAccounts;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.util.EncryptUtil;
import com.graby.store.web.top.TopApi;
import com.graby.store.web.top.TradeAdapter;
import com.taobao.api.ApiException;

@Component
@Transactional(readOnly = true)
public class TradeService {
	
	private static final int DEFAULT_TOP_TRADE_FETCH = 30000;

	// 是否合并淘宝订单
	@Value("${trade.combine}")
	private  boolean combine;
	
	@Autowired
	private TradeJpaDao tradeJpaDao;
	
	@Autowired
	private TradeOrderJpaDao tradeOrderJpaDao;

	@Autowired
	private TradeDao tradeDao;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private TradeAdapter tradeAdapter;
	
	@Autowired
	private ItemService itemServie;
	
	@Autowired
	private InventoryService inventoryService;	

	
	/* ====================== 交易相关查询 ======================= */
	
	/**
	 * 查询今天凌晨到现在的所有订单
	 * useable   : 可发送的
	 * related   : 已由物流通处理的
	 * failed   : 订购商品 未关联的 无库存
	 * @return
	 * @throws Exception 
	 */
	public GroupMap<String, Trade> fetchTopTrades(Date start,Date end) throws Exception {
		GroupMap<String, Trade> groupResults = new GroupMap<String, Trade>(); 
		List<com.taobao.api.domain.Trade> trades = topApi.getTrades(start, end);
		if (CollectionUtils.isEmpty(trades)) {return groupResults;}
		
		for (com.taobao.api.domain.Trade topTrade : trades) {
			Trade trade = tradeAdapter.adapter(topTrade);
			// 是否已创建
			TradeMapping mapping = getRelatedMapping(topTrade.getTid());
			if (mapping != null) {
				trade.setStatus(mapping.getStatus());
				groupResults.put("related", trade);
			} else {
				// 未创建的订单
				boolean useable = true;
				for (TradeOrder order : trade.getOrders()) {
					Long numIid = order.getNumIid();
					Long skuId = order.getSkuId();
					// 是否已关联
					Long itemId = itemServie.getRelatedItemId(numIid ,skuId);
					if (itemId == null) {
						// 未关联
						order.setStockNum(-1);
						useable = false;
					} else {
						long stockNum = inventoryService.getValue(1L, itemId, InvAccounts.CODE_SALEABLE);
						order.setStockNum(stockNum);
						Item item = itemServie.getItem(itemId);
						order.setItem(item);
						// 库存数量
						if (stockNum <= 0) {
							useable = false;
						}
					}
				}
				groupResults.put(useable? "useable" : "failed", trade);
			}
		}
		return groupResults;
	}
	
	/**
	 * 查询淘宝交易ID是否已存在系统交易ID
	 * @param tid 淘宝交易ID
	 * @return 系统交易ID
	 */
	public Long getRelatedTradeId(Long tid) {
		return tradeDao.getRelatedTradeId(tid);
	}
	
	/**
	 * 根据淘宝交易ID获取系统Mapping
	 * @param tid
	 * @return
	 */
	public TradeMapping getRelatedMapping(Long tid) {
		return tradeDao.getRelatedMapping(tid);
	}
	
	/**
	 * 根据系统交易ID查询淘宝交易ID
	 * @param tid
	 * @return
	 */
	public Long getRelatedTid(Long tradeId) {
		return tradeDao.getRelatedTid(tradeId);
	}	

	/**
	 * 查询最近50条待处理系统订单(等待物流通审核)
	 * @return
	 */
	public List<Trade> findWaitAuditTrades() {
		return tradeDao.findWaitAuditTrades();
	}
	
	/**
	 * 查询用户交易订单 
	 * @param userId 用户ID
	 * @return
	 */
	public Page<Trade> findUserTrades(Long userId, String status, long pageNo, long pageSize) {
		long start = (pageNo-1)*pageSize;
		long offset = pageSize;
		List<Trade> trades = tradeDao.getTrades(userId, "%"+status+"%", start, offset);
		long total = tradeDao.getTotalResults(userId, status);
		PageRequest pageable = new PageRequest((int)pageNo, (int)pageSize);
		Page<Trade> page = new PageImpl<Trade>(trades, pageable, total);
		return page;
	}
	
	
	/**
	 * 获取系统交易订单
	 * @param id
	 * @return
	 */
	public Trade getTrade(Long id) {
		return tradeDao.getTrade(id);
	}
	
	/* ====================== 交易处理======================= */
	
	/**
	 * 根据淘宝交易ID批量创建系统交易
	 * 合并收货方相同的订单。
	 * @param tids
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	public void createTradesFromTop(String[] tids) throws NumberFormatException, ApiException {
		if (tids == null || tids.length == 0) {
			return;
		}
		GroupMap<String, com.taobao.api.domain.Trade> tradeGroup = new GroupMap<String, com.taobao.api.domain.Trade>();
		for (String tid : tids) {
			com.taobao.api.domain.Trade topTrade = topApi.getTrade(Long.valueOf(tid));
			tradeGroup.put(hashAdress(topTrade), topTrade);
		}
		
		Set<String> keys = tradeGroup.getKeySet();
		for (String key : keys) {
			List<com.taobao.api.domain.Trade> topTrades = tradeGroup.getList(key);
			// 是否合并订单
			if (topTrades.size()>1 && combine) {
				Long[] tidArray = new Long[topTrades.size()];
				tidArray[0] = topTrades.get(0).getTid();
				// 第一个订单
				Trade trade = tradeAdapter.adapter(topTrades.get(0));
				// 合并其他订单
				for (int i = 1; i < topTrades.size(); i++) {
					Trade others = tradeAdapter.adapter(topTrades.get(i));
					trade.getOrders().addAll(others.getOrders());
					if (StringUtils.isNotBlank(others.getBuyerMessage())) {
						trade.setBuyerMessage(trade.getBuyerMessage() + "," + others.getBuyerMessage());
					}
					tidArray[i] = topTrades.get(i).getTid();
				}
				trade.setTradeFrom(StringUtils.join(tidArray, ","));
				createTrade(trade, null);
				for (Long tid : tidArray) {
					mappingTrade(tid, trade.getId());
				}
			} else {
				for (com.taobao.api.domain.Trade topTrade : topTrades) {
					Trade trade = tradeAdapter.adapter(topTrade);
					createTrade(trade, topTrade.getTid());
				}
			}
		}
		
	}
	
	// 根据收货人详细地址Hash
	private String hashAdress(com.taobao.api.domain.Trade trade) {
		StringBuffer buf = new StringBuffer();
		buf.append(trade.getReceiverState()).append(trade.getReceiverCity()).append(trade.getReceiverDistrict());
		buf.append(trade.getReceiverAddress()).append(trade.getReceiverName()).append(trade.getReceiverMobile());
		return EncryptUtil.md5(buf.toString());
	}
	
	
	/**
	 * 创建系统交易.
	 * TODO 都用mybatis
	 * @param trade
	 * @param tid TODO
	 */
	@Transactional(readOnly = false)
	public Trade createTrade(Trade trade, Long tid) {
		// 保存至系统订单
		Long tradeId =getRelatedTradeId(tid);
		if (tradeId == null) {
			// 状态等待物流通审核
			trade.setStatus(Trade.Status.TRADE_WAIT_CENTRO_AUDIT);
			tradeJpaDao.save(trade);
			List<TradeOrder> orders = trade.getOrders();
			if (CollectionUtils.isNotEmpty(orders)) {
				for (TradeOrder tradeOrder : orders) {
					// 更新商品SKU
					itemServie.updateSku(tradeOrder.getItem().getId(), tradeOrder.getSkuPropertiesName());
					tradeOrder.setTrade(trade);
					tradeOrderJpaDao.save(tradeOrder);
				}
			}
			// 创建关联关系
			mappingTrade(tid, trade.getId());
		}
		return trade;
	}
	
	private void mappingTrade(Long tid, Long tradeId) {
		if (tid != null) {
			TradeMapping mapping = new TradeMapping(tid, tradeId);
			tradeDao.createTradeMapping(mapping);
		}
	}
	
	/**
	 * 根据交易订单创建出库单
	 * @param tradeId
	 * @return
	 */
	public ShipOrder createSendShipOrderByTradeId(Long tradeId) {
		Trade trade = getTrade(tradeId);
		ShipOrder shipOrder = geneShipOrderFrom(trade);
		shipOrderService.createSendShipOrder(shipOrder);
		updateTradeStatus(tradeId, Trade.Status.TRADE_WAIT_EXPRESS_SHIP);
		return shipOrderService.getShipOrder(shipOrder.getId());
	}
	
	/**
	 * 更新订单
	 * @param tradeId
	 * @param status
	 */
	public void updateTradeStatus(Long tradeId, String status) {
		tradeDao.updateTradeStatus(tradeId, status);
		tradeDao.updateTradeMappingStatus(tradeId, status);
	}
	
	/**
	 * 根据交易订单创建出货单
	 * @param trade
	 * @return
	 */
	private ShipOrder geneShipOrderFrom(com.graby.store.entity.Trade trade) {
		ShipOrder shipOrder = new ShipOrder();
		shipOrder.setCentroId(trade.getCentro().getId());
		shipOrder.setTradeId(trade.getId());
		// 商铺用户名为发货人
		shipOrder.setOriginPersion(trade.getUser().getUsername());
		shipOrder.setOriginPhone(trade.getSellerPhone());
		
		// 收货方信息
		shipOrder.setReceiverAddress(trade.getReceiverAddress());
		shipOrder.setReceiverCity(trade.getReceiverCity());
		shipOrder.setReceiverDistrict(trade.getReceiverDistrict());
		shipOrder.setReceiverMobile(trade.getReceiverMobile());
		shipOrder.setReceiverName(trade.getReceiverName());
		shipOrder.setReceiverPhone(trade.getReceiverPhone());
		shipOrder.setReceiverState(trade.getReceiverState());
		shipOrder.setReceiverZip(trade.getReceiverZip());
		
		// 淘宝卖家买家相关信息
		shipOrder.setBuyerNick(trade.getBuyerNick());
		shipOrder.setBuyerMemo(trade.getBuyerMemo());
		shipOrder.setBuyerMessage(trade.getBuyerMessage());
		shipOrder.setSellerMemo(trade.getSellerMemo());
		shipOrder.setSellerMobile(trade.getSellerMobile());
		shipOrder.setSellerPhone(trade.getSellerPhone());
		shipOrder.setRemark(trade.getBuyerMessage());
		
		// 商铺用户为创建人
		shipOrder.setCreateDate(trade.getPayTime());
		shipOrder.setCreateUser(trade.getUser());
		// 发货明细
		for (TradeOrder tOrder : trade.getOrders()) {
			ShipOrderDetail detail = new ShipOrderDetail();
			detail.setItem(tOrder.getItem());
			detail.setNum(tOrder.getNum());
			shipOrder.getDetails().add(detail);
		}
		return shipOrder;
	}
	
	
	public Page<Trade> findUnfinishedTrades(int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int offset = pageSize;
		List<Trade> trades =  tradeDao.findUnfinishedTrades(start, offset);
		long count = tradeDao.countUnfinishedTrades();
		PageRequest pageable = new PageRequest(start, pageSize);
		Page<Trade> page = new PageImpl<Trade>(trades, pageable, count);
		return page;
		
	}
	
	/**
	 * 删除交易订单
	 * @param tradeId
	 */
	public void deleteTrade(Long tradeId) {
		tradeDao.deleteShipOrderDetail(tradeId);
		tradeDao.deleteShipOrder(tradeId);
		tradeDao.deleteTradeMapping(tradeId);
		tradeDao.deleteTradeOrder(tradeId);
		tradeDao.deleteTrade(tradeId);
	}
	

}
