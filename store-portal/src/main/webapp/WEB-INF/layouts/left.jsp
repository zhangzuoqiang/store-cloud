<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<h1>商品管理</h1>
	<div class="submenu">
		<!--<a href="${ctx}/item/create">添加商品</a>-->
		<a href="${ctx}/item/add">添加商品</a>
		<a href="${ctx}/item/list">我的商品</a>
	</div>
	<h1>库存管理</h1>
	<div class="submenu">
		<a href="${ctx}/store/info">库存状态</a>
		<a href="${ctx}/store/entry/list">入库单</a>
		<!-- 
		<a href="#">出库单</a>
		 -->
	</div>
	<h1>订单管理</h1>
	<div class="submenu">
		<a href="${ctx}/trade/wait">待发货订单</a>
		<a href="${ctx}/trade/list">物流通配送追踪</a>
		<!-- 
		<a href="#">统计信息</a>
		 -->
	</div>
</div>
