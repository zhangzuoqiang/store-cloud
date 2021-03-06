<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>发货处理</title>
	<link href="${ctx}/static/styles/step.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/prod.css" rel="stylesheet" media="all" />
	<script type="text/javascript">
		$(document).ready(function() {
			if ($("#err").length > 0){
				$("#submit_btn").attr("disabled",true);
			}
		});
	</script>
	</script>	
</head>

<body>

	<ol class="progtrckr" data-progtrckr-steps="5">
    <li class="progtrckr-done">1.确认物流通配送</li>
    <li class="progtrckr-todo">2.物流通审核</li>
    <li class="progtrckr-todo">3.快递配送流程</li>
    <li class="progtrckr-todo">4.收货人验收确认</li>
    <li class="progtrckr-todo">5.完成订单交易</li>
	</ol>
	
	<div class="page-header"></div>
	
	<form action="${ctx}/trade/send/confirm" method="post">
	<input type="hidden" name="tid" value="${trade.tid}">
	<input type="hidden" name="receiverName" value="${trade.receiverName}">
	<input type="hidden" name="receiverMobile" value="${trade.receiverMobile}">
	<input type="hidden" name="receiverPhone" value="${trade.receiverPhone}">
	<input type="hidden" name="receiverZip" value="${trade.receiverZip}">
	<input type="hidden" name="receiverState" value="${trade.receiverState}">
	<input type="hidden" name="receiverCity" value="${trade.receiverCity}">
	<input type="hidden" name="receiverDistrict" value="${trade.receiverDistrict}">
	<input type="hidden" name="receiverAddress" value="${trade.receiverAddress}">
	<input type="hidden" name="shippingType" value="${trade.shippingType}">
	<input type="hidden" name="buyerMemo" value="${trade.buyerMemo}">
	<input type="hidden" name="payTime" value="${trade.payTime}">
	<!-- 目前只支持湘潭仓 -->
	<input type="hidden" name="centro.id" value="1">
	<!-- 当前用户 -->
	<input type="hidden" name="user.id" value="<shiro:principal property="userid"/>">
	
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message">
	<thead><tr>
		<th>买家付款时间</th>	
		<th>收货人</th>
		<th>联系方式</th>
		<th colspan=2>收货地址详细信息</th>
	</thead>
	<tbody><tr>
		<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
		<td>${trade.receiverName} </td>
		<td>
			<strong>手机</strong> ${trade.receiverMobile}<br>
			<c:if test="${trade.receiverPhone != ''}">
				<strong>电话</strong> ${trade.receiverPhone}
			</c:if>
		</td>
		<td>
			<strong>邮编：</strong> ${trade.receiverZip} <br>  
			<strong>收件详细地址：</strong> ${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} - ${trade.receiverAddress}
		</td>	
	</tr></tbody>
	</table>
	
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	<thead><tr>
		<th>订购商品</th>
		<th>付款金额</th>		
		<th>购买数量</th>
		<th>仓库 - 湘潭高新仓</th>
		
	</thead>
	<tbody>
	<c:set var="i" value="0" />
	<c:forEach items="${trade.orders}" var="order">
		<tr>
		<td>${order.title}</td>
		<td>${order.totalFee}</td>
		<td>${order.num}</td>
		<td>
			<c:if test="${order.stockNum == -1}">
				<span id="err" class="label label-important">
				未关联商品
				</span>
			</c:if>
			<c:if test="${order.stockNum == 0}">
				<span id="err" class="label label-important">
				无库存
				</span>
			</c:if>
			<c:if test="${order.stockNum > 0}">
				<input type="hidden" name="orders[${i}].num" value="${order.num}">
				<input type="hidden" name="orders[${i}].item.id" value="${order.item.id}">
				<input type="hidden" name="orders[${i}].title" value="${order.item.title}">
				<c:set var="i" value="${i+1}"/>
				<span class="label label-success">
				${order.stockNum}
				</span>  <i class="icon-ok"/>
			</c:if>				
		</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	
	<label></label>
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
            <p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span>
                物流方式：
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
                </c:if>;                                
                &nbsp; &nbsp; &nbsp; &nbsp;
                送达类型(次日达/三日达)：
                <c:if test="${trade.lgAgingType != null}">
                ${trade.lgAgingType} ${trade.lgAging}
                </c:if>
                <c:if test="${trade.lgAgingType == null}">
                 无要求
                </c:if>
            </p>
 		</div>
	</div>
	
	<label></label>
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
                <p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span>买家备注</p>
                ${trade.hasBuyerMessage? trade.buyerMemo:"无备注"}
 		</div>
	</div>

	<div class="form-actions">
		<input id="submit_btn" class="btn btn-primary" type="submit" value="确认物流通配送"/>&nbsp;	
		<input id="cancel_btn" class="btn" type="button" value="暂不处理" onclick="history.back()"/>
	</div>
	</form>
</body>
</html>
