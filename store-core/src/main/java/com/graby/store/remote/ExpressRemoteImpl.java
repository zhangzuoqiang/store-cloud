package com.graby.store.remote;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.service.wms.ExpressService;


@RemotingService(serviceInterface = ExpressRemote.class, serviceUrl = "/express.call")
public class ExpressRemoteImpl implements ExpressRemote {
	
	@Autowired
	private ExpressService expressService;
	
	@Override
	public String getExpressCompanyName(String code) {
		return expressService.getExpressCompanyName(code);
	}

	@Override
	public Map<String, String> getExpressMap() {
		return expressService.getExpressMap();
	}

	@Override
	public boolean validate(String code, String orderno) {
		return expressService.validate(code, orderno);
	}
	
}
