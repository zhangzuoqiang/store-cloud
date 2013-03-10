package com.graby.store.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.graby.store.entity.TradeOrder;

public interface TradeOrderJpaDao extends PagingAndSortingRepository<TradeOrder, Long>{

}
