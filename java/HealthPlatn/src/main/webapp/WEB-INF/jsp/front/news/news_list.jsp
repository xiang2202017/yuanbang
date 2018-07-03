<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	function getDetail(newsId){
		var urlstr = "<%=basePath%>web/newsDetail.do";
		$.ajax({
            url:urlstr,
            data:{"newsId":newsId},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
</script>

<h3 class="animate-onscroll no-margin-top">${title}</h3>
<!-- 资讯 -->
<c:choose>
	<c:when test="${fpd.newslist != null && fn:length(fpd.newslist) != 0 }">
		<c:forEach items="${fpd.newslist }" var="item">
			<div class="blog-post big animate-onscroll">
				
				<div class="post-image" style="width: 40%">
					<img src="${item.imgPath }" width="350" height="230">
				</div>
				
				<p class="post-title"><a href="blog-single-sidebar.html" style="font-size: 17px">${item.title }</a></p>
				
				<div class="post-meta">
					<span>${item.editime != null ? item.editime : item.creatime}</span>
				</div>
				
				<p>${item.remark }</p>
				
				<a style="cursor: pointer;" onclick="getDetail('${item.id}')"  class="button read-more-button big button-arrow">更多</a>
				
			</div>
		</c:forEach>
		<!-- 分页 -->
		<div >
			<div class="divider"></div>
			<div style="float: right;padding-top: 0px;margin-top: 0px;">${page.frontPageStr}</div>
		</div>
	</c:when>
	<c:otherwise>
		<div><p align="center"><br><br><br>没有相关数据</p></div>
	</c:otherwise>
</c:choose>
<!--

//-->
