<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<h1>库存管理</h1>
	<div class="submenu">
		<a href="${ctx}/entry">待处理入库单</a>
	</div>
	<h1>订单管理</h1>
	<div class="submenu">
		<a href="${ctx}/trade/waits">待审核交易订单</a>
		<a href="${ctx}/trade/send/gwaits">未处理出库单</a>
		<a href="${ctx}/trade/send/waits">待出库交易定单</a>
		<a href="${ctx}/trade/sign/waits">等待用户签收</a>
	</div>
</div>
