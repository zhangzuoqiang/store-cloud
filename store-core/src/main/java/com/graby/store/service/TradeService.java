package com.graby.store.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.TradeJpaDao;
import com.graby.store.dao.jpa.TradeOrderJpaDao;
import com.graby.store.dao.mybatis.TradeDao;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;

@Component
@Transactional(readOnly = true)
public class TradeService {

	@Autowired
	private TradeJpaDao tradeJpaDao;
	
	@Autowired
	private TradeOrderJpaDao tradeOrderJpaDao;

	@Autowired
	private TradeDao tradeDao;
	
	@Autowired
	private ShipOrderService shipOrderService;

	/**
	 * 查询淘宝交易ID是否已存在系统交易ID
	 * @param tid 淘宝交易ID
	 * @return 系统交易ID
	 */
	public Long getRelatedTradeId(Long tid) {
		return tradeDao.getRelatedTradeId(tid);
	}
	
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
	 * 校验订单是否能提交
	 * 如有错误返回错误信息，没有则返回null
	 * @param trade
	 * @return
	 */
	public String validateTrade(Trade trade) {
		StringBuffer message = new StringBuffer();
		List<TradeOrder> orders = trade.getOrders();
		for (TradeOrder tradeOrder : orders) {
			if (tradeOrder.getNum()<= 0 ) {
				message.append("无库存或未关联商品：" + tradeOrder.getTitle());
			}
		}
		return message.length() == 0 ? null : message.toString();
	}
	
	/**
	 * 订单点发货时, 创建系统订单.
	 * 
	 * @param trade
	 */
	public void createTrade(Trade trade) {
		// 保存至系统订单
		Long tradeId =getRelatedTradeId(trade.getTid());
		if (tradeId == null) {
			// 状态等待物流通审核
			trade.setStatus(Trade.Status.TRADE_WAIT_CENTRO_AUDIT);
			tradeJpaDao.save(trade);
			List<TradeOrder> orders = trade.getOrders();
			if (CollectionUtils.isNotEmpty(orders)) {
				for (TradeOrder tradeOrder : orders) {
					tradeOrder.setTrade(trade);
					tradeOrderJpaDao.save(tradeOrder);
				}	
			}
			
			// 创建关联关系
			TradeMapping mapping = new TradeMapping(trade.getTid(), trade.getId());
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
		ShipOrder shipOrder = geneShipOrder(trade);
		shipOrderService.createSendShipOrder(shipOrder);
		updateStatus(tradeId, Trade.Status.TRADE_WAIT_EXPRESS_SHIP);
		tradeDao.updateTradeMappingStatus(trade.getTid(), Trade.Status.TRADE_WAIT_EXPRESS_SHIP);
		return shipOrderService.getShipOrder(shipOrder.getId());
	}
	
	public void updateStatus(Long tradeId, String status) {
		tradeDao.updateTradeStatus(tradeId, status);
		tradeDao.updateTradeMappingStatus(tradeId, status);
	}
	
	/**
	 * 查询最近50条待处理订单(等待物流通审核)
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
		StringBuffer buf = new StringBuffer();
		buf.append("%").append(status).append("%");
		status = buf.toString();
		long start = (pageNo-1)*pageSize;
		long offset = pageSize;
		List<Trade> trades = tradeDao.getTrades(userId, status, start, offset);
		long total = tradeDao.getTotalResults(userId, status);
		PageRequest pageable = new PageRequest((int)pageNo, (int)pageSize);
		Page<Trade> page = new PageImpl<Trade>(trades, pageable, total);
		return page;
	}
	
	
	/**
	 * 获取交易订单
	 * @param id
	 * @return
	 */
	public Trade getTrade(Long id) {
		return tradeDao.getTrade(id);
		//Trade trade = tradeJpaDao.findOne(id);
//		/return trade;
	}
	
	/**
	 * 根据交易订单创建出货单
	 * @param trade
	 * @return
	 */
	private ShipOrder geneShipOrder(com.graby.store.entity.Trade trade) {
		ShipOrder shipOrder = new ShipOrder();
		shipOrder.setCentroId(1L);
		shipOrder.setTradeId(trade.getId());
		shipOrder.setReceiverAddress(trade.getReceiverAddress());
		shipOrder.setReceiverCity(trade.getReceiverCity());
		shipOrder.setReceiverDistrict(trade.getReceiverDistrict());
		shipOrder.setReceiverMobile(trade.getReceiverMobile());
		shipOrder.setReceiverName(trade.getReceiverName());
		shipOrder.setReceiverPhone(trade.getReceiverPhone());
		shipOrder.setReceiverState(trade.getReceiverState());
		shipOrder.setReceiverZip(trade.getReceiverZip());
		shipOrder.setRemark(trade.getBuyerMemo());
		shipOrder.setCreateDate(trade.getPayTime());
		shipOrder.setCreateUser(trade.getUser());
		for (TradeOrder tOrder : trade.getOrders()) {
			ShipOrderDetail detail = new ShipOrderDetail();
			detail.setItem(tOrder.getItem());
			detail.setNum(tOrder.getNum());
			detail.setSkuPropertiesName(tOrder.getSkuPropertiesName());
			shipOrder.getDetails().add(detail);
		}
		return shipOrder;
	}
	

}
