package com.graby.store.remote;

import java.util.List;

import com.graby.store.entity.Centro;

public interface CentroRemote {
	
	/**
	 * 返回所有仓库
	 * @return
	 */
	List<Centro> findCentros();
	
}
