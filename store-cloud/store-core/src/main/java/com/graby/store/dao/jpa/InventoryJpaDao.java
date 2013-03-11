package com.graby.store.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.graby.store.entity.ItemInventory;


public interface InventoryJpaDao extends CrudRepository<ItemInventory, Long>{
	
}
