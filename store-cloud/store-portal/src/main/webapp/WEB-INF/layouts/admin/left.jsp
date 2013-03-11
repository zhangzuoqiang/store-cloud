<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<h1>库存管理</h1>
	<div class="submenu">
		<a href="${ctx}/admin/entry">待处理入库单</a>
	</div>
	<h1>订单管理</h1>
	<div class="submenu">
		<a href="${ctx}/admin/trade/waits">待处理交易订单</a>
		<a href="${ctx}/admin/trade/send/waits">待处理出库单</a>
	</div>
</div>
