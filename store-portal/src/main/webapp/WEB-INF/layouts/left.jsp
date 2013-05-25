<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<h1>商品管理</h1>
	<div class="submenu">
		<a href="${ctx}/item/add">添加商品</a>
		<a href="${ctx}/item/list">我的商品</a>
	</div>
	<h1>库存管理</h1>
	<div class="submenu">
		<a href="${ctx}/store/info">库存状态</a>
		<a href="${ctx}/store/entry/list">发货入库</a>
	</div>
	<h1>订单管理</h1>
	<div class="submenu">
		<!-- 
		<a href="${ctx}/trade/special">活动专场</a>
		 <a href="${ctx}/trade/test">test</a>
		 -->
		<a href="${ctx}/trade/waits">待发货订单</a>
		<a href="${ctx}/trade/refunds">退款处理</a>
		<a href="${ctx}/trade/received">仓库已接收</a>
		<a href="${ctx}/trade/notifys">通知用户签收</a>
		<a href="${ctx}/trade/traces">物流信息追踪</a>
	</div>
</div>
