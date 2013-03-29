package com.graby.store.dao.mybatis;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Express;

@MyBatisRepository
public interface ExpressDao {
	
	Express getExpress(String code);
	
}
