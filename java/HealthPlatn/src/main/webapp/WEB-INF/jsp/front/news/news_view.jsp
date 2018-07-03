<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	function getAnother(newId){
		var urlstr = "<%=basePath%>web/newsDetail.do";
		$.ajax({
            url:urlstr,
            data:{"newsId":newId},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
</script>
<div>${content }</div>
<div style="float: left;">
	<c:choose>
		<c:when test="${nextNews!=null }">
			<a style="cursor: pointer;" onclick="getAnother('${nextNews.id}')">上一条：${nextNews.title }</a>
		</c:when>
	</c:choose>
</div>
<div style="float: right;">
	<c:choose>
		<c:when test="${previousNews!=null }">
			<a style="cursor: pointer;" onclick="getAnother('${previousNews.id}')">下一条：${previousNews.title }</a>
		</c:when>
	</c:choose>
</div>
