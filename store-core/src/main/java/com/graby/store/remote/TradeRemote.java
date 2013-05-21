package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;

/**
 * 交易服务
 * serviceUrl = "/trade.call"
 */
public interface TradeRemote {

	/**
	 * 查询淘宝交易ID是否已存在系统交易ID
	 * @param tid 淘宝交易ID
	 * @return 系统交易ID
	 */
	Long getRelatedTradeId(Long tid);
	
	/**
	 * 获取交易映射关系
	 * @param tid 淘宝交易ID
	 * @return
	 */
	TradeMapping getRelatedMapping(Long tid);


	/**
	 * 订单点发货时, 创建系统交易.
	 * 
	 * @param trade 系统交易
	 */
	void createTrade(Trade trade);

	/**
	 * 根据交易创建出库单
	 * @param tradeId 系统交易ID
	 * @return
	 */
	ShipOrder createSendShipOrderByTradeId(Long tradeId);
	
	/**
	 * 根据交易订单创建所有出库单
	 * @param centroId 仓库ID
	 */
	void createAllSendShipOrder(Long centroId);

	/**
	 * 查询最近200条待处理交易(等待物流通审核)
	 * @return
	 */
	List<Trade> findWaitAuditTrades();
	
	/**
	 * 查询最近200条待处理交易(等待物流通审核) 带过滤条件
	 * @return
	 */
	List<Trade> findWaitAuditTradesBy(Map<String, Object> params);

	/**
	 * 查询用户交易
	 * @param userId 用户ID
	 * @return
	 */
	Page<Trade> findUserTrades(Long userId, String status, long pageNo, long pageSize);

	/**
	 * 获取交易
	 * @param id
	 * @return
	 */
	Trade getTrade(Long id);
	
	/**
	 * 查询未关闭交易
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page<Trade> findUnfinishedTrades(int pageNo, int pageSize);
	
	/**
	 * 删除交易
	 * @param tradeId
	 */
	void deleteTrade(Long tradeId);

}