package com.graby.store.base.remote.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.AbstractHessianInput;
import com.graby.store.base.remote.service.RemotingServiceExporter;

/**
 * @author mahuabiao
 */
public class HessianExProxyFactory extends HessianProxyFactory {

	private final Map<String, String> _cookies = new HashMap<String, String>();

	private final ThreadLocal<CurrentConnection> _current = new ThreadLocal<CurrentConnection>();

	private String _basicAuth;
	
	private boolean _zip;
	
	public void setZip(boolean zip) {
		this._zip = zip;
	}

	public URLConnection openConnection(URL url) throws IOException {
		HttpURLConnection.setFollowRedirects(true);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects(true);
		conn.setDoOutput(true);

		String urlKey = getUrlKey(url);
		_current.set(new CurrentConnection(urlKey, conn));

		String cookie = (String) _cookies.get(urlKey);
		if (cookie != null) {
			conn.setRequestProperty("Cookie", cookie);
		} else if (_basicAuth != null) {
			conn.setRequestProperty("Authorization", _basicAuth);
		}
		if (_zip) {
			conn.setRequestProperty("Accept-Encoding", "gzip");
		}
		conn.setRequestProperty(RemotingServiceExporter.REMOTE_EXPORTER, RemotingServiceExporter.HESSIAN_SERVICE_EXPORTER);
		return conn;
	}

	public AbstractHessianInput getHessianInput(InputStream is) {
		CurrentConnection current = (CurrentConnection) _current.get();
		String cookie = current.connection.getHeaderField("Set-Cookie");
		if (cookie != null) {
			int index = cookie.indexOf(";");
			if (index >= 0)
				cookie = cookie.substring(0, index);
			_cookies.put(current.urlKey, cookie);
		}
		return super.getHessianInput(is);
	}

	protected String getUrlKey(URL url) {
		return url.getAuthority();
	}

	private class CurrentConnection {
		public final String urlKey;

		public final URLConnection connection;

		public CurrentConnection(String urlKey, URLConnection connection) {
			this.urlKey = urlKey;
			this.connection = connection;
		}
	}
}