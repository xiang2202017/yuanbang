<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
	
</style>
	<script type="text/javascript">
	var pageurl = "";	//分页用
	var showDiv;		//分页用
	var preNewMenuId ;
	
	$(function(){
		showDiv = "#mfDiv";
		var typeId = $("#typeIdInp").val();
		pageurl = '<%=basePath%>web/newsList?type=' + typeId;
		
		var newsId = $("#choosedId").val();
		if(newsId != null && newsId != ""){//从首页内容资讯列表处跳转过来
			var urlstr = "<%=basePath%>web/newsDetail.do";
			$.ajax({
	            url:urlstr,
	            data:{"newsId":newsId},
	            dataType:"html",
	            type:"post",
	            success:function(data){
					//div加载页面
	                $("#listDiv").html(data);
	                $('body').scrollTop(0);
	            }
	        });
		}
	});
	
	
	//分页必用（列表页面必须保留）
	function getPagingUrl(){
		return pageurl;
	}
	
	
	function openNewsMenu(menuid, menuUrl){
		if(menuid != preNewMenuId){
			$("#menu"+menuid).css("font-weight", "bold");
			$("#menu"+menuid).css("font-size", "16px");
			$("#"+preNewMenuId).css("font-weight", "normal");
			$("#"+preNewMenuId).css("font-size", "15px");
		}
		preNewMenuId = "menu"+menuid;
		var urlstr = "<%=basePath%>"+menuUrl;
		pageurl = urlstr;
		$.ajax({
            url:urlstr,
            dataType:"html",
            data:{"menuId":menuid},
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
	
	</script>
<section id="content">	
			
			<!-- Section -->
			<section class="section full-width-bg gray-bg">
				
				<div class="row">
					<!-- Sidebar -->
					<div id="navDiv" class="col-lg-3 col-md-3 col-sm-4 sidebar">
						<input type="hidden" id="typeIdInp" value="${fpd.type }">
						<input type="hidden" id="choosedId" value="${newsId }">
						<div class="sidebar-box white animate-onscroll">
							<ul id="verNavgation" class="shop-items-widget">
								<c:forEach items="${fpd.submenulist}" var="menu" varStatus="status">
									<c:if test="${status.count==1}">
										<input id="firstInp" type="hidden" value="${menu.menu_url}">
									</c:if>
									<c:choose>
										<c:when test="${menu.menu_id == fpd.menuId }">
											
											<li >
												<div class="shop-item-content" >
													<h6><a id="menu${menu.menu_id}" style="cursor: pointer; font-weight: bold;font-size: 16px" onclick="openNewsMenu('${menu.menu_id }','${menu.menu_url}')">${menu.menu_name }</a></h6>
												</div>
											</li>
										</c:when>
										<c:otherwise>
											<li >
												<div class="shop-item-content" >
													<h6><a id="menu${menu.menu_id}" style="cursor: pointer;" onclick="openNewsMenu('${menu.menu_id }','${menu.menu_url}')">${menu.menu_name }</a></h6>
												</div>
											</li>
										</c:otherwise>
									</c:choose>
									
								</c:forEach>
								
							</ul>
							
						</div>
						
						<div class="sidebar-box white animate-onscroll">
							<h3>点击排行榜</h3>
							<ul class="upcoming-events">
								<c:forEach items="${fpd.topNews }" var="item" varStatus="vs">
									<c:if test="${vs.index < 5}">
										<li>
											<div class="event-content">
												<h6><a onclick="getDetail('${item.id}')">${item.title }</a></h6>
												<ul class="event-meta">
													<li><i class="icons icon-clock"></i>${item.editime != null ? item.editime : item.creatime}</li>
												</ul>
											</div>
										</li>
										<!-- /Event -->
									</c:if>
								</c:forEach>
							</ul>
						</div>
						
					</div>
					<!-- /Sidebar -->
					<div id="listDiv" class="col-lg-9 col-md-9 col-sm-8">
						<jsp:include page="news_list.jsp"></jsp:include>
					</div>
				</div>
			</section>
			<!-- /Section -->
		
		</section>
	
