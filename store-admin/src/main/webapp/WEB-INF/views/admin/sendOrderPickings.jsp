<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>拣货单</title>
	<script  type="text/javascript">
	$(function() {
		
		// 全选事件
	   	$("#checkAll").click(function() {
	   		if($(this).attr("checked") == "checked") {
	   			$("input[name='order_select[]']").each(function() {
	            	$(this).attr("checked", true);
	        	});
	   		} else {
	   			$("input[name='order_select[]']").each(function() {
	            	$(this).attr("checked", false);
	        	});
	   		}
		});
		
	    // 重置为运单打印状态
	   $('#btn_express').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="order_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());  
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何货单！');
	  		} else {
	  			var action = "${ctx}/trade/send/express?ids=" + chk_value;
	  			window.location.href=action;
	  		}
	   });
	   		
	   	// 确认出库
	   $('#btn_submit').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="order_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());  
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何货单！');
	  		} else {
	  			var action = "${ctx}/trade/send/submits?ids=" + chk_value;
	  			window.location.href=action;
	  		}
	   });
		
	});
	
	</script>	
</head>

<body>

	<legend><small>待拣货订单列表</small></legend>
	
	<div class="row">
	  	<div class="pull-right">
	  		<a id="btn_express" href="#" class="btn btn-info">重新打印运单</a>
	  		<a id="btn_print" href="#" class="btn btn-info">打印拣货单</a>
	  		<a id="btn_submit" href="#" class="btn btn-success">确认已出库</a>
	  	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>创建日期</th>
		<th>商铺名称</th>
		<th>出库单号</th>
		<th>运输公司</th>
		<th>运单号</th>
		<th>商品</th>
		<th>状态</th>
		<th><input type="checkbox" id="checkAll" name="checkAll"/> 全选</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders}" var="order">
			<tr>
				<td><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${order.createUser.shopName}</td>
				<td>${order.orderno}</td>
				<td>${order.expressCompany}</td>
				<td>${order.expressOrderno}</td>
				<td>
					<c:forEach items="${order.details}" var="detail">
						${detail.item.code} ${detail.item.title} ${detail.num} <BR>
					</c:forEach>
				</td>
				<td>运单已打印,等待拣货.</td>
				<td><input type='checkbox' id='trade_select' name='order_select[]' value='${order.id}' /></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
