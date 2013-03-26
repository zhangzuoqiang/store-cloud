package com.graby.store.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GroupMap <K, V> implements  Serializable {
	
	private static final long serialVersionUID = -7467355654750347713L;

	private Map<K, List<V>> map = new LinkedHashMap<K, List<V>>();
	
	private int size;
	
	public void put(K key, V value) {
		if (key != null) {
			List<V> list = null;
			if (map.containsKey(key)) {
				list = map.get(key);
			} else {
				list = new ArrayList<V>();
				map.put(key, list);
			}
			list.add(value);
			size ++;
		}
	}

	public List<V> getList(K key) {
		return (List<V>) map.get(key);
	}

	public Set<K> getKeySet() {
		return map.keySet();
	}

	public Collection<List<V>> values() {
		return map.values();
	}
	
	public int getSize() {
		return size;
	}
	
}
