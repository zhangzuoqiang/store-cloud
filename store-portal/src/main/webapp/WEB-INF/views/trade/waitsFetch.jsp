<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>未处理订单</title>
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
		
		// 发送物流通按钮
		$('#tab').bind('click', function (e) {
	    	if(e.target.text.substring(0,3)=='可发送') {
	       		$("#submit").css("visibility", "visible");
	       	} else {
	       		$("#submit").css("visibility", "hidden");
	       	}
	    });
	    
	    // 发送事件
	   $('#submit').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="trade_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());  
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何订单！');
	  		} else {
	  			var action = "${ctx}/trade/send?tids=" + chk_value;
	  			window.location.href=action;
	  		}
	   });
		
	});
	
	</script>
</head>
<body>
	<br>
	<div class="navbar">
	  <div class="navbar-inner">
	    <div class="container">
			<ul id="tab" class="nav nav-pills">
			  <li><a>${date}</a></li>
		      <li class="active"><a href="#useable" data-toggle="tab">可发送(${fn:length(useable)})</a></li>
		      <li><a href="#failed" data-toggle="tab">库存不足(${fn:length(failed)})</a></li>
		      <li><a href="#related" data-toggle="tab">已提交(${fn:length(related)})</a></li>
		  	</ul>
		  	<div class="span4 pull-right">
	  			<a id="submit" href="#" class="btn btn-success pull-right">物流通发货</a>
	  		</div>
	    </div>
	  </div>
	</div>
	
   
    <div class="tab-content">
    	<div id="useable" class="tab-pane active" >
    	<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>建单时间</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>收货地址</th>
			<th>商品(库存)</th>
			<th><input type="checkbox" id="checkAll" name="checkAll"/> 全选</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${useable}" var="trade">
				<tr>
					<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
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

					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td>
						<div>
							<c:forEach items="${trade.orders}" var="order">
								${order.title}
								<span class="label label-success">
								${order.stockNum}
								</span> <br/>
							</c:forEach>
						</div>
					</td>
					<td>
						<input type='checkbox' id='trade_select' name='trade_select[]' value='${trade.tid}' />
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    	</div>
    	
    	<div id="failed" class="tab-pane" >
   		<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>建单时间</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th>商品</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${failed}" var="trade">
				<tr>
					<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
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
					<td>
						<c:forEach items="${trade.orders}" var="order">
							${order.title}
							<span id="err" class="label label-important"> 
							<c:if test="${order.stockNum == -1}">
								未关联 
							</c:if>
							<c:if test="${order.stockNum == 0}">
								无库存
							</c:if>
							</span>
							<br>
						</c:forEach>
					</td>			
				</tr>
			</c:forEach>
			</tbody>
		</table>
    	</div>
    	
    	<div id="related" class="tab-pane" >
   		<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>建单时间</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th>订单状态</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${related}" var="trade">
				<tr>
					<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
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
					<td>
						<c:if test="${trade.status == 'TRADE_WAIT_CENTRO_AUDIT'}">
							等待物流通审核			
						</c:if>
						<c:if test="${trade.status == 'TRADE_WAIT_EXPRESS_SHIP'}">
							物流通审核通过，快递配送中...
						</c:if>
						<c:if test="${trade.status == 'TRADE_WAIT_BUYER_RECEIVED'}">
							物流通已发货 请通知买家等待签收
						</c:if>					
					</td>		
				</tr>
			</c:forEach>
			</tbody>
		</table>    	    	
    	</div>    	    	    	
    </div>

</body>
</html>
