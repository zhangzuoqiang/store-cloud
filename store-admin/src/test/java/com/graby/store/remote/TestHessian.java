package com.graby.store.remote;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.caucho.hessian.client.HessianProxyFactory;
import com.graby.store.base.remote.service.RemotingServiceExporter;

public class TestHessian {
	public static void main(String[] args) throws Exception {
		String url = "http://localhost/centro.call";

		HessianExProxyFactory factory = new HessianExProxyFactory();
		CentroRemote centroRemote = (CentroRemote) factory.create(CentroRemote.class, url);
		System.out.println("Hello: " + centroRemote.findCentros());
	}
	
	static class HessianExProxyFactory extends HessianProxyFactory {
		public URLConnection openConnection(URL url) throws IOException {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty(RemotingServiceExporter.REMOTE_EXPORTER, RemotingServiceExporter.HESSIAN_SERVICE_EXPORTER);
			conn.setDoOutput(true);
			return conn;
		}
	}
}
