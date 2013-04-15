package com.graby.store.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CentroDao;
import com.graby.store.entity.Centro;

@Component
@Transactional(readOnly = true)
public class CentroService {
		
	@Autowired
	private CentroDao centroDao;
	
	public List<Centro> findCentros() {
		return centroDao.findCentros();
	}
	
}
