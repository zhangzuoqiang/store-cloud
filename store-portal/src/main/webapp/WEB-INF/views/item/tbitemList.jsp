<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<head>
	<script type="text/javascript">
		function map(itemid, tbitemid) {
			var skuid = $("#skus_" + tbitemid + " option:selected").val();
			var sku = "0";
			if (typeof skuid != "undefined"){
    			sku = skuid; 
			}
			var url = "${ctx}/item/relate/" + itemid + "/" + + tbitemid + "/" + sku;
			window.location = url;
		}
		
		$(document).ready(function(){
		  	$("#search").click(function(){
		  		var action = "${ctx}/item/mapping/${item.id}?q=" + $("#q").val();
			  	htmlobj=$.ajax({url:action,async:false,type:"post"});
			  	$("#rbody").html(htmlobj.responseText);
		  	});
		});
	</script>
</head>

<body>
	
	<div id="rbody">
	
	<form class="form-search">
		<input id="q" name="q" type="text" class="span5" placeholder="根据标题查询商品..."/>
		<a id="search" href="#" class="btn btn-primary">查询</a>
	</form>
	
	<table id="contentTable" class="table table-striped">
		<thead>
			<tr>
			<th>商品标题</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${tbitems}" var="tbitem">
			<tr>
				<td><i class="icon-chevron-right"/>${tbitem.title} <br>
				<c:if test="${not empty tbitem.skus}">
					<select id="skus_${tbitem.numIid}" name="skuid" >
					<c:forEach items="${tbitem.skus}" var="sku">
						<option value="${sku.skuId}">${sku.propertiesName}</option>
					</c:forEach>
					</select>
				</c:if>
				<td><a href="javascript:map(${item.id},${tbitem.numIid})">添加</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	</div>
</body>