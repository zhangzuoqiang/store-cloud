package com.graby.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.ExpressJpaDao;
import com.graby.store.entity.Express;

@Component
@Transactional(readOnly = true)
public class ExpressService {

	@Autowired
	private ExpressJpaDao expressJpaDao;

	public void addExpress(String code, String companyName) {
		Express e = new Express();
		e.setCode(code);
		e.setCompanyName(companyName);
		expressJpaDao.save(e);
	}
	
}
