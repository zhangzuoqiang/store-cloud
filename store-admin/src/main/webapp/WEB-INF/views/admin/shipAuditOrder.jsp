<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<link href="${ctx}/static/styles/prod.css" rel="stylesheet" media="all" />
	<link href="${ctx}/static/styles/pure.css" rel="stylesheet" media="all" />
	<script type="text/javascript">
		function submits(id) {
			var action = "${ctx}/trade/send/submits?action=ship/ajax/done&ids=" + id;
			htmlobj=$.ajax({
				url:action,
				async:true,
				type:"post",
				success: function(msg) {
	                 $("#content").html(htmlobj.responseText);
	            },
					error: function(XMLHttpRequest, textStatus, errorThrown) {
	            }
			});
		}
	</script>
</head>

<body>

<c:if test="${empty entrys}">
	未找到交易订单
</c:if>

<c:if test="${not empty entrys}">
<div id="content">
<c:forEach items="${entrys}" var="entry">
	<legend><small>订单信息</small></legend>
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
        	<p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span><strong>交易信息</strong></p>
        	<p>
	        	<strong>买家昵称：</strong>${entry.trade.buyerNick}&nbsp;&nbsp;&nbsp;&nbsp;
	        	<strong>付款时间：</strong><fmt:formatDate value="${entry.trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;&nbsp;&nbsp;
	        	<strong>来自</strong>
	        	<c:if test="${entry.trade.combine}">
	        		多个淘宝订单合并
	        	</c:if>
	        	<c:if test="${!entry.trade.combine}">
	        		淘宝订单
	        	</c:if>
	        	[${entry.trade.tradeFrom}]&nbsp;&nbsp;&nbsp;&nbsp;
	        	<strong>交易状态：</strong> <span class="label label-success">${e:tradeStatus(entry.trade.status)}</span>
        	</p>
        	买家备注：${entry.trade.sellerMemo} &nbsp;&nbsp;
			卖家备注：${entry.trade.buyerMemo}<br>
			买家留言：${entry.trade.buyerMessage} 
 		</div>
	</div>
	<br>
	<!-- 收货人信息 -->
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message">
	<thead><tr>
		<th>收货人</th>
		<th>联系方式</th>
		<th>收货地址详细信息</th>
	</thead>
	<tbody><tr>
		<td>${entry.trade.receiverName}</td>
		<td>
			<strong>手机</strong> ${entry.trade.receiverMobile}<br>
			<c:if test="${entry.trade.receiverPhone != ''}">
				<strong>电话</strong> ${entry.trade.receiverPhone}
			</c:if>
		</td>
		<td>
			<strong>邮编：</strong> ${entry.trade.receiverZip} <br>  
			<strong>收件详细地址：</strong> ${entry.trade.receiverState} ${entry.trade.receiverCity} ${entry.trade.receiverDistrict} - ${entry.trade.receiverAddress}
		</td>	
		<td>
		</td>
	</tr></tbody>
	</table>
	
	<!-- 订购商品信息 -->
	<table class="table  ui-tiptext-container-message" >
	<thead><tr>
		<th>订购商品</th>
		<th>规格</th>
		<th>付款金额</th>		
		<th>购买数量</th>
	</thead>
	<tbody>
	<c:set var="i" value="0" />
	<c:forEach items="${entry.trade.orders}" var="order">
		<input type="hidden" value="${order.numIid}">
		<tr>
		<td>${order.title}</td>
		<td>${order.skuPropertiesName}</td>
		<td>${order.totalFee}</td>
		<td>${order.num}</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>	
	
	<!-- 快递信息 -->
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	     <p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span><strong>物流信息</strong></p>
		    <p><strong>出库仓库：</strong>湘潭高新仓 ; &nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>出库单号：</strong>${entry.order.orderno} ;&nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>建单时间：</strong><fmt:formatDate value="${entry.order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></p>
		    <p>
		    <strong>运输公司：</strong>${entry.order.expressCompany};&nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>运单号：</strong>${entry.order.expressOrderno};&nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>状态：</strong><span class="label label-success">${e:shipStatus(entry.order.status)}</span>
		    </p>
	    </div>
	</div>
	
	<c:if test="${entry.order.status == 'WAIT_EXPRESS_PICKING'}">
	<div class="form-actions">
		<a class="btn btn-primary" href="javascript:submits(${entry.order.id})">确认出库</a>
	</div>
	</c:if>
	
	<c:if test="${entry.order.status != 'WAIT_EXPRESS_PICKING'}">
		<h3>该订单已发货</h3>
	</c:if>
			
</c:forEach>
</div>
</c:if>
</body>
</html>
