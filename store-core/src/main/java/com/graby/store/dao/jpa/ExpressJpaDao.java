package com.graby.store.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.graby.store.entity.Express;

public interface ExpressJpaDao extends CrudRepository<Express, Long>{
	
}
