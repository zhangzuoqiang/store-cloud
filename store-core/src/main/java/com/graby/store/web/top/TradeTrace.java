package com.graby.store.web.top;

import java.io.Serializable;
import java.util.List;

import com.graby.store.entity.Trade;
import com.taobao.api.domain.TransitStepInfo;

/**
 * 物流跟踪
 * @author huabiao.mahb
 *
 */
public class TradeTrace implements Serializable {
	
	private static final long serialVersionUID = -7210812818068787657L;
	private Trade trade;
	private String expressCompany;
	private String expressOrderno;
	private String tid;
	private String status;
	private List<TransitStepInfo> traceList;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TransitStepInfo> getTraceList() {
		return traceList;
	}
	public void setTraceList(List<TransitStepInfo> traceList) {
		this.traceList = traceList;
	}
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressOrderno() {
		return expressOrderno;
	}
	public void setExpressOrderno(String expressOrderno) {
		this.expressOrderno = expressOrderno;
	}
}
