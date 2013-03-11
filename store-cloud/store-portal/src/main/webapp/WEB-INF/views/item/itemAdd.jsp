<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tab.js" type="text/javascript"></script>
</head>
<body>

	<legend><small>添加商品</small></legend>

	<ul class="nav nav-tabs">
	  <li class="active"><a href="#upload" data-toggle="tab">文件上传</a></li>
	  <li><a href="#import" data-toggle="tab">导入淘宝商品</a></li>
	</ul>
	
	<div class="container">
    <div class="tab-content">
    	<div class="tab-pane active" id="upload">
		<form id="uploadForm" action="${ctx}/item/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
		<fieldset>
			<div class="control-group">
				<div class="controls">
					<input type="file" name="file"/> 
					<a href="${ctx}/static/download/商品模板.xls" class="">下载商品模板 <small>(商品模板.xls)</small></a>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
		</form>
		</div>
		<div class="tab-pane" id="import">
			<div>
				<a href="#" class="btn btn-primary">一键导入所有淘宝商品</a>
			</div>
		</div>
	</div>
</body>
</html>