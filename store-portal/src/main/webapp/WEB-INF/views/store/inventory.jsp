<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head></head>
<body>

<legend><small></small></legend>
<table id="contentTable" class="table table-striped table-condensed"  >
仓库：${centro}
<thead><tr>
	<th>商品名称</th>
	<th>厂家库存</th>
	<th>在途(发往仓库途中)</th>
	<th>可销库存</th>
	<th>不良品</th>
	<th>冻结(仓库发货中)</th>
	<th>已售出</th>
	</tr></thead>
	<tbody>
		<c:forEach items="${values}" var="info">
		<tr>
			<td>${info.itemName}</td>
			<td>${info.c1}</td>
			<td>${info.c2}</td>
			<td>${info.c3}</td>
			<td>${info.ca}</td>
			<td>${info.c7}</td>
			<td>${info.c8}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
	
</body>
</html>