package com.graby.store.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class ExpressService {
	
	@Resource
	private Map<String,String> expressMap;
	
	public String getExpressCompanyName(String code) {
		return expressMap.get(code);
	}
	
//	@Autowired
//	private ExpressJpaDao expressJpaDao;
//	
//	@Autowired
//	private ExpressDao expressDao;
//
//	public void addExpress(String code, String companyName) {
//		Express e = new Express();
//		e.setCode(code);
//		e.setCompanyName(companyName);
//		expressJpaDao.save(e);
//	}
//	
//	/**
//	 * 根据编码获取快递公司
//	 * @param code
//	 * @return
//	 */
//	public Express getExpress(String code) {
//		return expressDao.getExpress(code);
//	}
	
}
