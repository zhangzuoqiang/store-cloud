package com.graby.store.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.graby.store.dao.jpa.EntryOrderDetailJpaDao;
import com.graby.store.dao.jpa.ShipOrderJpaDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;

@Component
@Transactional(readOnly = true)
public class ShipOrderService {

	// 默认查询条数
	private static final int DEFAULT_FETCH_ROWS = 200;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ShipOrderJpaDao orderJpaDao;

	@Autowired
	private ShipOrderDao shipOrderDao;

	@Autowired
	private EntryOrderDetailJpaDao entryOrderDetailJpaDao;

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private StatefulKnowledgeSession ksession;	
	
	@Autowired
	private ExpressService expressService; 
	
	private String formateDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	private String getLastOrderno(Date date) {
		String dateQur = formateDate(date, "yyyyMMdd");
		String sql = "select  ifnull(max(substr(orderno,10,5)) , '00000') as no from sc_ship_order t where t.orderno like ?";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, "%" + dateQur + "%");
		Map<String, Object> result = results.iterator().next();
		return (String) result.get("no");
	}

	private String geneOrderno(String type) {
		StringBuffer number = new StringBuffer();
		number.append(type.equals(ShipOrder.TYPE_ENTRY) ? "E" : "S");
		Date today = new Date();
		number.append(formateDate(today, "yyyyMMdd"));
		String max = getLastOrderno(today);
		String no = intToString(Integer.parseInt(max) + 1);
		number.append(no);
		return number.toString();
	}

	private static String intToString(int val) {
		int len = String.valueOf(val).length();
		StringBuffer buf = new StringBuffer();
		if (len < 5) {
			for (int i = 0; i < 5 - len; i++) {
				buf.append("0");
			}
		}
		buf.append(val);
		return buf.toString();
	}

	/**
	 * 查询所有在途入库单
	 */
	public List<ShipOrder> findEntryOrderOnWay() {
		return shipOrderDao.findEntryOrderOnWay();
	}
	
	/**
	 * 保存入库单
	 * 
	 * @param order
	 */
	@Transactional(readOnly = false)
	public void saveEntryOrder(ShipOrder order) {
		Date now = new Date();
		Long userid = ShiroContextUtils.getUserid();
		User user = new User();
		user.setId(userid);
		if (order.getId() == null) {
			String orderno = geneOrderno(ShipOrder.TYPE_ENTRY);
			order.setType(ShipOrder.TYPE_ENTRY);
			order.setOrderno(orderno);
			order.setCreateDate(now);
			if (userid != null) {
				order.setCreateUser(user);
			}
		}
		// 等待商家发货
		order.setStatus(ShipOrder.EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);
		order.setCentroId(1L);
		order.setLastUpdateDate(now);
		if (userid != null) {
			order.setLastUpdateUser(user);
			order.setLastUpdateDate(now);
		}
		orderJpaDao.save(order);
	}

	/**
	 * 保存发货单明细
	 * 
	 * @param orderId
	 *            入库单号
	 * @param itemId
	 *            商品ID
	 * @param num
	 *            商品数量
	 */
	@Transactional(readOnly = false)
	public void saveShipOrderDetail(Long orderId, Long itemId, long num) {
		Long detailId = shipOrderDao.getEntryOrderDetail(orderId, itemId);
		if (detailId == null) {
			ShipOrderDetail detail = new ShipOrderDetail();
			ShipOrder order = new ShipOrder();
			order.setId(orderId);
			Item item = new Item();
			item.setId(itemId);
			detail.setOrder(order);
			detail.setItem(item);
			detail.setNum(num);
			entryOrderDetailJpaDao.save(detail);
		} else {
			shipOrderDao.increaseEntryOrderDetail(detailId, num);
		}

	}

	/**
	 * 删除发货单
	 * 
	 * @param orderId
	 */
	public void deleteShipOrder(Long orderId) {
		shipOrderDao.deleteDetailByOrderId(orderId);
		shipOrderDao.deleteOrder(orderId);
	}

	/**
	 * 删除发货单明细
	 * 
	 * @param id
	 */
	public void deleteShipOrderDetail(Long id) {
		shipOrderDao.deleteDetail(id);
	}

	/**
	 * 返回用户的入库单
	 * 
	 * @param userid
	 * @param status
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<ShipOrder> findEntrys(Long userid, String status, int page, int pageSize) {
		User user = new User();
		user.setId(userid);
		return orderJpaDao.findByCreateUserAndStatus(user, status, new PageRequest(page - 1, pageSize));
	}

	/**
	 * 获取发货单
	 * 
	 * @param id
	 * @return
	 */
	public ShipOrder getShipOrder(Long id) {
		return shipOrderDao.getShipOrder(id);
	}

	/**
	 * 确认发送入库单
	 * 
	 * @param id
	 */
	public boolean sendEntryOrder(Long id) {
		ShipOrder order = this.getShipOrder(id);
		List<ShipOrderDetail> details = order.getDetails();
		// 库存记账-商铺发送入库单
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(order.getCentroId(), 
						order.getCreateUser().getId(), 
						detail.getItem().getId(),
						detail.getNum(), 
						InvAccountTemplate.SHIP_ENTRY_SEND);
			}
			shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取消发送入库单
	 * 
	 * @param id
	 */
	public boolean cancelEntryOrder(Long id) {
		ShipOrder order = this.getShipOrder(id);
		List<ShipOrderDetail> details = order.getDetails();
		// 库存记账-商铺发送入库单
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(order.getCentroId(), 
						order.getCreateUser().getId(), 
						detail.getItem().getId(),
						detail.getNum(), 
						InvAccountTemplate.SHIP_ENTRY_CANCEL);
			}
			shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 接收发送入库单
	 * 
	 * @param id
	 * @param entrys
	 *            TODO
	 */
	public void recivedEntryOrder(Long id, List<InvAccountEntrys> entrys) {
		// 库存记账
		if (CollectionUtils.isNotEmpty(entrys)) {
			for (InvAccountEntrys accountEntrys : entrys) {
				inventoryService.inputs(accountEntrys.getCentroId(), 
						accountEntrys.getUserId(), 
						accountEntrys.getItemId(),
						accountEntrys.getEntrys());
			}
		}
		shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_FINISH);
	}

	/* ------------ 出库单 ------------ */

	/**
	 * 查询所有出库单(待处理)
	 * @return
	 */
	public List<ShipOrder> findSendOrderWaits() {
		return shipOrderDao.findSendOrderWaits(1L, DEFAULT_FETCH_ROWS);
	}
	

	/**
	 * 按规则分类所有未处理出库单
	 * 
	 * 先查询仓库的所有待处理出库单，按规则将运输公司和运输公司编码填充到出库单。
	 * @param centroId 仓库ID
	 * @return 出库单列表
	 */
	public List<ShipOrder> findGroupSendOrderWaits(Long centroId) {
		List<ShipOrder> orders = shipOrderDao.findSendOrderWaits(centroId, DEFAULT_FETCH_ROWS);
//		for (ShipOrder shipOrder : orders) {
//			ksession.insert(shipOrder);
//		}
//		ksession.fireAllRules();
		String companyCode;
		String companyName;
		List<ShipOrder> results = new ArrayList<ShipOrder>();
		for (ShipOrder shipOrder : orders) {
			shipOrder.setItems(itemTitles(shipOrder));
			companyCode = shipOrder.getExpressCompany();
			companyName = companyCode == null ? "未分类" : expressService.getExpressCompanyName(companyCode);
			shipOrder.setExpressCompanyName(companyName);
			results.add(shipOrder);
		}
		return results;
	}
	
	// 商品列表
	private String itemTitles(ShipOrder order) {
		StringBuffer content = new StringBuffer();
		for (Iterator<ShipOrderDetail> iterator = order.getDetails().iterator(); iterator.hasNext();) {
			ShipOrderDetail detail =  iterator.next();
			content.append(detail.getItemTitle() + " ").append(detail.getNum() + "件");
			content.append(", ");
		}
		content.append(order.getRemark());
		return content.toString();
	}
	
	/**
	 * 查询所有出库单(等待用户签收)
	 * @return
	 */
	public List<ShipOrder> findSendOrderSignWaits() {
		return shipOrderDao.findSendOrderSignWaits();
	}
	
	/**
	 * 根据淘宝交易号查询出货单
	 * @param tradeId
	 * @return
	 */
	public ShipOrder getSendShipOrderByTradeId(Long tradeId) {
		return shipOrderDao.getSendShipOrderByTradeId(tradeId);
	}
	 
	/**
	 * 创建出库单
	 * 
	 * @param shipOrder
	 */
	public void createSendShipOrder(ShipOrder shipOrder) {
		Date now = new Date();
		if (shipOrder.getId() == null) {
			String orderno = geneOrderno(ShipOrder.TYPE_SEND);
			shipOrder.setType(ShipOrder.TYPE_SEND);
			shipOrder.setOrderno(orderno);
			shipOrder.setCreateDate(now);
		}
		// 等待物流接收
		shipOrder.setStatus(ShipOrder.SendOrderStatus.WAIT_EXPRESS_RECEIVED);
		shipOrder.setLastUpdateDate(now);
		orderJpaDao.save(shipOrder);

		List<ShipOrderDetail> items = shipOrder.getDetails();
		if (CollectionUtils.isNotEmpty(items)) {
			for (ShipOrderDetail detail : items) {
				saveShipOrderDetail(shipOrder.getId(), detail.getItem().getId(), detail.getNum());
			}
		}
	}
	
	/**
	 * 运单打印成功，注册运单信息。
	 * 
	 * @param Map printedOrders 已完成打印的出货单
	 * Map结构:
	 * id=出货单ID
	 * expressCompany=运输公司CODE
	 * expressOrderno=运单号
	 * 
	 */
	public void setSendOrderExpress(List<Map<String,String>> orderMaps) {
		if (CollectionUtils.isNotEmpty(orderMaps)) {
			for (Map<String, String> map : orderMaps) {
				Assert.notNull(map.get("expressCompany"), "物流公司不能为空");
				Assert.notNull(map.get("expressOrderno"), "运单号不能为空");
				shipOrderDao.setSendOrderExpress(map);
			}
		}
	}
	
	/**
	 * 查询所有未拣货出库单
	 * @param centroId
	 * @return
	 */
	public List<ShipOrder> findSendOrderPickings(Long centroId) {
		return shipOrderDao.findSendOrderPickings(centroId, DEFAULT_FETCH_ROWS);
	}	
	
	/**
	 * 重置货单为运单未打印状态。
	 * @param orderids
	 */
	public void reExpressShipOrder(Long[] orderids) {
		if (orderids == null || orderids.length == 0) {
			return;
		}
		for (Long orderId : orderids) {
			shipOrderDao.setOrderStatus(orderId, ShipOrder.SendOrderStatus.WAIT_EXPRESS_RECEIVED);
		}
	}
	
	/**
	 * 批量提交出库单，等待用户签收.
	 * @param orderids
	 */
	public void submits(Long[] orderids) {
		if (orderids == null || orderids.length == 0) {
			return;
		}
		for (Long orderId : orderids) {
			shipOrderDao.setOrderStatus(orderId, ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED);
			shipOrderDao.setTradeStatus(orderId, Trade.Status.TRADE_WAIT_BUYER_RECEIVED);
		}
	}
	
	/**
	 * 提交出货单，仓库发货（单条）
	 * @param order
	 * @return
	 * @throws ApiException 
	 */
	public ShipOrder submitSendOrder(ShipOrder order) throws ApiException {
		
		// 更新基本信息（运单号、运输公司）
		ShipOrder sendOrderEntity = getShipOrder(order.getId());
		sendOrderEntity.setExpressCompany(order.getExpressCompany());
		sendOrderEntity.setExpressOrderno(order.getExpressOrderno());
		sendOrderEntity.setLastUpdateDate(new Date());
		sendOrderEntity.setLastUpdateUser(order.getLastUpdateUser());
		
		// 库存记账-仓库发货
		List<ShipOrderDetail> details = sendOrderEntity.getDetails();
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(sendOrderEntity.getCentroId(), 
						sendOrderEntity.getCreateUser().getId(),
						detail.getItem().getId(),
						detail.getNum(), 
						InvAccountTemplate.STORAGE_SEND);
			}
		}
		// 更新出货单状态-等待用户签收
		sendOrderEntity.setStatus(ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED);
		updateShipOrder(sendOrderEntity);
		// 更新交易订单状态-等待用户签收 TODO
		tradeService.updateTradeStatus(sendOrderEntity.getTradeId(), Trade.Status.TRADE_WAIT_BUYER_RECEIVED);
		return sendOrderEntity;
	}
	
	/**
	 * 出货单用户签收确认
	 * @param orderId
	 */
	public ShipOrder signSendOrder(Long orderId) {
		ShipOrder order = getShipOrder(orderId);
		order.setStatus(ShipOrder.SendOrderStatus.SEND_FINISH);
		updateShipOrder(order);
		// TODO
		tradeService.updateTradeStatus(order.getTradeId(), Trade.Status.TRADE_FINISHED);
		List<ShipOrderDetail> details = order.getDetails();
		// 库存记账-买家签收
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(order.getCentroId(), 
						order.getCreateUser().getId(),
						detail.getItem().getId(),
						detail.getNum(), 
						InvAccountTemplate.BUYER_RECEIVED);
			}
		}
		return order;
	}
	
	@Transactional(readOnly = false)
	public void updateShipOrder(ShipOrder order) {
		orderJpaDao.save(order);
	}

}
