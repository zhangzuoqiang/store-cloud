package com.graby.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.ExpressJpaDao;
import com.graby.store.dao.mybatis.ExpressDao;
import com.graby.store.entity.Express;

@Component
@Transactional(readOnly = true)
public class ExpressService {

	@Autowired
	private ExpressJpaDao expressJpaDao;
	
	@Autowired
	private ExpressDao expressDao;

	public void addExpress(String code, String companyName) {
		Express e = new Express();
		e.setCode(code);
		e.setCompanyName(companyName);
		expressJpaDao.save(e);
	}
	
	/**
	 * 根据编码获取快递公司
	 * @param code
	 * @return
	 */
	public Express getExpress(String code) {
		return expressDao.getExpress(code);
	}
	
}
