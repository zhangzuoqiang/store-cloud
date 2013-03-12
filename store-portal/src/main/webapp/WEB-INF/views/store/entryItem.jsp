<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品管理</title>
	<script>
	
		function addItem(itemid, numid) {
			var num_val = $('#'+numid).val();
			var actionUrl = "${ctx}/store/entry/item/add/" + ${order.id} + "/" + itemid + "/" + num_val;
			window.location.href=actionUrl;
		}
		
		$(document).on("mouseenter", ".add_opt", function(e) {
			$(this).css("background-color","#FFFFCC");
			var eid = $(this)[0].id;
			var elm =$(".a_"+eid); 
			elm.show();
	    });
	    
		$(document).on("mouseleave", ".add_opt", function(e) {
			$(this).css("background-color","#FFFFFF");
			var eid = $(this)[0].id;
			var elm =$(".a_"+eid); 
			elm.hide();
	    });	    
	    

	</script>
</head>

<body>
	<legend></legend>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table class="table alert">
		<tr>
			<td><strong>入库单单号</strong>：${order.orderno}</td>
			<td><strong>入库仓库</strong>：湘潭高新仓</td>
			<td><strong>总件数</strong>：${order.totalnum}</td>
		</tr>
		<tr>
			<td><strong>运输公司运单号</strong>：${order.expressOrderno}</td>
			<td><strong>运输公司名称</strong>： ${order.expressCompany}</td>
			<td><strong>送货详细地址</strong>：${order.receiverAddress}</td>
		</tr>		
	</table>	
	
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
	
	<div class="form-actions"  style="text-align: right;">
		<form action="${ctx}/store/entry/send/${order.id}" method="get">
			<input id="submit_btn" class="btn btn-primary" type="submit" value="发送至仓库"/>&nbsp;
			<a href="${ctx}/store/entry/list" class="btn confirm">暂不处理</a>
		</form>
	</div>
	
	<legend><small>可添加商品列表</small></legend>
	<table id="contentTable" class="table table-striped table-condensed">
		<thead>
			<tr>
			<th>商品编码</th>
			<th>商品标题</th>
			<th>重量(克)</th>
			<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${items}" var="item">
			<tr id="f_${item.id}_num" class="add_opt">
				<td>${item.code}</td>
				<td>${item.title}</td>
				<td>${item.weight}</td>
				<td  width="500" style="word-wrap: break-word; word-break : break-all;">
					<div >
						<input type="text" id="${item.id}_num" data="${item.id}" class="span4 send_num" placeholder="请输入商品数量..">
					    <a class=" a_f_${item.id}_num btn hide" href="javascript:addItem(${item.id},'${item.id}_num');">添加</a>
					</div>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
