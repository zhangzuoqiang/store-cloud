package com.graby.store.admin.print;

import java.util.ArrayList;
import java.util.List;

public class Pick {
	
	private String expressCompany;
	private String expressOrderno;
	private String receivedName;
	private String receivedAddress;
	private String remark;
	private List<PickDetail> details = new ArrayList<PickDetail>();

	public void addDetail(PickDetail detail) {
		this.getDetails().add(detail);
	}
	
	public String getExpressCompany() {
		return expressCompany;
	}

	public String getExpressOrderno() {
		return expressOrderno;
	}

	public String getReceivedName() {
		return receivedName;
	}

	public String getReceivedAddress() {
		return receivedAddress;
	}

	public List<PickDetail> getDetails() {
		return details;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public void setExpressOrderno(String expressOrderno) {
		this.expressOrderno = expressOrderno;
	}

	public void setReceivedName(String receivedName) {
		this.receivedName = receivedName;
	}

	public void setReceivedAddress(String receivedAddress) {
		this.receivedAddress = receivedAddress;
	}

	public void setDetails(List<PickDetail> details) {
		this.details = details;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
