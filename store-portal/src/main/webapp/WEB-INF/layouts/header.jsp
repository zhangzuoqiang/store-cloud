<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="header">
<div class="navbar  navbar-fixed-top">
    <div class="navbar-inner">
        <div class="nav-collapse">
          <ul class="nav">
            <li class="active"><a href="#">物流通-电商仓储管理中心</a></li>
            <li><a href="#"><shiro:principal property="shopname"/></a></li>
          </ul>
          <ul class="nav pull-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">功能 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">消息</a></li>
                <li><a href="#">待办事项</a></li>
                <li class="divider"></li>
                <li><a href="#">退出</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->
 	
  <legend></legend> 
  <br><br>
  	<!-- <img src = "${ctx}/static/images/envato.png"> 
	<h1 class="small">
	&nbsp;&nbsp;
    &nbsp;&nbsp;物流通&nbsp; <shiro:principal property="shopname"/>
    </h1>
    -->
</div>