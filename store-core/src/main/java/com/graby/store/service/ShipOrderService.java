package com.graby.store.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.graby.store.base.GroupMap;
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
		ShipOrderDetail detail = new ShipOrderDetail();
		ShipOrder order = new ShipOrder();
		order.setId(orderId);
		Item item = new Item();
		item.setId(itemId);
		detail.setOrder(order);
		detail.setItem(item);
		detail.setNum(num);
		entryOrderDetailJpaDao.save(detail);
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
						InvAccountTemplate.SHOP_SEND);
			}
			shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
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
		return shipOrderDao.findSendOrderWaits(1L);
	}
	
	/**
	 * 按规则分类所有出库单(待处理)
	 * @return
	 */
	public GroupMap<String, ShipOrder> findGroupSendOrderWaits(Long centroId) {
		GroupMap<String, ShipOrder> results =new GroupMap<String,ShipOrder>();
		List<ShipOrder> orders = shipOrderDao.findSendOrderWaits(centroId);
		for (ShipOrder shipOrder : orders) {
			ksession.insert(shipOrder);
		}
		ksession.fireAllRules();
		for (ShipOrder shipOrder : orders) {
			String expressCompany = shipOrder.getExpressCompany();
			results.put(expressCompany == null ? "OTHER" : expressCompany, shipOrder);
		}
		return results;
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
	 * @param tid
	 * @return
	 */
	public ShipOrder getShipOrderByTid(Long tid) {
		return shipOrderDao.getShipOrderByTid(tid);
	}
	 
	/**
	 * 保存出库单
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
		// 等待仓库发货
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
	 * 提交出货单，仓库发货
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
		// 更新交易订单状态-等待用户签收
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
