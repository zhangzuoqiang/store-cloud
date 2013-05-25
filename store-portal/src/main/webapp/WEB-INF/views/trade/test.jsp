<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>未处理订单</title>
	<link href="${ctx}/static/messenger/messenger.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/messenger/messenger-theme-future.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/messenger/messenger.min.js" type="text/javascript"></script>
	<script  type="text/javascript">
	$(function() {
		
		// 全选事件
	   	$("#checkAll").click(function() {
	   		if($(this).attr("checked") == "checked") {
	   			$("input[name='trade_select[]']").each(function() {
	            	$(this).attr("checked", true);
	        	});
	   		} else {
	   			$("input[name='trade_select[]']").each(function() {
	            	$(this).attr("checked", false);
	        	});
	   		}
		});
		
	    // 发送事件
	   $('#submit').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="trade_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何订单！');
	  		} else {
	  			// 分段发送至仓库
	  			var arr = new Array();
	  			var size=200;
	  			for(var i = 1, len = chk_value.length; i<= len; i++) {
	  				arr.push(chk_value[i-1]);
	  				if (i%size == 0 ) {
	  					postTrade(arr);
	  					arr=[];
	  				} else if (i == len) {
	  					postTrade(arr);
	  				}
     			}
	  		}
	   });
	   
	   function testId() {
	   }
	   
	   function postTrade(tids) {
		   var action = "${ctx}/rest/trade/test?tids=" + tids;
		   $.post(action, function(data){
			   $.globalMessenger().post({message:data,  showCloseButton: true});
			   $("#body").html(data);
		   });
	   }
	   
	});
	
	</script>
</head>
<body>

<a id="submit" href="#" class="btn btn-success pull-right">物流通发货</a>

   <div class="tab-content">
    	<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>序号</th>
			<th><input type="checkbox" id="checkAll" name="checkAll"/> 全选</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${array}" var="i">
				<tr>
							
					<td>${i}</td>
					<td>
						<input type='checkbox' id='trade_select' name='trade_select[]' value='${i}' />
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
    </div>