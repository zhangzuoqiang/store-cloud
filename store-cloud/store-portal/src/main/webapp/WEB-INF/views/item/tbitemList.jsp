<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<body>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>商品标题</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${tbitems}" var="tbitem">
			<tr>
				<td>${tbitem.title}</td>
				<td><a href="${ctx}/item/relate/${item.id}/${tbitem.numIid}">添加</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>