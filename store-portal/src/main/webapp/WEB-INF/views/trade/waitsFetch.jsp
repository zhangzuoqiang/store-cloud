<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>未处理订单</title>
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
	  			// 分段发送至仓库
	  			var action = "${ctx}/rest/trade/send?tids=" + chk_value;
	  			var arr = new Array();
	  			var size=200;
	  			for(var i = 1, len = chk_value.length; i<= len; i++) {
	  				arr.push(chk_value[i-1]);
	  				if (i%size == 0 ) {
	  					postTrade(arr);
	  					arr=[];
	  				} else if (i == len) {
	  					postTrade(arr);
	  				}
     			}
	  		}
	   });
	   
	   function postTrade(tids) {
		   var action = "${ctx}/rest/trade/send?tids=" + tids;
		   $.post(action, function(data){
			   $.globalMessenger().post({message:data,  showCloseButton: true});
			   $("#body").html(data);
		   });
	   }
	   
	});
	
	</script>
</head>
<body>
<div id="body">
	<div class="navbar">
	  <div class="navbar-inner">
	    <div class="container">
			<ul id="tab" class="nav nav-pills">
			  <li><a>${date}</a></li>
		      <li class="active"><a href="#useable" data-toggle="tab">可发送(${fn:length(useable)})</a></li>
		      <li><a href="#failed" data-toggle="tab">库存不足(${fn:length(failed)})</a></li>
		      <li><a href="#related" data-toggle="tab">物流通已接收(${fn:length(related)})</a></li>
		      <li><a href="#refund" data-toggle="tab">已退款(${fn:length(refund)})</a></li>
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
			<th>交易订单号/付款时间</th>
			<th>交易状态</th>
			<th>交易类型</th>
			<th>物流方式</th>
			<th>买家</th>
			<th>收货地址</th>
			<th>商品(库存)</th>
			<th><input type="checkbox" id="checkAll" name="checkAll"/> 全选</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${useable}" var="trade">
				<tr>
					<td><span class="label">${trade.tid}</span><br>
						<fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> 
					</td>				
					<td>${e:tradeStatus(trade.status)}</td>
					<td>${e:tradeType(trade.type)}</td>
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
	                <td>${trade.buyerNick}</td>
					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td>
						<div>
							<c:forEach items="${trade.orders}" var="order">
								${order.title} 
								<c:if test="${not empty order.skuPropertiesName}">
								<br>规格：${order.skuPropertiesName}
								</c:if>
								<span class="label label-success">
								${order.stockNum}
								</span> 
								<c:if test="${order.hasRefund == 'true'}">
									<span class="label label-important">已退款</span>
								</c:if>
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
			<th>交易类型</th>
			<th>交易状态</th>
			<th>付款时间</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>买家昵称</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th>商品</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${failed}" var="trade">
				<tr>
					<td>${e:tradeType(trade.type)}</td>
					<td>${e:tradeStatus(trade.status)}</td>
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
	                <td>${trade.buyerNick}</td>
					<td>${trade.receiverName}</td>
					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td>
						<c:forEach items="${trade.orders}" var="order">
							${order.title}
							<c:if test="${not empty order.skuPropertiesName}">
							<br>规格：${order.skuPropertiesName}
							</c:if>
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
			<th>交易类型</th>
			<th>交易状态</th>
			<th>付款时间</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>卖家昵称</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th>商品</th>
			<th>仓库处理状态</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${related}" var="trade">
				<tr>
					<td>${e:tradeType(trade.type)}</td>
					<td>${e:tradeStatus(trade.status)}</td>
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
	                <td>${trade.buyerNick}</td>
					<td>${trade.receiverName}</td>
					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td>
						<div>
							<c:forEach items="${trade.orders}" var="order">
								${order.title}
								<c:if test="${not empty order.skuPropertiesName}">
								<br>规格：${order.skuPropertiesName}
								</c:if>
							</c:forEach>
						</div>
					</td>
					<td>
						${e:tradeStatus(trade.tag)}
					</td>		
				</tr>
			</c:forEach>
			</tbody>
		</table>
    	</div>    	    	    	
    	
    	<div id="refund" class="tab-pane" >
     	<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>交易订单号/付款时间</th>
			<th>交易状态</th>
			<th>交易类型</th>
			<th>物流方式</th>
			<th>卖家</th>
			<th>收货地址</th>
			<th>商品</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${refund}" var="trade">
				<tr>
					<td><span class="label">${trade.tid}</span><br>
						<fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> 
					</td>				
					<td>${e:tradeStatus(trade.status)}</td>
					<td>${e:tradeType(trade.type)}</td>
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
	                <td>${trade.buyerNick}</td>
					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td>
						<div>
							<c:forEach items="${trade.orders}" var="order">
								${order.title} 
								<c:if test="${not empty order.skuPropertiesName}">
								<br>规格：${order.skuPropertiesName}
								</c:if>
								<c:if test="${order.hasRefund == 'true'}">
									<span class="label label-important">已退款</span>
								</c:if>
							</c:forEach>
						</div>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    	</div>    	
    	
    </div>
</div>
</body>
</html>
