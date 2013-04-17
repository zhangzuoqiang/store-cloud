<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<h1>库存管理</h1>
	<div class="submenu">
		<a href="${ctx}/stock">库存状态</a>
		<a href="${ctx}/entry">待处理入库单</a>
		<!-- 
		<a href="${ctx}/stock/position">库位管理</a>
		 -->
	</div>
	<h1>订单管理</h1>
	<div class="submenu">
		<a href="${ctx}/trade/waits">交易订单审核</a>
		<a href="${ctx}/trade/send/waits">运单号打印</a>
		<a href="${ctx}/trade/send/pickings">批量拣货处理</a>
		<a href="${ctx}/trade/ship/audit">运单出库审核</a>
		<a href="${ctx}/trade/sign/waits">用户签收处理</a>
		<!-- 
		<a href="${ctx}/trade/unfinish">未完成交易</a>
		 -->
	</div>
</div>
