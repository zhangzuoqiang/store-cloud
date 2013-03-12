<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>待处理出库单</title>
</head>

<body>

	<br>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>创建日期</th>
		<th>商品名称</th>
		<th>出库单号</th>
		<th>处理</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders}" var="order">
			<tr>
				<td><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${order.createUser.shopName}</td>
				<td>${order.orderno}</td>
				<td><a href="${ctx}/trade/send/do/${order.id}" class="btn btn-primary">发货处理</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
