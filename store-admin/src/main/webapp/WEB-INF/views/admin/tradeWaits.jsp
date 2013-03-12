<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>等待审核交易订单</title>
</head>

<body>

	<legend><small>商铺申请处理的交易订单，等待仓库审核.</small></legend>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>来源商铺</th>
		<th>建单时间</th>
		<th>订购商品</th>
		<th>物流方式</th>
		<th>是否次日达\三日达</th>
		<th>收货地址</th>
		<th>审核</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${trades}" var="trade">
			<c:if test="${trade.id != null}">
			<tr>
				<td>${trade.user.shopName}</td>
				<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
				<td>${trade.itemTitles}</td>
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
					<a href="${ctx}/trade/audit/${trade.id}" class="btn btn-primary">开始审核</a></td>
				</td>
			</tr>
			</c:if>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
