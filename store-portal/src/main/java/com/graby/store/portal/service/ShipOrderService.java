package com.graby.store.portal.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.EntryOrderDetailJpaDao;
import com.graby.store.dao.jpa.EntryOrderJpaDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.User;
import com.graby.store.portal.auth.AuthContextUtils;
import com.graby.store.portal.inventory.AccountTemplate;
import com.graby.store.portal.inventory.InventoryService;
import com.graby.store.portal.inventory.InventoryService.AccountEntrys;

@Component
@Transactional(readOnly = true)
public class ShipOrderService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EntryOrderJpaDao entryOrderJpaDao;

	@Autowired
	private ShipOrderDao entryOrderDao;

	@Autowired
	private EntryOrderDetailJpaDao entryOrderDetailJpaDao;

	@Autowired
	private InventoryService inventoryService;

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
	 * 保存入库单
	 * 
	 * @param order
	 */
	public void saveEntryOrder(ShipOrder order) {
		Date now = new Date();
		Long userid = AuthContextUtils.getUserid();
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
		}
		entryOrderJpaDao.save(order);
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
		entryOrderDao.deleteDetailByOrderId(orderId);
		entryOrderDao.deleteOrder(orderId);
	}

	/**
	 * 删除发货单明细
	 * 
	 * @param id
	 */
	public void deleteShipOrderDetail(Long id) {
		entryOrderDao.deleteDetail(id);
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
		return entryOrderJpaDao.findByCreateUserAndStatus(user, status, new PageRequest(page - 1, pageSize));
	}

	/**
	 * 获取发货单
	 * 
	 * @param id
	 * @return
	 */
	public ShipOrder getShipOrder(Long id) {
		return entryOrderJpaDao.findOne(id);
	}

	/**
	 * 确认发送入库单
	 * 
	 * @param id
	 */
	public void sendEntryOrder(Long id) {
		ShipOrder order = this.getShipOrder(id);
		// 库存记账
		List<ShipOrderDetail> details = order.getDetails();
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(order.getCentroId(), 
						AuthContextUtils.getUserid(), 
						detail.getItem().getId(),
						detail.getNum(), 
						AccountTemplate.SHOP_SEND);
			}
		}
		entryOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
	}

	/**
	 * 接收发送入库单
	 * 
	 * @param id
	 * @param entrys
	 *            TODO
	 */
	public void recivedEntryOrder(Long id, List<AccountEntrys> entrys) {
		// 库存记账
		if (CollectionUtils.isNotEmpty(entrys)) {
			for (AccountEntrys accountEntrys : entrys) {
				inventoryService.inputs(accountEntrys.getCentroId(), 
						accountEntrys.getUserId(), 
						accountEntrys.getItemId(),
						accountEntrys.getEntrys());
			}
		}
		entryOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_FINISH);
	}

	/**
	 * 查询所有在途入库单
	 */
	public List<ShipOrder> findEntryOrderOnWay() {
		return entryOrderDao.findEntryOrderOnWay();
	}

	/* ------------ 出库单 ------------ */

	/**
	 * 保存出库单
	 * 
	 * @param shipOrder
	 */
	public void saveSendShipOrder(ShipOrder shipOrder) {
		Date now = new Date();
		Long userid = AuthContextUtils.getUserid();
		User user = new User();
		user.setId(userid);
		if (shipOrder.getId() == null) {
			String orderno = geneOrderno(ShipOrder.TYPE_SEND);
			shipOrder.setType(ShipOrder.TYPE_SEND);
			shipOrder.setOrderno(orderno);
			shipOrder.setCreateDate(now);
			if (userid != null) {
				shipOrder.setCreateUser(user);
			}
		}
		// 等待仓库发货
		shipOrder.setStatus(ShipOrder.SendOrderStatus.WAIT_EXPRESS_RECEIVED);
		shipOrder.setCentroId(1L);
		shipOrder.setLastUpdateDate(now);
		if (userid != null) {
			shipOrder.setLastUpdateUser(user);
		}
		entryOrderJpaDao.save(shipOrder);

		List<ShipOrderDetail> items = shipOrder.getDetails();
		if (CollectionUtils.isNotEmpty(items)) {
			for (ShipOrderDetail detail : items) {
				saveShipOrderDetail(shipOrder.getId(), detail.getItem().getId(), detail.getNum());
			}
		}

	}

}
