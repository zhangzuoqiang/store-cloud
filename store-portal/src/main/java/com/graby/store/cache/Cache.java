package com.graby.store.cache;



/**
 * 缓存接口
 * @author huabiao.mahb
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K , V> {
	
	/**
	 * put .
	 * 
	 * @param key
	 * @param value
	 */
	void put(K key, V value);
	
	/**
	 * get .
	 * 
	 * @param key
	 * @return
	 */
	V get(K key);
	
	/**
	 * remove.
	 * 
	 * @param key
	 */
	void remove(K key);
	
}
