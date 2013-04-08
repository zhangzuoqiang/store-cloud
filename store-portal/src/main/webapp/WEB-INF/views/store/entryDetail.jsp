<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<body>
	<table id="contentTable" class="table table-condensed alert">
		<thead><tr>
		<th>已添加商品</th><th>商品编号</th>
		<th>重量(克)</th><th>数量</th><th>删除</th></tr></thead>
		<tbody>
		<c:forEach items="${order.details}" var="detail">
			<tr>
				<td>${detail.item.title}</td>
				<td>${detail.item.code}</td>
				<td>${detail.item.weight}</td>
				<td>${detail.num}</td>
				<td><a href="${ctx}/store/entry/item/delete/${order.id}/${detail.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
