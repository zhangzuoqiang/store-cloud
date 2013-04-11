<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品管理</title>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.ext.js" type="text/javascript"></script>
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#item_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/item/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${item.id}"/>
		<input type="hidden" name="type" value="${item.type}"/>
		<fieldset>
			<legend><small>管理商品</small></legend>
			<div class="control-group">
				<label for="item_title" class="control-label">商品名称:</label>
				<div class="controls">
					<input type="text" name="title"  value="${item.title}" class="input-large required" minlength="3"/>
					<p class="help-block">商品标题 ， 如A3时尚春款修身打底衫T恤</p>
				</div>
			</div>	
			<div class="control-group">
				<label for="item_title" class="control-label">商品编号（条形码）:</label>
				<div class="controls">
					<input type="text" name="code"  value="${item.code}" class="input-large required" minlength="8"/>
					<p class="help-block">SKU对应的商品条形码</p>
				</div>
			</div>	
			<div class="control-group">
				<label class="control-label">sku(属性):</label>
				<div class="controls">
					<input type="text" name="sku"  value="${item.sku}" class="input-large"/>
					<p class="help-block">商品sku， 如长袖13180 尺寸:XL; 颜色:天蓝色</p>
				</div>
			</div>				
			<div class="control-group">
				<label for="item_title" class="control-label">重量（单位：克）:</label>
				<div class="controls">
					<input type="text" name="weight"  value="${item.weight}" class="input-large required digits" minlength="1"/>
					<p class="help-block">商品重量（带包装），用于结算快递费用。</p>
				</div>
			</div>	
			<div class="control-group">
				<label for="item_title" class="control-label">描述:</label>
				<div class="controls">
					<textarea name="description" class="input-large">${item.description}</textarea>
				</div>
			</div>	
			
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
