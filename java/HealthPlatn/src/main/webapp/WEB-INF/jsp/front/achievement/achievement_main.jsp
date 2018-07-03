<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	var prePMenuId = "";
	var pageurl = "";	//分页用
	var showDiv;		//分页用
	
	$(function(){
		showDiv = "#listDiv";
		pageurl = "<%=basePath%>" + $("#menuUrlInp").val();
		
		var menuId = "menu" + $("#menuIdInp").val();
		$("#"+menuId).css("font-weight", "bold");
		$("#"+menuId).css("font-size", "16px");
		prePMenuId = menuId;
		$.ajax({
            url:pageurl,
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	});
	
	//分页必用（列表页面必须保留）
	function getPagingUrl(){
		return pageurl;
	}
	
	//菜单点击
	function openAchMenu(id, memurl){
		var menuAid = "menu" + id;
		if(prePMenuId == ""){
			prePMenuId = $("#menuIdInp").val();
		}
		if(id != prePMenuId){
			$("#"+menuAid).css("font-weight", "bold");
			$("#"+menuAid).css("font-size", "16px");
			$("#"+prePMenuId).css("font-weight", "normal");
			$("#"+prePMenuId).css("font-size", "15px");
		}
		prePMenuId = menuAid;
		$("#menuIdInp").val(id);
// 		var urlstr = '<%=basePath%>'+memurl;
		pageurl = memurl;
		$.ajax({
            url:memurl,
            data:{"menuId":id},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	

</script>

<!-- Section -->
	<section id="content">
		<section class="section full-width-bg gray-bg">
			<div class="row">
			
				<!-- Sidebar -->
				<div class="col-lg-3 col-md-3 col-sm-4 sidebar">
					
					
					<!-- Top Rated Products -->
					<div class="sidebar-box white animate-onscroll">
						<input type="hidden" id="menuIdInp" value="${fpd.menuId }">
						<input type="hidden" id="menuUrlInp" value="${fpd.menuUrl }">
						<ul class="shop-items-widget">
										<li >
											<div class="shop-item-content" >
												<h6><a id="menu15" style="cursor: pointer;font-weight: bold;font-size: 16px" onclick="openAchMenu(15,'<%=basePath%>'+'web/member/consume.do')">消费记录</a></h6>
											</div>
										</li>
										<li >
											<div class="shop-item-content" >
												<h6><a id="menu16" style="cursor: pointer;" onclick="openAchMenu(16,'<%=basePath%>'+'web/member/score.do')">积分查询</a></h6>
											</div>
										</li>
						</ul>
						
					</div>
					
				</div>
				<!-- /Sidebar -->
				
				<div class="col-lg-9 col-md-9 col-sm-8" >
						<div id="listDiv" style="height:400px">
							
						</div>
				</div>			
			</div>
		</section>
	</section>
