package com.graby.test;

import java.util.List;

import com.graby.store.base.GroupMap;
import com.graby.store.entity.ShipOrder;

public class Test2 {
	
	public static void main(String[] args) {
		GroupMap<String, ShipOrder> results = new GroupMap<String, ShipOrder>();
		for (String key : results.getKeySet()) {
			List<ShipOrder> orders = results.getList(key);
		}
	}
}
