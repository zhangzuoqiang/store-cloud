<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>通知用户签收</title>
	<link href="${ctx}/static/messenger/messenger.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/messenger/messenger-theme-future.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/messenger/messenger.min.js" type="text/javascript"></script>
	<script  type="text/javascript">
	$(function() {
		
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
	  			var action = "${ctx}/rest/trade/notify?tradeIds=" + chk_value;
	  			$.post(action, function(data){
	  				$.globalMessenger().post({message:data,  showCloseButton: true});
	  				$("#body").html(data);
	  			});	  			
	  		}
	   });
		
	});
	
	</script>		
</head>

<body>
<div id="body">

	<legend><small>物流通已发货，等待用户签收。</small></legend>
	
	<div class="row">
	  	<div class="pull-right">
	  		<a id="btn_submit" href="#" class="btn btn-success">通知用户签收</a>
	  	</div>
	</div>
	
	<div class="tab-content">  
		<div class="tab-pane active" id="taobao">        
		<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>建单时间</th>
			<th>状态</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th><input type="checkbox" id="checkAll" name="checkAll"/> 全选</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${trades}" var="trade">
				<tr>
					<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
					<td>${e:tradeStatus(trade.status)}</td>
					<td>
	                <c:if test="${trade.shippingType == 'free'}">
	                卖家包邮
	                </c:if>
	                <c:if test="${trade.shippingType == 'post'}">
	                平邮
	                </c:if>  
	                <c:if test="${trade.shippingType == 'express'}">
	                快递
	                </c:if> 
	                <c:if test="${trade.shippingType == 'ems'}">
	                EMS
	                </c:if>  
	                <c:if test="${trade.shippingType == 'virtual'}">
	                虚拟发货
	                </c:if> 
	                </td>
	                <td>
	                	<c:if test="${trade.lgAgingType != null}">
	                	${trade.lgAgingType} ${trade.lgAging}
	                	</c:if>
	                	<c:if test="${trade.lgAgingType == null}">
	                	 无要求
	                	</c:if>
	                </td>
	                
					<td>${trade.receiverName}</td>
					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td><input type='checkbox' id='trade_select' name='trade_select[]' value='${trade.id}' /></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
	
	</div>
</div>
</body>
</html>
