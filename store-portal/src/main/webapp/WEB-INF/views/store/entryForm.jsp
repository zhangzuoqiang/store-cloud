<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>入库单</title>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.ext.js" type="text/javascript"></script>
	<script src="http://github-proxy.kodono.info/?q=https://raw.github.com/Aymkdn/Datepicker-for-Bootstrap/master/bootstrap-datepicker.js"></script>
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
	

	<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$('#fetch_date').datepicker();
			$("#origin_persion").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/store/entry/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${order.id}"/>
		<fieldset>
			<legend><small>入库单</small></legend>
			<div class="control-group">
				<label for="origin_company" class="control-label">商家名称</label>
				<div class="controls">
					<shiro:user><shiro:principal property="shopname"/></shiro:user>
				</div>
			</div>
			<div class="control-group">
				<label for="origin_persion" class="control-label">商家联系人</label>
				<div class="controls">
					<input id="origin_persion" name="originPersion" type="text" class="input-large required" minlength="2" value="${order.originPersion}"/>
					<span class="help-inline">请输入商家联系人</span>
				</div>
			</div>
			<div class="control-group">
				<label for="item_title" class="control-label">商家联系手机号码</label>
				<div class="controls">
					<input id="origin_phone" name="originPhone" type="text" class="input-large required mobile" minlength="3" value="${order.originPhone}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="item_title" class="control-label">入库仓库</label>
				<div class="controls">
					<input id="centro_id" name="centroId" type="hidden"  value="${order.centroId}"/>
					<select class="control-label">
					  <option>湘潭高新仓</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">预计到达时间</label>
				<div class="controls">
				  <input id="fetch_date" name="fetchDate" type="text"  class="span2" data-date-format="yyyy-mm-dd"
				    value='<fmt:formatDate value="${order.fetchDate}" type="date" pattern="yyyy-MM-dd"/>'>
									
				</div>
			</div>			
			<div class="control-group">
				<label for="totalnum" class="control-label">总件数</label>
				<div class="controls">
					<input id="totalnum" name="totalnum" type="text" class="input-large required digits" minlength="1" value="${order.totalnum}"/>
				</div>
			</div>						
			<div class="control-group">
				<label for="item_title" class="control-label">运输公司运单号</label>
				<div class="controls">
					<input id="express_orderno" name="expressOrderno" type="text" class="input-large required" minlength="3" value="${order.expressOrderno}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="item_title" class="control-label">运输公司名称</label>
				<div class="controls">
					<input id="express_company" name="expressCompany" type="text" class="input-large required" minlength="3" value="${order.expressCompany}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="item_title" class="control-label">发货方详细地址</label>
				<div class="controls">
					<input id="dist_address" name="receiverAddress"  type="text" class="input-large required" minlength="3" value="${order.receiverAddress}"/>
				</div>
			</div>
									
			<div class="control-group">
				<label for="remark" class="control-label">备注:</label>
				<div class="controls">
					<textarea id="remark" name="remark" class="input-large">${order.remark}</textarea>
				</div>
			</div>	
			<div class="form-actions">
			<c:if test="${action == 'create'}">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="保存并添加商品"/>&nbsp;
			</c:if>	
			<c:if test="${action == 'update'}">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="保存入库单信息"/>&nbsp;
			</c:if>				
			</div>
		</fieldset>
	</form>

</body>
</html>
