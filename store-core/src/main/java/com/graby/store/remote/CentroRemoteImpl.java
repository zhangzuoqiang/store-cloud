package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Centro;
import com.graby.store.service.base.CentroService;

@RemotingService(serviceInterface = CentroRemote.class, serviceUrl = "/centro.call")
public class CentroRemoteImpl implements CentroRemote {
	
	@Autowired
	public CentroService centroService;
	
	@Override
	public List<Centro> findCentros() {
		return centroService.findCentros();
	}

}
