<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>物流信息追踪</title>
	
	<script  type="text/javascript">
	$(function() {
		
		// 物流信息折叠 
		$("[rel='pop']").popover({ html : true});
		 
		// 全选事件
	   	$("#checkAll").click(function() {
	   		if($(this).attr("checked") == "checked") {
	   			$("input[name='trade_select[]']").each(function() {
	            	$(this).attr("checked", true);
	        	});
	   		} else {
	   			$("input[name='trade_select[]']").each(function() {
	            	$(this).attr("checked", false);
	        	});
	   		}
		});
		
	   	// 确认出库
	   $('#btn_submit').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="trade_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());  
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何交易订单！');
	  		} else {
	  			//var action = "${ctx}/trade/notify?ids=" + chk_value;
	  			//window.location.href=action;
	  			var action = "${ctx}/trade/close?tradeIds=" + chk_value;
	  			window.location.href=action;
	  		}
	   });
		
	});
	
	</script>		
</head>

<body>
<div id="body">

	<legend><small>物流信息跟踪，已完成的订单请及时关闭。</small></legend>
	
	<div class="row">
	  	<div class="pull-right">
	  		<a id="btn_submit" href="#" class="btn btn-primary">关闭交易</a>
	  	</div>
	</div>
	
	<div class="tab-content">  
		<div class="tab-pane active" id="taobao">        
		<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th class="span1">建单时间</th>
			<th class="span1">淘宝交易号</th>
			<th class="span5">收货地址</th>
			<th class="span2">运输公司</th>
			<th class="span2">运单号</th>
			<th class="span2">物流状态</th>
			<th class="span2">跟踪信息</th>
			<th class="span1"><input type="checkbox" id="checkAll" name="checkAll"/> 全选</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${traces.content}" var="trace">
				<tr>
					<td><fmt:formatDate value="${trace.trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
	                <td>${trace.trade.tradeFrom}</td>
					<td>
					<span>${trace.trade.receiverState}${trace.trade.receiverCity}${trace.trade.receiverDistrict}
					${trace.trade.receiverAddress} ${trace.trade.receiverName}</span><br>
					</td>
					<td>${trace.expressCompany}</td>
					<td>${trace.expressOrderno}</td>
					<td>${trace.status}</td>
					<td><a class="btn btn-success" rel="pop" data-content="
								<c:forEach items="${trace.traceList}" var="step">
									${step.statusTime} ${step.statusDesc}<br>
								</c:forEach>"
								data-title="状态:${step.status}" data-trigger="hover" data-placement="left" data-width="700">物流信息</a>
					<td>
					<c:if test="${trace.status == '对方已签收'}">
	               	<input type='checkbox' id='trade_select' name='trade_select[]' value='${trace.trade.id}' />
	                </c:if> 
	                </td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<tags:pagination page="${traces}" paginationSize="15"/>
		</div>
	
	</div>
</div>
</body>
</html>
