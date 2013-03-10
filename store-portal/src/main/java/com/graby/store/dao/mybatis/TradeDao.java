package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;

@MyBatisRepository
public interface TradeDao {
	
	
	/**
	 * 关联淘宝交易ID和系统交易ID
	 * @param tid
	 * @param tradeId
	 */
	public void createTradeMapping(TradeMapping tradeMapping);
	
	/**
	 * 查询淘宝交易ID是否已存在系统交易ID
	 * @param tid 淘宝交易ID
	 * @return 系统交易ID
	 */
	public Long getRelatedTradeId(Long tid);
	
	/**
	 * 查询最近50条待处理订单(等待物流通审核)
	 * @return
	 */
	public List<Trade> findWaitAuditTrades();
}
