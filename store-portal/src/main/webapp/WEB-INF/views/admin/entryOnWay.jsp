<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>在途入库单</title>
</head>

<body>
	<legend><small>在途入库单列表</small></legend>
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>来源商铺</th>
		<th>入库单单号</th>
		<th>运输公司运单号</th>
		<th>运输公司</th>
		<th>总件数</th>
		<th>预计到达日期</th>
		<th>处理</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${entryOrders}" var="order">
			<tr>
				<td style="vertical-align: middle;">${order.createUser.shopName}</td>
				<td style="vertical-align: middle;">${order.orderno}</td>
				<td style="vertical-align: middle;">${order.expressOrderno}</td>
				<td style="vertical-align: middle;">${order.expressCompany}</td>
				<td style="vertical-align: middle;">${order.totalnum}</td>
				<td style="vertical-align: middle;"><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd"/></td>
				<td style="vertical-align: middle;">	
					<a href="${ctx}/admin/entry/operate/${order.id}" class="btn btn-primary">处理</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
