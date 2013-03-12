<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>入库单管理</title>
	<script>
		$(document).on("click", ".confirm", function(e) {
			if (!confirm("确认删除发货单"))
       			event.preventDefault();
	    });
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<br>
	<div class="row">
		<form name="reflushForm" action="" class="form-search">
			<select name="status" onchange="javascript:document.reflushForm.submit()">
				<option value="ENTRY_WAIT_SELLER_SEND"  <c:if test="${status == 'ENTRY_WAIT_SELLER_SEND'}">selected</c:if> >待发送</option>
				<option value="ENTRY_WAIT_STORAGE_RECEIVED"   <c:if test="${status == 'ENTRY_WAIT_STORAGE_RECEIVED'}">selected</c:if>>已发送,等待仓库审核。</option>
				<option value="ENTRY_FINISH" <c:if test="${status == 'ENTRY_FINISH'}">selected</c:if>>仓库已接收</option>
			</select>
		</form>
	</div>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>入库单号</th>
		<th>运输公司运单号</th>
		<th>运输公司</th>
		<th>总件数</th>
		<th>创建日期</th>
		<th>状态</th>
		<th>操作</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders.content}" var="order">
			<tr>
				<td style="vertical-align: middle;">${order.orderno}</td>
				<td style="vertical-align: middle;">${order.expressOrderno}</td>
				<td style="vertical-align: middle;">${order.expressCompany}</td>
				<td style="vertical-align: middle;">${order.totalnum}</td>
				<td style="vertical-align: middle;"><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd"/></td>
				<td style="vertical-align: middle;">	
				<c:if test="${order.status == 'ENTRY_WAIT_SELLER_SEND'}">
					待发送 <a href="${ctx}/store/entry/send/${order.id}">发送</a>
				</c:if>
				<c:if test="${order.status == 'ENTRY_WAIT_STORAGE_RECEIVED'}">
					已发送
				</c:if>
				<c:if test="${order.status == 'ENTRY_FINISH'}">
					已接收
				</c:if>		
				</td>
				<td style="vertical-align: middle;">
					<c:if test="${order.status == 'ENTRY_WAIT_SELLER_SEND'}">
					  	<a href="${ctx}/store/entry/update/${order.id}">单据信息编辑</a><br>
					  	<a href="${ctx}/store/entry/item/${order.id}">发货商品管理</a><br>
					  	<a href="${ctx}/store/entry/delete/${order.id}" class="confirm">删除</a>
					</c:if>
					<c:if test="${order.status == 'ENTRY_WAIT_STORAGE_RECEIVED'}">
						取消发送
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${orders}" paginationSize="15"/>
	<a class="btn" href="${ctx}/store/entry/create">新建入库单</a>
</body>
</html>
