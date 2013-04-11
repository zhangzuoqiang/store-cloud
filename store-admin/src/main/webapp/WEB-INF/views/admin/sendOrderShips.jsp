<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>快递运单单</title>
</head>

<body>

	<legend><small>待打印运单列表，请使用打单系统操作。</small></legend>
	
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>创建日期</th>
		<th>商铺名称</th>
		<th>商品</th>
		<th>目的地</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders}" var="order">
			<tr>
				<td><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${order.createUser.shopName}</td>
				<td>
					<c:forEach items="${order.details}" var="detail">
						${detail.item.code} ${detail.item.title} ${detail.num} <BR>
					</c:forEach>
				</td>
				<td>${order.receiverState} ${order.receiverCity} ${order.receiverDistrict} <br>
				 	${order.receiverAddress}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
