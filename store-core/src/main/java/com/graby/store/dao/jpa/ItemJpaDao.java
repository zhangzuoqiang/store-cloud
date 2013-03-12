package com.graby.store.dao.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.graby.store.entity.Item;

public interface ItemJpaDao extends PagingAndSortingRepository<Item, Long> {
	
	Page<Item> findByUserid(Long userid, Pageable pageRequest);
	
	List<Item> findByUserid(Long userid);
	
}
