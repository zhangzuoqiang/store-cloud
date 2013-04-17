package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.ShipOrder;
import com.graby.store.service.inventory.AccountEntryArray;
import com.taobao.api.ApiException;

/**
 * 货单服务
 * serviceUrl = "/ship.call"
 */
public interface ShipOrderRemote {

	/**
	 * 查询所有在途入库单
	 */
	public List<ShipOrder> findEntryOrderOnWay();

	/**
	 * 保存入库单
	 * 
	 * @param order
	 */
	public void saveEntryOrder(ShipOrder order);

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
	public void saveShipOrderDetail(Long orderId, Long itemId, long num);

	/**
	 * 删除发货单
	 * 
	 * @param orderId
	 */
	public void deleteShipOrder(Long orderId);

	/**
	 * 删除发货单明细
	 * 
	 * @param id
	 */
	public void deleteShipOrderDetail(Long id);

	/**
	 * 返回用户的入库单
	 * 
	 * @param userid
	 * @param status
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<ShipOrder> findEntrys(Long userid, String status, int page, int pageSize);

	/**
	 * 获取发货单
	 * 
	 * @param id
	 * @return
	 */
	public ShipOrder getShipOrder(Long id);

	/**
	 * 确认发送入库单
	 * 
	 * @param id
	 */
	public boolean sendEntryOrder(Long id);

	/**
	 * 接收发送入库单
	 * 
	 * @param id
	 * @param entrys
	 */
	public void recivedEntryOrder(Long id, List<AccountEntryArray> entrys);

	/**
	 * 查询所有出库单(未处理)
	 * 
	 * @return
	 */
	public List<ShipOrder> findSendOrderWaits();
	
	/**
	 * 分组查询所有未处理出库单
	 * @param centroId 仓库ID
	 * @return GroupMap 结构
	 *   key=快递公司编码
	 *   value=归类的出库单列表
	 */
	public List<ShipOrder> findGroupSendOrderWaits(Long centroId);
	
	/**
	 * 运单打印成功，更新出库单物流信息。
	 * @param orderMaps 已完成打印的出货单
	 * Map结构:
	 * id=出货单ID
	 * expressCompany=运输公司CODE
	 * expressOrderno=运单号
	 * 
	 */
	public void setSendOrderExpress(List<Map<String,String>> orderMaps);

	/**
	 * 根据状态查询出库单
	 * @return
	 */
	public List<ShipOrder> findSendOrderByStatus(Long centroId, String status);
	
	/**
	 * 重置货单为运单未打印状态。
	 * @param orderids
	 */
	public void reExpressShipOrder(Long[] orderIds);
	
	/**
	 * 查询出库单
	 * @param orderIds
	 * @return
	 */
	public List<ShipOrder> findSendOrders(Long[] orderIds);	
	
	/**
	 * 根据运单号或昵称查询出库单
	 * @param q
	 * @return
	 */
	public List<ShipOrder> findSendOrderByQ(String q);
	
	/**
	 * 批量提交出库单，等待用户签收.
	 * @param orderids
	 */
	public void submits(Long[] orderIds);
	
	/**
	 * 查询所有出库单(等待用户签收)
	 * 
	 * @return
	 */
	public List<ShipOrder> findSendOrderSignWaits();

	/**
	 * 根据淘宝交易号查询出货单
	 * 
	 * @param tid
	 * @return
	 */
	public ShipOrder getShipOrderByTid(Long tid);


	/**
	 * 提交出货单，仓库发货. 记录商品库存到可销售到冻结 更新出货单交易订单状态到等待用户签收
	 * 
	 * @param order
	 * @return
	 * @throws ApiException
	 */
	public ShipOrder submitSendOrder(ShipOrder order) throws ApiException;
	
	/**
	 * 出货单用户签收确认 库存记录商品冻结到已售出 更新订单状态
	 * 
	 * @param orderId
	 */
	public ShipOrder signSendOrder(Long orderId);


}