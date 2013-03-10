<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
	<form id="uploadForm" action="${ctx}/item/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
		<fieldset>
			<legend><small>添加商品</small></legend>
			<div class="control-group">
				<!--<label for="item_title" class="control-label">选择文件:</label>-->
				<div class="controls">
					<input type="file" name="file" /> 
					<a href="${ctx}/static/download/商品模板.xls" class="">下载商品模板 <small>(商品模板.xls)</small></a>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
