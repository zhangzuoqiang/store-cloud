package com.graby.store.remote;

import java.util.List;

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
	 * 根据系统交易ID查询淘宝交易ID
	 * @param tid 系统交易ID
	 * @return
	 */
	Long getRelatedTid(Long tradeId);

	/**
	 * 校验订单是否能提交
	 * 如有错误返回错误信息，没有则返回null
	 * @param trade 系统交易
	 * @return
	 */
	String validateTrade(Trade trade);

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
	 * 更新交易状态
	 * @param tradeId
	 * @param status
	 */
	void updateTradeStatus(Long tradeId, String status);

	/**
	 * 查询最近50条待处理交易(等待物流通审核)
	 * @return
	 */
	List<Trade> findWaitAuditTrades();

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

}