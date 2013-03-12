package com.graby.store.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheMapImpl implements Cache<String, String> {

	private Map<String, String> c = new HashMap<String, String>();

	@Override
	public void put(String key, String value) {
		c.put(key, value);
	}

	@Override
	public String get(String key) {
		return c.get(key);
	}

	@Override
	public void remove(String key) {
		c.remove(key);
	}

}
