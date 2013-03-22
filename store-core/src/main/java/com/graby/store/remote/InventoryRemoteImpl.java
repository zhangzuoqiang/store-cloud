package com.graby.store.remote;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.service.InventoryService;


@RemotingService(serviceInterface = InventoryRemote.class, serviceUrl = "/inventory.call")
public class InventoryRemoteImpl implements InventoryRemote {

	@Autowired
	private InventoryService InventoryService;

	@Override
	public Long getValue(Long centroId, Long itemId, String account) {
		return InventoryService.getValue(centroId, itemId, account);
	}

}
