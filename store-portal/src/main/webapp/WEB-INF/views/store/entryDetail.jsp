<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<script>
		function delItem(detailId) {
			var action = "${ctx}/store/entry/ajax/item/delete/${order.id}/" + detailId;
			htmlobj=$.ajax({
				url:action,
				async:true,
				success: function(msg) {
                   $("#itemPanel").html(htmlobj.responseText);
                },
				error: function(XMLHttpRequest, textStatus, errorThrown) {
                }
			});
			
			$("#msg").html("删除成功").show(100).delay(1000).hide(400);
		}		
	</script>
</head>
<body>
	<table id="contentTable" class="table table-condensed alert">
		<thead><tr>
		<th>商品编号</th>
		<th>商品标题</th>
		<th>sku</th>
		<th>重量(克)</th>
		<th>数量</th>
		<th>删除</th></tr></thead>
		<tbody>
		<c:forEach items="${order.details}" var="detail">
			<tr>
				<td>${detail.item.code}</td>
				<td>${detail.item.title}</td>
				<td>${detail.item.sku}</td>
				<td>${detail.item.weight}</td>
				<td>${detail.num}</td>
				<td><a href="javascript:delItem(${detail.id})">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
