<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>通知用户签收</title>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tooltip.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-popover.js" type="text/javascript"></script>
	<script>
		$(function () {
	       $("[rel='pop']").popover();
	    });
	</script>
</head>

<body>
<div id="body">

	<legend><small class="alert">最近7天退款单，未审核的退款订单可直接<strong>撤销</strong>，已审核未发货的订单请快速联系仓库客服人员取消发送，已发货的订单请自行联系买家签收退还。</small></legend>

	
	<div class="tab-content">  
		<div class="tab-pane active" id="taobao">        
		<table id="contentTable" class="table table-striped table-condensed">
			<thead><tr>
			<th>单号</th>
			<th>买家昵称/商品信息</th>
			<th>淘宝订单交易状态</th>
			<th>退款状态</th>
			<th>货物状态</th>
			<th>退款原因/申请时间</th>
			<th>仓库处理</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${refunds}" var="entry">
				<tr>
					<td>退款单号：${entry.refund.refundId}<br>交易单号：${entry.refund.tid}</td>
					<td>${entry.refund.buyerNick}<br>${entry.refund.title}</td>
					<td>${e:tradeStatus(entry.refund.orderStatus)}</td>
					<td>${e:refundStatus(entry.refund.status)}</td>
					<td>${e:refundGoodStatus(entry.refund.goodStatus)}</td>
					<td>
						<fmt:formatDate value="${entry.refund.created}" type="date" pattern="yyyy-MM-dd HH:mm"/><br>
						<a class="label label-info" rel="pop" data-content="${entry.refund.desc}" data-title="${entry.refund.reason}" data-placement="left">查看原因</a> 
					</td>					
					<td>
						<c:if test="${not empty entry.mapping}">
							仓库处理状态：${e:tradeStatus(entry.mapping.status)} <br>
							<c:if test="${entry.mapping.status == 'TRADE_WAIT_CENTRO_AUDIT'}">
								<a class="btn btn-primary" href="${ctx}/trade/delete/${entry.mapping.tradeId}">撤销订单</a>
							</c:if>
							<c:if test="${entry.mapping.status != 'TRADE_WAIT_CENTRO_AUDIT'}">
								<span class="label label-important">请联系仓库客服撤销发货</span>
							</c:if>
						</c:if>
						<c:if test="${ empty entry.mapping}">
							未接收
						</c:if>
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
