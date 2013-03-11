<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>等待发货订单</title>
</head>

<body>

	<legend></legend>
	
	<ul id="tab" class="nav nav-tabs">
      <li class="active"><a href="#home" data-toggle="tab">淘宝订单</a></li>
      <li><a href="#profile" data-toggle="tab">手工订单</a></li>
   </ul>
          
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>建单时间</th>
		<th>金额</th>
		<th>物流方式</th>
		<th>是否次日达\三日达</th>
		<th>收货人</th>
		<th>收货地址</th>
		<th>发货处理</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${trades.content}" var="trade">
			<tr>
				<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
				<td>${trade.totalFee}</td>
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
				<c:if test="${trade.isForceWlb == true}">
					由物流宝发货				
				</c:if>
				<c:if test="${trade.buyerRate == true}">
					<span class="label label-success">物流通配送中</span>
				</c:if>						
				<c:if test="${ (trade.isForceWlb == false) && (trade.buyerRate == false)}">
					<a href="${ctx}/trade/deal/tb/${trade.tid}" class="btn btn-primary">开始处理</a></td>				
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${trades}" paginationSize="15"/>
</body>
</html>
