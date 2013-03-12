package com.graby.store.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	 * 订单点发货时, 创建系统订单,同时产生出货单.
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
		shipOrderService.saveSendShipOrder(shipOrder);
		
		// TODO 测试后恢复
		tradeDao.setTradeStatus(tradeId, Trade.Status.TRADE_WAIT_EXPRESS_SHIP);
		return shipOrder;
	}
	
	public void setStatus(Long tradeId, String status) {
		
	}
	
	/**
	 * 查询最近50条待处理订单(等待物流通审核)
	 * @return
	 */
	public List<Trade> findWaitAuditTrades() {
		return tradeDao.findWaitAuditTrades();
	}
	
	/**
	 * 获取交易订单
	 * @param id
	 * @return
	 */
	public Trade getTrade(Long id) {
		Trade trade = tradeJpaDao.findOne(id);
		return trade;
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
			shipOrder.getDetails().add(detail);
		}
		return shipOrder;
	}
	

}
