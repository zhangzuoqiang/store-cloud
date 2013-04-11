package com.graby.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;


/**
 * 交易订单
 * @author huabiao.mahb
 */
@Entity
@Table(name = "sc_trade")
public class Trade implements Serializable{
	
	private static final long serialVersionUID = -1537169007391002970L;

	/**
	 * 交易状态
	 */
	public interface Status {
		
	    /**
	     * 等待物流通审核（通过物流通发送）
	     */
	    String TRADE_WAIT_CENTRO_AUDIT = "TRADE_WAIT_CENTRO_AUDIT";
	    
	    /**
	     * 等待快递配送（物流通审核通过）
	     */
	    String TRADE_WAIT_EXPRESS_SHIP = "TRADE_WAIT_EXPRESS_SHIP";
	    
	    /**
	     * 等待买家签收（物流已发送）
	     */
	    String TRADE_WAIT_BUYER_RECEIVED = "TRADE_WAIT_BUYER_RECEIVED";	    
	    
	    /**
	     * 交易成功（买家成功收件）
	     */
	    String TRADE_FINISHED = "TRADE_FINISHED";
	    
	    /**
	     * 交易关闭， 库存解冻，商品可销量减少，交易量增加。
	     */
	    String TRADE_CLOSED = "TRADE_CLOSED";
	    
	    /**
	     * 付款以前，卖家或买家主动关闭交易
	     */
	    String TRADE_CLOSED_BY_TAOBAO = "TRADE_CLOSED_BY_TAOBAO";
	    
	}

	
	/**
	 * 序号
	 */
	private Long id;
	
	/**
	 * 仓库
	 */
	private Centro centro;
	
	/**
	 * 所属用户
	 */
	private User user;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 交易订单来源
	 */	
	private String tradeFrom;
	
	/**
	 * 如果为淘宝交易，则保存淘宝交易订单ID
	 */
	private Long tid;
	
	/* ------------ 买家信息 ------------ */
	
	/**
	 * 买家昵称
	 */
	private String buyerNick;
	
	/**
	 * 买家邮件地址
	 */
	private String buyerEmail;
	
	/**
	 * 买家支付宝账号
	 */
	private String buyerAlipayNo;
	
	/**
	 * 支付时间 也就是订单创建时间
	 */
	private Date payTime;
	
	/**
	 * 买家下单的地区
	 */
	private String buyerArea;
	
	/**
	 * 买家留言
	 */
	private String buyerMessage;
	
	/**
	 * 是否有买家留言
	 */
	private Boolean hasBuyerMessage;	
	
	
	/* ------------ 物流要求信息 ------------ */
	
	/**
	 * 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。
	 * 可选值：free(卖家包邮),post(平邮),express(快递),ems(EMS), virtual(虚拟发货)。
	 */
	private String shippingType;
	
	
	/**
	 * 买家备注（与淘宝网上订单的买家备注对应，只有买家才能查看该字段）
	 */
	private String buyerMemo;
	
	
	/**
	 * 次日达订单送达时间
	 */
	private String lgAging;

	/**
	 * 次日达，三日达等送达类型
	 */
	private String lgAgingType;
	

	/* ------------ 收货方信息 ------------ */
	
	/**
	 * 收货人的详细地址
	 */
	private String receiverAddress;

	/**
	 * 收货人的所在城市
	 */
	private String receiverCity;

	/**
	 * 收货人的所在地区
	 */
	private String receiverDistrict;

	/**
	 * 收货人的手机号码
	 */
	private String receiverMobile;

	/**
	 * 收货人的姓名
	 */
	private String receiverName;

	/**
	 * 收货人的电话号码
	 */
	private String receiverPhone;

	/**
	 * 收货人的所在省份
	 */
	private String receiverState;

	/**
	 * 收货人的邮编
	 */
	private String receiverZip;
	
	
	/* ------------ 子订单 ------------ */
	
	/**
	 * 子订单集合
	 */
	private List<TradeOrder> orders = new ArrayList<TradeOrder>();

	/**
	 * 子订单名称集合，不持久化.
	 */
	private String itemTitles;
	
	/* ------------ 其他------------ */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@OneToMany(mappedBy="trade")
	public List<TradeOrder> getOrders() {
		return orders;
	}
	
	@ManyToOne
	@JoinColumn(name="centro_id")
	public Centro getCentro() {
		return centro;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	@Index(name="idx_status")
	public String getStatus() {
		return status;
	}
	
	@Index(name="idx_tid")
	public Long getTid() {
		return tid;
	}

	@Index(name="idx_shipping_type")
	public String getShippingType() {
		return shippingType;
	}
	
	@Transient
	public String getItemTitles() {
		return itemTitles;
	}
	
	public String getBuyerNick() {
		return buyerNick;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public String getBuyerAlipayNo() {
		return buyerAlipayNo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public String getBuyerArea() {
		return buyerArea;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public String getReceiverState() {
		return receiverState;
	}

	public String getReceiverZip() {
		return receiverZip;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public void setBuyerAlipayNo(String buyerAlipayNo) {
		this.buyerAlipayNo = buyerAlipayNo;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public void setBuyerArea(String buyerArea) {
		this.buyerArea = buyerArea;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}

	public void setOrders(List<TradeOrder> orders) {
		this.orders = orders;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}
	
	public void addOrder(TradeOrder order) {
		this.orders.add(order);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLgAging() {
		return lgAging;
	}

	public String getLgAgingType() {
		return lgAgingType;
	}

	public void setLgAging(String lgAging) {
		this.lgAging = lgAging;
	}

	public void setLgAgingType(String lgAgingType) {
		this.lgAgingType = lgAgingType;
	}

	public Boolean getHasBuyerMessage() {
		return hasBuyerMessage;
	}

	public void setHasBuyerMessage(Boolean hasBuyerMessage) {
		this.hasBuyerMessage = hasBuyerMessage;
	}

	public void setItemTitles(String itemTitles) {
		this.itemTitles = itemTitles;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

}
