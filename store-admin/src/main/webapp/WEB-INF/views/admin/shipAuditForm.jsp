<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>出库确认</title>
	<script type="text/javascript">
	function query() {
		var action = "${ctx}/trade/ship/audit/ajax?q=" + $("#q").val();
   		htmlobj=$.ajax({
			url:action,
			async:true,
			type:"post",
			success: function(msg) {
                 $("#content").html(htmlobj.responseText);
            },
				error: function(XMLHttpRequest, textStatus, errorThrown) {
            }
		});
	}
	</script>
</head>

<body>
	<br>
	<div class="row">
		<div class="span4">
			<input id="q" class="span10" type="text" name="orderno"  placeholder="淘宝交易号\快递运单号\卖家昵称" />
		</div>
		<div>
			<a href="#" class="btn btn-primary" onclick="query();">查询交易订单</a>
		</div>
	</div>
	<div id="content">
	</div>
</body>
</html>
