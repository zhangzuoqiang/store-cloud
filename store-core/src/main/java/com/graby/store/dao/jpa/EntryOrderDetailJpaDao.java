package com.graby.store.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.graby.store.entity.ShipOrderDetail;

public interface EntryOrderDetailJpaDao extends PagingAndSortingRepository<ShipOrderDetail, Long> {

}
