package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sc_trade_order")
public class TradeOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1598440936042592403L;

	/**
	 * 交易子订单序号
	 */
	private Long id;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	
	/*--------- 商品信息 ---------*/
	
	/**
	 * 商品数字ID(淘宝)
	 */
	private Long numIid;
	
	/**
	 * 商品标题
	 */
	private String title;

	/**
	 * 商品图片的绝对路径
	 */
	private String picPath;

	/**
	 * 购买数量。取值范围:大于零的整数
	 */
	private long num;
	
	/**
	 * 手工调整金额.格式为:1.01;单位:元;精确到小数点后两位.
	 */
	private String adjustFee;
	
	/**
	 * 订单优惠金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String discountFee;
	
	/**
	 * 应付金额（商品价格 * 商品数量 + 手工调整金额 - 订单优惠金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String totalFee;	

	/**
	 * 子订单来源,如jhs(聚划算)、taobao(淘宝)、wap(无线)
	 */
	private String orderFrom;

	/**
	 * 商品的最小库存单位Sku的id.可以通过taobao.item.sku.get获取详细的Sku信息
	 */
	private Long skuId;
	
	/**
	 * SKU的值。如：机身颜色:黑色;手机套餐:官方标配
	 */
	private String skuPropertiesName;

	/**
	 * 订单超时到期时间。格式:yyyy-MM-dd HH:mm:ss
	 */
	private Date timeoutActionTime;

	/**
	 * 主订单
	 */
	private Trade trade;
	
	/**
	 * 系统商品
	 */
	private Item item;
	
	/* -------- 不持久化 --------  */
	
	/**
	 * 库存可销售数量（不持久化, 从库存接口获取）
	 * -1 表示未关联
	 */
	private long stockNum;
	
	/**
	 * 是否退款
	 */
	private boolean hasRefund;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name="trade_id")
	public Trade getTrade() {
		return trade;
	}
	
	@ManyToOne
	@JoinColumn(name="item_id")
	public Item getItem() {
		return item;
	}
	
	/**
	 * 不持久化
	 */
	@Transient
	public long getStockNum() {
		return stockNum;
	}
	
	@Transient
	public boolean isHasRefund() {
		return hasRefund;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public String getTitle() {
		return title;
	}

	public String getPicPath() {
		return picPath;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public long getNum() {
		return num;
	}

	public Long getNumIid() {
		return numIid;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public String getSkuPropertiesName() {
		return skuPropertiesName;
	}

	public Date getTimeoutActionTime() {
		return timeoutActionTime;
	}

	public void setId(Long oid) {
		this.id = oid;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}

	public void setTimeoutActionTime(Date timeoutActionTime) {
		this.timeoutActionTime = timeoutActionTime;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getAdjustFee() {
		return adjustFee;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public void setStockNum(long stockNum) {
		this.stockNum = stockNum;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public void setHasRefund(boolean hasRefund) {
		this.hasRefund = hasRefund;
	}
}
