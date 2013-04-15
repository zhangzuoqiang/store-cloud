package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.service.inventory.InventoryService;


@RemotingService(serviceInterface = InventoryRemote.class, serviceUrl = "/inventory.call")
public class InventoryRemoteImpl implements InventoryRemote {

	@Autowired
	private InventoryService InventoryService;

	@Override
	public Long getValue(Long centroId, Long itemId, String account) {
		return InventoryService.getValue(centroId, itemId, account);
	}

	@Override
	public List<Map<String, Long>> stat(Long centroId, Long userId) {
		return InventoryService.stat(centroId, userId);
	}

}
