package com.graby.store.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.graby.store.entity.ItemMapping;

public interface ItemMappingJpaDao  extends PagingAndSortingRepository<ItemMapping, Long>{

}
