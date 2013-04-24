<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>未处理订单</title>
	<link href="${ctx}/static/messenger/messenger.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/messenger/messenger-theme-future.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/messenger/messenger.min.js" type="text/javascript"></script>
	<script  type="text/javascript">
	$(function() {
	   	
	   	$("#batchSend").click(function() {
	   		var tids = ${unrelated};
	   		if (tids.length==0) {
	   			alert('不需要发送！');
	   		} else {
	   			var action = "${ctx}/rest/trade/send?tids="+tids;
	  			$.post(action, function(data){
	  				$.globalMessenger().post({message:data, showCloseButton: true});
	  				$("#body").html(data);
	  			});
	   		}
		});
	
	});
	</script>
</head>
<body>
	<div id="body">
	<ul>
		<li>可发送(${fn:length(unrelated)}) <a id="batchSend" class="btn btn-primary" href="#"> 批量发送1000条 </a></li>
		<li>已接受(${fn:length(related)})</li>
	</ul>
	</div>
</body>
</html>
