package com.graby.store.base.remote.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.springframework.remoting.httpinvoker.HttpInvokerClientConfiguration;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;

import com.graby.store.base.remote.service.RemotingServiceExporter;

public class HttpInvokerExRequestExecutor extends SimpleHttpInvokerRequestExecutor {

	private static Map<String, String> cookies = new HashMap<String, String>();

	private boolean zip;

	public void setZip(boolean zip) {
		this.zip = zip;
	}

	protected InputStream readResponseBody(HttpInvokerClientConfiguration config, HttpURLConnection con) throws IOException {
		InputStream is = super.readResponseBody(config, con);
		String cookie = con.getHeaderField("Set-Cookie");
		if (cookie != null) {
			int index = cookie.indexOf(";");
			if (index >= 0) {
				cookie = cookie.substring(0, index);
			}
			cookies.put(con.getURL().getAuthority(), cookie);
		}
		return zip ? new GZIPInputStream(is) : is;
	}

	protected void prepareConnection(HttpURLConnection con, int contentLength) throws IOException {
		super.prepareConnection(con, contentLength);
		con.setInstanceFollowRedirects(true);
		String urlKey = con.getURL().getAuthority();
		String cookie = (String) cookies.get(urlKey);
		if (cookie != null) {
			con.setRequestProperty("Cookie", cookie);
		}
		con.setRequestProperty(RemotingServiceExporter.REMOTE_EXPORTER, RemotingServiceExporter.HTTP_INVOKER_SERVICE_EXPORTER);
	}

	public static void clearCookie() {
		cookies.clear();
	}

}
