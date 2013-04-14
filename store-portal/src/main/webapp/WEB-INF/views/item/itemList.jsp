<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品管理</title>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-confirm.js" type="text/javascript"></script>
	<script>
	
		//$(function () {
	    //   $("[rel='tooltip']").tooltip();
	    //});
	    	
		$(document).on("mouseenter", ".tb_opt", function(e) {
			$(this).css("background-color","#FFFFCC");
			var eid = $(this)[0].id;
			var elm =$(".r_"+eid); 
			elm.show();
	    });
	    
		$(document).on("mouseleave", ".tb_opt", function(e) {
			$(this).css("background-color","");
			var eid = $(this)[0].id;
			var elm =$(".r_"+eid); 
			elm.hide();
	    });
	    
		$(document).ready(function() {
			$('.confirm').confirm({
				'title' : '删除商品',
				'message' : '确认删除该商品 ？',
			});
		});	    
	</script>
	
</head>
	<script>
		$(document).ready(function() {
			// 打开淘宝商品列表关联
			$("a[data-toggle=modal]").click(function(){
			    var target = $(this).attr('data-target');
			    var url = $(this).attr('href');
			    $(target).load(url);
			});	
			
			$('#confirm').confirm({
				'title' : '删除',
				'message' : '确认删除该商品',
			});			
		});
	</script>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<form class="form-search" action="${ctx}/item/list">
		<input id="q" name="q" type="text" class="span3" placeholder="根据标题查询商品..."/>
		<button class="btn btn-primary">查询</button>
	</form>
	
	<table id="contentTable" class="table table-striped ">
		<thead>
			<tr>
			<th>商品编码</th>
			<th>商品标题</th>
			<th>属性(sku)</th>
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
				<td>${item.sku}</td>
				<td>${item.weight}</td>
				<td width="300" style="word-wrap: break-word; word-break : break-all;">
					<c:forEach items="${item.mapping}" var="tbitem">
					<div id="tb_link_${tbitem.numIid}_${tbitem.skuId}" class="tb_opt">
		          		<a id="tb_title" href="${tbitem.detailUrl}" target="_blank">
		          			${tbitem.title} ${tbitem.skuTitle}
		          		</a>
						<a href="${ctx}/item/unrelate/${item.id}/${tbitem.numIid}/${tbitem.skuId == null ? "0" : tbitem.skuId}?page=${page}" 
							class="r_tb_link_${tbitem.numIid}_${tbitem.skuId} hide"><i class="icon-remove"></i></a>
						<br>			          		
				</div>
					</c:forEach>
				</td>
				<td><a href="${ctx}/item/mapping/${item.id}?page=${page}" data-toggle="modal" data-target="#mappingModal" data-backdrop="false">关联淘宝商品</a></td>
				<td><a href="${ctx}/item/delete/${item.id}?page=${page}" class="confirm">删除商品</a>
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
	    <h3>淘宝商品关联</h3>
	  </div>
	  <div class="modal-body" >
	  </div>
	  <div class="modal-footer">
         <a href="#" class="btn" data-dismiss="modal">关闭</a>
	  </div>
	</div>
	
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tooltip.js" type="text/javascript"></script>
</body>
</html>
