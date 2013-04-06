<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<script type="text/javascript">

$(function() {
   	$("#call").click(function() {
   		var action = "${ctx}/stock/ajax/detail?userid=" + $("#selectUser").val();
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
	});
});
</script>
</head>
<body>

<legend><small>库存状态</small></legend>

	<div>
	<select id="selectUser">
		<c:forEach items="${users}" var="user">
			<option value='${user.id}'>${user.shopName}</option>
		</c:forEach>
	</select>
	<a id="call" href="#" class="btn btn-primary">查看库存</a>
	</div>

	<div id="content">
	</div>
	
</body>
</html>