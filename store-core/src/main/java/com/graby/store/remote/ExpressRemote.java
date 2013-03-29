package com.graby.store.remote;

public interface ExpressRemote {
	
	/**
	 * 获取快递公司名称
	 * @param code 快递公司编码
	 * @return 快递公司名称
	 */
	String getExpressCompanyName(String code);

}
