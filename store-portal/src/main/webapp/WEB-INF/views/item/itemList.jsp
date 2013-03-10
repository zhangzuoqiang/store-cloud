<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品管理</title>
</head>

<script>
	
	function modalReady() {
		$("a[data-toggle=modal").click(function(){
		    var target = $(this).attr('data-target');
		    var url = $(this).attr('href');
		    $(target).load(url);
		});	
	}
	
	$(document).ready(function() {
		modalReady();
	});
</script>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-condensed">
		<thead>
			<tr>
			<th>商品编码</th>
			<th>商品标题</th>
			<th>重量(克)</th>
			<th>已关联淘宝商品</th>
			<th>管理</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${items.content}" var="item">
			<tr>
				<td><a href="${ctx}/item/update/${item.id}">${item.code}</a></td>
				<td>${item.title}</td>
				<td>${item.weight}</td>
				<td>
					<div id="comment">
					<c:forEach items="${item.mapping}" var="tbitem">
						<a href="${tbitem.tbItemDetailurl}" target="_blank">${tbitem.tbItemTitle}</a> <br>
					</c:forEach>
					</div>
				</td>
				<td><a href="${ctx}/item/mapping/${item.id}" data-toggle="modal" data-target="#mappingModal" data-backdrop="false">关联淘宝商品</a></td>
				<td><a href="${ctx}/item/delete/${item.id}">删除商品</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${items}" paginationSize="15"/>

	<!-- 关联淘宝商品 -->
	<div class="modal hide fade" id="mappingModal">
	  <div class="modal-header">
	    <a class="close" data-dismiss="modal">×</a>
	    <h3>淘宝商品列表</h3>
	  </div>
	  <div class="modal-body">
	  </div>
	  <div class="modal-footer">
         <a href="#" class="btn" data-dismiss="modal">关闭</a>
	  </div>
	</div>
	
	<script src="assets/js/bootstrap-modal.js"></script>
</body>
</html>
