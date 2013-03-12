<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>出库单</title>
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
    <li class="progtrckr-done">2.物流通审核</li>
    <li class="progtrckr-done">3.快递配送流程</li>
    <li class="progtrckr-todo">4.收货人验收确认</li>
    <li class="progtrckr-todo">5.完成订单交易</li>
	</ol>
	
	<div class="page-header"></div>
	
	<!-- 仓库-->
	<input type="hidden" name="centro.id" value="1">
	<!-- 当前用户 -->
	<input type="hidden" name="user.id" value="<shiro:principal property="userid"/>">
	<label></label>
	
	<legend><small>第一步：仓库拣货</small></legend>
	
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	     <p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span>拣货单</p>
	     
		    <p><strong>出库仓库：</strong>湘潭高新仓 ; &nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>出库配单号：</strong>${order.orderno} ;&nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>建单时间：</strong><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></p>
	    </div>
	</div>
	<label></label>
	
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	<thead><tr>
		<th>商铺名称</th>
		<th>商品编号</th>
		<th>商品标题</th>
		<th>商铺详细信息要求(sku)</th>
		<th>订购数量</th>		
	</thead>
	<tbody>
	<c:set var="i" value="0" />
	<c:forEach items="${order.details}" var="detail">
		<tr>
		<td>${order.createUser.shopName}</td>
		<td>${detail.item.code}</td>
		<td>${detail.item.title}</td>
		<td></td>
		<td>${detail.num}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="5"><strong>买家备注:</strong>${order.remark}</td>
	</tr>	
	<tr>
		<!-- ${ctx}/trade/check/${order.id} -->
		<td colspan="5">
			<a href="#" class="btn btn-primary offset2">打印拣货单</a>
			<a href="#" class="btn btn-primary offset1">拣货单验货</a>
		</td>
	</tr>
	</tbody>
	</table>	


	<legend><small>第二步：选择运输公司并发货</small></legend>
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	     <p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span>快递运单</p>
		    <p><strong>发货人：</strong>${order.createUser.shopName}&nbsp;&nbsp;&nbsp;&nbsp;
		    <strong>发货时间：</strong><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;&nbsp;&nbsp;
		     <strong>发货地址：</strong>仓储配送中心-湘潭高新仓</p>
	    </div>
	</div>

	<label></label>
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
	
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
	     <p class="ui-tiptext ui-tiptext-message">
		    <div><span class="span2"><strong>运输公司选择：</strong></span><select></select></div>
		    <div><span class="span2"><strong>运输公司运单号：</strong></span><input type="text"/></div>
	    </div>
	</div>	
	
	<div class="form-actions">
		<!-- ${ctx}/trade/exporder/${order.id} -->
		<div class="alert alert-info">
		<strong>快递运单审核!</strong>请审核运单与实物是否相符，无误后点击确认发货。<a href="#" class="btn btn-primary">确认发货</a> 
		</div>
	</div>
	
</body>
</html>
