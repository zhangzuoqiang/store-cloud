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
	   
	   // 输出拣货单
	   $('#btn_pick').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="order_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());  
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何货单！');
	  		} else {
	  			var action = "${ctx}/trade/send/pick/report?ids=" + chk_value;
	  			window.open(action);
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
	
	function pickReport(type) {
	   	var chk_value =[];  
	  		$('input[name="order_select[]"]:checked').each(function(){  
	   		chk_value.push($(this).val());  
  		});  
  		if (chk_value.length==0) {
  			alert('你还没有选择任何货单！');
  		} else {
  			var action = "${ctx}/trade/send/pick/report?ids=" + chk_value + "&format=" + type;
  			window.open(action);
  		}
	}
	
	</script>	
</head>

<body>
	
	<br>
	
	<div class="row">
	  	<div class="pull-right">
	  		<a id="btn_express" href="#" class="btn btn-info">退回重打运单</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  		<a id="btn_pick_batch" href="#" class="btn btn-info">打印汇总拣货单</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  		<a id="btn_pick" href="#" class="btn btn-info">打印合并拣货单</a>&nbsp;&nbsp;&nbsp;&nbsp;
	  		<!-- 
	  	    <div class="btn-group">
	          <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">打印汇总拣货单<span class="caret"></span></button>
	          <ul class="dropdown-menu">
	            <li><a href="javascript:pickReport('pdf')">PDF</a></li>
	            <li><a href="javascript:pickReport('xls')">XLS</a></li>
	            <li><a href="javascript:pickReport('html')">HTML</a></li>
	          </ul>
	        </div>
	         -->
	  		<a id="btn_submit" href="#" class="btn btn-success">确认已拣货</a>
	  	</div>
	</div>
	
	<table id="contentTable" class="table table-striped"  >
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
