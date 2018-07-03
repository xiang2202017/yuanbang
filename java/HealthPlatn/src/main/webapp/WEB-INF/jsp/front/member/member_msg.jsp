<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">

//获取消息详情
function getMsgDetail(id){
	$.ajax({
           url:"<%=basePath%>web/member/toMsgDetail.do",
           data:{"id":id},
           dataType:"html",
           type:"post",
           success:function(data){
               //div加载页面
               $("#listDiv").html(data);
           }
       });
}
</script>

<h3 class="animate-onscroll no-margin-top">会员消息列表</h3>
<!-- 资讯 -->
<c:forEach items="${msglist }" var="item">
	<div class="blog-post big animate-onscroll">
		
		<div class="post-image" style="width:90px;height:50px">
			<img src="${item.imgPath }" alt="" >
		</div>
		
		<h4 class="post-title"><a style="cursor: pointer;" onclick="getMsgDetail('${item.id}')">${item.title }</a></h4>
		
		<div class="post-meta">
			<span>${item.editime != null ? item.editime : item.creatime}</span>
		</div>
		<br>
		<p>${item.remark }</p>
		
		<a style="cursor: pointer;" onclick="getMsgDetail('${item.id}')"  class="button read-more-button big button-arrow">更多</a>
		
	</div>
</c:forEach>
<!-- 分页 -->
<div class="animate-onscroll">
	<div class="divider"></div>
	<div class="numeric-pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.frontPageStr}</div>
</div>