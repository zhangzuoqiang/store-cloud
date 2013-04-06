package com.graby.store.web.top;

import java.io.Serializable;
import java.util.List;

import com.taobao.api.domain.TransitStepInfo;

public class ExpressTrace implements Serializable {
	
	private static final long serialVersionUID = -7210812818068787657L;
	
	private String tid;
	private String expressOrderno;
	private String companyName;
	private String status;
	private List<TransitStepInfo> traceList;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getExpressOrderno() {
		return expressOrderno;
	}
	public void setExpressOrderno(String expressOrderno) {
		this.expressOrderno = expressOrderno;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
}
