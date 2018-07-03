<%@page import="com.health.entity.system.FMenu"%>
<%@page import="java.util.List"%>
<%@page import="com.health.system.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<FMenu> fmenulist = (List<FMenu>)session.getAttribute(Const.SESSION_FRONT_MENULIST);
%>
<!DOCTYPE html>

<html>

	<head>
		
		<!-- Meta Tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- Title -->
		<title>主页</title>
		
		<!-- Favicon -->
		<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>static/front_UI/img/favicon.ico">
		
		<!-- Stylesheets -->
		<link href="<%=basePath %>static/front_UI/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/fontello.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/flexslider.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/js/revolution-slider/css/settings.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="<%=basePath %>static/front_UI/css/owl.carousel.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/responsive-calendar.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/chosen.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/jackbox/css/jackbox.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>static/front_UI/css/cloud-zoom.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>static/front_UI/css/style.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/custom.css" rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<link href="<%=basePath %>static/front_UI/css/jackbox-ie8.css" rel="stylesheet" type="text/css" />
			<link rel="stylesheet" href="<%=basePath %>static/front_UI/css/ie.css">
        <![endif]-->
		
		<!--[if gt IE 8]>
			<link href="<%=basePath %>static/front_UI/css/jackbox-ie9.css" rel="stylesheet" type="text/css" />
		<![endif]-->
		
		<!--[if IE 7]>
			<link rel="stylesheet" href="<%=basePath %>static/front_UI/css/fontello-ie7.css">
		<![endif]-->
		
		<style type="text/css">
			.no-fouc {display:none;}
	  	</style>
		
		<script type="text/javascript">
		var lastPage;
		var needRefresh = -1;//页面需要刷新吗？全局变量 -1 不需要 1需要
		var preMenuId = 1;
		
		function openMenu(menuUrl, menuId, parentMenuId){
			var showMenu = menuId;
			if(parentMenuId != ""){
				showMenu = parentMenuId;
			}
			if(showMenu != preMenuId){
				$("#"+preMenuId).removeClass("current-menu-item");
				$("#"+showMenu).addClass("current-menu-item");
			}
			preMenuId = showMenu;
			
			if(menuId == 1){//首页
				$("#menuForm").attr("action",menuUrl); 
				$("#menuForm").submit();
			}else{
				var urlstr = "<%=basePath%>"+menuUrl;
				var index = urlstr.indexOf("?", 0);
				if(index < 0){
					urlstr = "<%=basePath%>"+menuUrl + "?tm="+new Date().getTime();
				}
				$.ajax({
		            url:urlstr,
		            data:{"menuId":menuId, "menuType":"1"},//点击除首页外的菜单
		            dataType:"html",
		            type:"post",
		            success:function(data){
		                //div加载页面
		                //bootbox.alert("test");
		                $("#mfDiv").html(data);
		                lastPage = data;
		            },
		            error: function(XMLHttpRequest, textStatus, errorThrown) { 
		            	alert(XMLHttpRequest.status); 
		            	alert(XMLHttpRequest.readyState); 
		            	alert(textStatus); 
		            }
		        });
			}
		}
		
		//打开购物车页面
		function openMenucart(){
			$.ajax({
	            url:"<%=basePath%>web/member/toShoppingCart.do",
	            dataType:"html",
	            type:"post",
	            success:function(data){
	                //div加载页面
	                $("#mfDiv").html(data);
	            },
	            error: function(XMLHttpRequest, textStatus, errorThrown) { 
	            	alert(XMLHttpRequest.status); 
	            	alert(XMLHttpRequest.readyState); 
	            	alert(textStatus); 
	            }
	        });
		}
		
		//调整footer的位置
		function resizeWin(){
			var fixedHeight = 192 + 66;		//页面顶部加页面底部
			var winHeight = document.body.clientHeight || document.documentElement.clientWidth ;	//网页的高度
			var contentHeight = document.body.scrollHeight;		//浏览器可以显示网页的高度
			if(contentHeight > winHeight){
				//$("#mfDiv").height(contentHeight - fixedHeight);
			}
			alert(winHeight);
		}
		</script>
		
	</head>
	
	<body class="sticky-header-on tablet-sticky-header">
	
		<!-- Container -->
		<div class="container">
			
			
			<!-- Header -->
			<header id="header" class="animate-onscroll">
				<!-- Main Header -->
				<div id="main-header">
					<div class="container">
					<div class="row">
						<!-- Logo -->
						<div id="logo" class="col-lg-3 col-md-3 col-sm-3" style="padding: 0;margin: 0">
<!-- 							<a href="#"><img src="<%=basePath %>static/front_UI/img/logo.png" alt="Logo" width="100" height="40"></a> -->
							<h1 class="great-vibes" style="padding-top: 0;height: 27px;line-height: 37px">源邦实业集团</h1>
						</div>
						<!-- /Logo -->
						<!-- Main Quote -->
						<div class="col-lg-5 col-md-4 col-sm-4">
							<blockquote>我们一起<br>开创绿色生活</blockquote>
						</div>
						<!-- /Main Quote -->
					</div>
					</div>
				</div>
				<!-- /Main Header -->
				<!-- Lower Header -->
				<div id="lower-header">
					<div class="container">
					<div id="menu-button">
						<div>
						<span></span>
						<span></span>
						<span></span>
						</div>
						<span>菜单</span>
					</div>
					<form id="menuForm" style="display: none;" method="post">
						<input type="hidden" id="menuId" name="menuId">
					</form>
					<ul id="navigation">
						<c:forEach items="<%=fmenulist %>" var="menu" varStatus="status">
							<c:choose>
								<c:when test="${status.count==1}">
									<li id="${menu.menu_id }" class="current-menu-item">
								</c:when> 
								<c:otherwise>
									<li id="${menu.menu_id }">
								</c:otherwise>
							</c:choose>
							<a style="cursor:pointer;" onclick="openMenu('${menu.menu_url}','${menu.menu_id }','')" >
								<span>${menu.menu_name }</span>
							</a>
							<ul>
								<c:if test="${menu.subMenu != null }">
									<c:forEach items="${menu.subMenu}" var="sub">
										<li id="${sub.menu_id }">
											<a style="cursor:pointer;" onclick="openMenu('${sub.menu_url}','${sub.menu_id }','${sub.parent_id }')">
												${sub.menu_name }
											</a>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</c:forEach>
						<!-- Donate -->
						
						<li class="donate-button ">
							<a onclick="openMenucart()">购物车</a>
						</li>
						<!-- /Donate -->
					</ul>
					</div>
				</div>
				<!-- /Lower Header -->
			</header>
			<!-- /Header -->

					
			<!-- center -->
			<div id="mfDiv" class="content" style="min-height: 100%;height: 100%;">
				<jsp:include page="${fpd.pagePath }"></jsp:include>
			</div>
			<!-- center -->

			<!-- /Footer -->
			<footer id="footer" style="height: 66px;">
				<div id="lower-footer">
					<div class="row">
						<div >
							<p class="copyright" align="center">© 2017 Candidate. All Rights Reserved. <a href="#" target="_blank" title="陕西源邦集团">陕西源邦集团</a> - Collect from <a href="#" title="陕西源邦集团" target="_blank">陕西源邦集团</a></p>
						</div>
					</div>
				</div>
			</footer>

			<!-- /Footer -->
			
			
			
			<!-- Back To Top -->
			<a href="#" id="button-to-top"><i class="icons icon-up-dir"></i></a>
		
		</div>
		<!-- /Container -->	
	
		<!-- JavaScript -->
		<!-- jQuery -->
		<script src="<%=basePath %>static/front_UI/js/jquery-1.11.0.min.js"></script>
		<script src="<%=basePath %>static/front_UI/js/jquery-ui-1.10.4.min.js"></script>
		
		<!-- Preloader -->
		<script src="<%=basePath %>static/front_UI/js/jquery.queryloader2.min.js"></script>
		
		<!-- Bootstrap -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/bootstrap.min.js"></script>
		
		<!-- Modernizr -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/modernizr.js"></script>
		
		<!-- Sliders/Carousels -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.flexslider-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/owl.carousel.min.js"></script>
		
		<!-- Revolution Slider  -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/revolution-slider/js/jquery.themepunch.plugins.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/revolution-slider/js/jquery.themepunch.revolution.min.js"></script>
		
		<!-- Calendar -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/responsive-calendar.min.js"></script>
		
		<!-- Raty -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.raty.min.js"></script>
		
		<!-- Chosen -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/chosen.jquery.min.js"></script>
		
		<!-- jFlickrFeed -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jflickrfeed.min.js"></script>
		
		<!-- InstaFeed -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/instafeed.min.js"></script>
		
		<!-- Twitter -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/php/twitter/jquery.tweet.js"></script>
		
		<!-- MixItUp -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.mixitup.js"></script>
		
		<!-- JackBox -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/jackbox/js/jackbox-packed.min.js"></script>
		
		<!-- CloudZoom -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/zoomsl-3.0.min.js"></script>
		
		<!-- Main Script -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/script.js"></script>
		<!-- bootbox -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/bootbox.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>static/js/validate.js"></script>
		
		<!--[if lt IE 9]>
			<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.placeholder.js"></script>
			<script type="text/javascript" src="<%=basePath %>static/front_UI/js/script_ie.js"></script>
		<![endif]-->
		
	</body>

</html>