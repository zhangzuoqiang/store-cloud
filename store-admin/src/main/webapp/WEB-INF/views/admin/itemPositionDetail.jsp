<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
</head>
<body>

<c:if test="${empty items}">
未找到商品
</c:if>

<c:if test="${not empty items}">
<table id="contentTable" class="table table-striped table-condensed"  >
<thead><tr>
	<th>库位</th>
	<th>商品编码</th>
	<th>商品名称</th>
	</tr></thead>
	<tbody>
		<form name="updatePositionForm" action="${ctx}/stock/position">
		<c:forEach items="${items}" var="item">
		<tr>
			<td><input type="text" class="span3" name="${item.position}" value='${item.position}'/></td>
			<td>${item.code}</td>
			<td>${item.title}</td>
		</tr>
		</c:forEach>
		</form>
	</tbody>
</table>
</c:if>
</body>
</html>