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
	var showDiv;		//分页用
	
	$(function(){
		showDiv = "#listDiv";
	});
	
	//分页必用（列表页面必须保留）
	function getPagingUrl(){
		var typeid = $("#typeIdInp").val();
		var urlstr = "<%=basePath%>web/getProductList?type="+typeid;
		return urlstr;
	}

	//搜索产品名
	function search(){
		var name = $("#name").val();
		var url = getPagingUrl() + "&name=" + name;
		getList(url);
	}
	
	var prePMenuId = "";
	
	//产品类别菜单点击
	function openProductMenu(id){
		var preMenuAid;
		var menuAid = "menu" + id;
		if(prePMenuId == ""){
			prePMenuId = $("#typeIdInp").val();
		}
		preMenuAid = "menu" + prePMenuId;
		if(id != prePMenuId){
			$("#"+menuAid).css("font-weight", "bold");
			$("#"+menuAid).css("font-size", "16px");
			$("#"+preMenuAid).css("font-weight", "normal");
			$("#"+preMenuAid).css("font-size", "15px");
		}
		prePMenuId = id;
		$("#typeIdInp").val(id);
		$("#name").val("");
		var url = getPagingUrl();
		getList(url);
	}
	
	//得到产品列表
	function getList(urlstr){
		$.ajax({
            url:urlstr,
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	
	//获取产品详情
	function getDetail(productId){
		var urlstr = "<%=basePath%>web/toProductView.do";
		$.ajax({
            url:urlstr,
            data:{"productId":productId},
            dataType:"html",
            type:"post",
            success:function(data){
            	//alert(data);
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	
	//添加到购物车
	function addToCart(id){
		$.ajax({
            url:"<%=basePath%>web/member/addToCart.do",
            data:{"productId":id},
            dataType:"json",
            type:"post",
            success:function(data){
            	var result = data.result;
            	if(result == "fail"){
            		alert("添加失败");
            	}else if(result == "success"){
                	alert("已添加到购物车");
                }else if(result == "nologin"){
                	alert("您还未登录，请先登录后再来操作");
                }
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
						<input type="hidden" id="typeIdInp" value="${pd.typeId }">
						<ul class="shop-items-widget">
							<c:forEach items="${typeList}" var="menu">
								<c:choose>
									<c:when test="${pd.typeId == menu.id }">
										<li>
											<div class="shop-item-content">
												<h6><a id="menu${menu.id }" style="cursor: pointer;font-weight: bold;font-size: 16px" onclick="openProductMenu('${menu.id}')">${menu.name }</a></h6>
											</div>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<div class="shop-item-content">
												<h6><a id="menu${menu.id }" style="cursor: pointer;" onclick="openProductMenu('${menu.id}')">${menu.name }</a></h6>
											</div>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
						
					</div>
					<!-- /Instagram Photos -->						<!-- Top Rated Products -->
					<div class="sidebar-box white animate-onscroll">
						
						<h3>畅销产品</h3>
						
						<ul class="shop-items-widget">
							<c:forEach items="${topList }" var="item">
								<li>
									<div class="featured-image">
										<img src="${item.imgPath }" alt="">
									</div>
									<div class="shop-item-content">
										<h6><a onclick="getDetail('${item.id}')">${item.name }</a></h6>
										<span class="price">${item.price }</span>
									</div>
								</li>
							</c:forEach>
						</ul>
						
					</div>
					<!-- /Top Rated Products -->						
					
				</div>
				<!-- /Sidebar -->
				
				<div class="col-lg-9 col-md-9 col-sm-8">
						<div id="searchDiv" class="animate-onscroll" style="float: right;">
							<span>产品名：<input id="name" name="name"> <button onclick="search()">搜索</button></span>
						</div>
						<br>
						<hr width="100%">
						<div id="listDiv">
							<jsp:include page="product_list.jsp"></jsp:include>
						</div>
				</div>			
			</div>
		</section>
	</section>
