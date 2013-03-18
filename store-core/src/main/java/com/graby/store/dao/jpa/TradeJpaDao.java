package com.graby.store.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.graby.store.entity.Trade;

public interface TradeJpaDao extends PagingAndSortingRepository<Trade, Long> {
	
}
