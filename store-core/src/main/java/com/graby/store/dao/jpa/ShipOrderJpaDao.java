package com.graby.store.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.User;

public interface ShipOrderJpaDao extends PagingAndSortingRepository<ShipOrder, Long>{
	
	Page<ShipOrder> findByCreateUserAndStatus(User user, String status, org.springframework.data.domain.Pageable pageRequest);
}
