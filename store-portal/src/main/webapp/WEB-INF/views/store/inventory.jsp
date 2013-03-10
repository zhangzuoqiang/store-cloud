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
	<th>商品</th>
	<th>商家发货</th>
	<th>在途</th>
	<th>可销库存</th>
	<th>残次</th>
	<th>机损</th>
	<th>箱损</th>
	<th>冻结</th>
	<th>已售出</th>
	</tr></thead>
	<tbody>
		<c:forEach items="${values}" var="info">
		<tr>
			<td>${info.itemName}</td>
			<td>${-info.c1}</td>
			<td>${info.c2}</td>
			<td>${info.c3}</td>
			<td>${info.c4}</td>
			<td>${info.c5}</td>
			<td>${info.c6}</td>
			<td>${info.c7}</td>
			<td>${info.c8}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
	
</body>
</html>