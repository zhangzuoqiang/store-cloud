<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>出库单签收</title>
	
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.ext.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-confirm.js" type="text/javascript"></script>
	
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/step.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/prod.css" rel="stylesheet" media="all" />	
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#submit_sign').confirm({
				'title' : '确认用户签收',
				'message' : '确认买家已成功签收物品 ！',
			});
		});
	</script>
</head>

<body>

	<ol class="progtrckr" data-progtrckr-steps="5">
    <li class="progtrckr-done">1.商家确认物流通配送</li>
    <li class="progtrckr-done">2.物流通审核</li>
    <li class="progtrckr-done">3.快递配送流程</li>
    <li class="progtrckr-done">4.收货人验收确认</li>
    <li class="progtrckr-todo">5.完成订单交易</li>
	</ol>
	
	<div class="page-header"></div>
	
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	     <p class="ui-tiptext ui-tiptext-message "><span class="ui-tiptext-icon"></span> <span class="label label-info">出库单</span></p>
		 <p><strong>出库仓库：</strong>湘潭高新仓 ; &nbsp;&nbsp;&nbsp;&nbsp;
		 <strong>出库单号：</strong>${order.orderno} ;&nbsp;&nbsp;&nbsp;&nbsp;
		 <strong>建单时间：</strong><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></p>
	    </div>
	</div>
	<label></label>
	
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	<thead><tr>
		<th>商铺名称</th>
		<th>商品编号（条形码）</th>
		<th>商品标题</th>
		<th>订购数量</th>		
	</thead>
	<tbody>
	<c:set var="i" value="0" />
	<c:forEach items="${order.details}" var="detail">
		<tr>
		<td>${order.createUser.shopName}</td>
		<td>${detail.item.code}</td>
		<td>${detail.item.title}</td>
		<td>${detail.num}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="5"><strong>淘宝交易号:</strong>${order.remark}</td>
	</tr>	
	</tbody>
	</table>	

	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	     	<p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span>
	     		<span class="label label-info">快递运单</span>
	     	</p>
			<strong>发货人：</strong>${order.createUser.shopName}&nbsp;&nbsp;&nbsp;&nbsp;
			<strong>发货时间：</strong><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>
	    </div>
	</div>

	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message">
	<thead><tr>
		<th>收货人</th>
		<th>联系方式</th>
		<th colspan=2>收货地址详细信息</th>
	</thead>
	<tbody><tr>
		<td>${order.receiverName} </td>
		<td>
			<strong>手机</strong> ${order.receiverMobile}<br>
			<c:if test="${order.receiverPhone != ''}">
				<strong>电话</strong> ${order.receiverPhone}
			</c:if>
		</td>
		<td>
			<strong>邮编：</strong> ${order.receiverZip} <br>  
			<strong>收件详细地址：</strong> ${order.receiverState} ${order.receiverCity} ${order.receiverDistrict} - ${order.receiverAddress}
		</td></tr>
	</tbody>
	</table>
	<div class="ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	      <strong>运输公司：</strong>${order.expressCompany}  &nbsp;&nbsp;&nbsp;&nbsp; <strong>运单号：</strong>${order.expressOrderno} 
	    </div>
	</div>	
	
	<form action="${ctx}/trade/sign/submit" method="post" >
		<input type="hidden" name="lastUpdateUser.id" value="<shiro:principal property="userid"/>">
		<input type="hidden" name="id" value="${order.id}">
		<div class="form-actions">
			<a id="submit_sign" class="btn btn-primary" href="${ctx}/trade/sign/submit/${order.id}">买家签收确认</a>
			<input id="cancel_btn" class="btn" type="button" value="暂不处理" onclick="history.back()"/>
		</div>
	</form>
</body>
</html>
