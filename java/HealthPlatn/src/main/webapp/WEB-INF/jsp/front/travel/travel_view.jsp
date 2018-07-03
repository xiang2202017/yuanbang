<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	$(function(){
		$("#phoneF").html($("#phone").val());
	});
</script>
	
<input type="hidden" id="phone" value="${travel.joinPhone }">				
<!-- 商品描述 -->
<div>
	<c:choose>
		<c:when test="${content != null }">
			${content }
		</c:when>
		<c:otherwise>
			<p>暂无相关数据</p>
		</c:otherwise>
	</c:choose>
</div>
				
