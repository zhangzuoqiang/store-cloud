package com.graby.store.remote;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.service.ExpressService;


@RemotingService(serviceInterface = ExpressRemote.class, serviceUrl = "/express.call")
public class ExpressRemoteImpl implements ExpressRemote {
	
	@Autowired
	private ExpressService expressService;
	
	@Override
	public String getExpressCompanyName(String code) {
		return expressService.getExpressCompanyName(code);
	}
	
}
