package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Item;
import com.graby.store.service.item.ItemService;

@RemotingService(serviceInterface = ItemRemote.class, serviceUrl = "/item.call")
public class ItemRemoteImpl implements ItemRemote {

	@Autowired
	private ItemService itemService;
	
	@Override
	public Item getItem(Long id) {
		return itemService.getItem(id);
	}

	@Override
	public Page<Item> findPageUserItems(Long userId, int pageNo, int pageSize) {
		return itemService.findPageUserItems(userId, "", pageNo, pageSize);
	}

	@Override
	public List<Item> findUserItems(Long userid) {
		return itemService.findPageUserItems(userid, "", 1, Integer.MAX_VALUE).getContent();
	}

	@Override
	public Long getRelatedItemId(Long numIid, Long skuId) {
		return itemService.getRelatedItemId(numIid, skuId);
	}

	@Override
	public void position(Long itemId, String position) {
		itemService.position(itemId, position);
	}

}
