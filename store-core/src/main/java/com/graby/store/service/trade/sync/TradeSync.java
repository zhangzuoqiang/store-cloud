package com.graby.store.service.trade.sync;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;

@Component
public class TradeSync {

	@Value("${top.appkey}")
	private String appKey = "1021395257";
	
	@Value("${top.appSecret}")
	private String appSecret = "sandbox0475ca7f0a4a47a3d5303014e";
	
	@Value("${top.serverUrl}")
	private String serverUrl = "http://gw.api.tbsandbox.com/router/rest";

	private TaobaoClient client;

	@PostConstruct
	public void init() {
		client = new DefaultTaobaoClient(serverUrl, appKey, appSecret, "json");
	}
	
	private String sessionKey() {
		return ShiroContextUtils.getSessionKey();
	}

	/**
	 * 方案三
	 */
	public void startSync() throws Exception {
		final TopApiService topApiService = new TopApiService(client);
		// 启动主动通知监听器
		topApiService.permitUser(sessionKey());
		Configuration conf = new Configuration(appKey, appSecret, null);
		TopCometStream stream = new TopCometStreamFactory(conf).getInstance();
		stream.setConnectionListener(new ConnectionLifeCycleListenerImpl());
		stream.setMessageListener(new TopCometMessageListenerImpl(topApiService));
		stream.start();

		// 初始化三个月内订单
		final Date now = new Date();
		String start = DateUtils.formatDay(DateUtils.addMonths(now, -3));
		String end = DateUtils.formatDay(DateUtils.addDays(now, -1));
		topApiService.asyncSoldTrades(start, end, sessionKey());

		// 获取今天的增量订单
		topApiService.syncIncrementSoldTrades(DateUtils.getTodayStartTime(), now, sessionKey());
	}

}
