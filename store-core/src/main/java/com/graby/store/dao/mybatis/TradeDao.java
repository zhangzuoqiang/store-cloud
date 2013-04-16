package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;

@MyBatisRepository
public interface TradeDao {
	
	/**
	 * 获取交易
	 * @param id
	 * @return
	 */
	public Trade getTrade(Long id);
	
	/**
	 * 关联淘宝交易ID和系统交易ID
	 * @param tid
	 * @param tradeId
	 */
	public void createTradeMapping(TradeMapping tradeMapping);
	
	/**
	 * 更新订单映射表状态。
	 * @param tradeId 系统交易号
	 * @param status
	 */
	public void updateTradeMappingStatus(Long tradeId, String status);
	
	/**
	 * 查询淘宝交易ID是否已存在系统交易ID
	 * @param tid 淘宝交易ID
	 * @return 系统交易ID
	 */
	public Long getRelatedTradeId(Long tid);
	
	/**
	 * 查询淘宝交易ID关联的Mapping
	 * @param tid
	 * @return
	 */
	public TradeMapping getRelatedMapping(Long tid);
	
	/**
	 * 根据系统交易ID查询淘宝交易ID
	 * @param tradeId
	 * @return
	 */
	public Long getRelatedTid(Long tradeId);
	
	/**
	 * 查询最近50条待处理订单(等待物流通审核)
	 * @return
	 */
	public List<Trade> findWaitAuditTrades();
	
	/**
	 * 查询未完成交易订单
	 */
	public List<Trade> findUnfinishedTrades(int pageNo, int pageSize);
	public long countUnfinishedTrades();
	
	
	/**
	 * 设置订单状态
	 * @param tradeId
	 * @param status
	 */
	public void updateTradeStatus(Long tradeId, String status);
	
	/**
	 * 获取用户订单总数
	 * @param userId
	 * @param status
	 * @return
	 */
	public long getTotalResults(Long userId, String status);
	
	/**
	 * 获取用户订单列表
	 * @param userId
	 * @param status
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Trade> getTrades(Long userId, String status, long start, long offset);
	
	// ** 删除交易信息：出货单明细、出货单、交易关联、交易订单 */
	public void deleteShipOrderDetail(Long tradeId);
	public void deleteShipOrder(Long tradeId);
	public void deleteTradeMapping(Long tradeId);
	public void deleteTradeOrder(Long tradeId);
	public void deleteTrade(Long tradeId);
	
}
