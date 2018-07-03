<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	var preNewMenuId = 'contact';

	function openContent(menuid){
		if(menuid != preNewMenuId){
			$("#"+menuid).css("font-weight", "bold");
			$("#"+menuid).css("font-size", "16px");
			$("#"+preNewMenuId).css("font-weight", "normal");
			$("#"+preNewMenuId).css("font-size", "15px");
			$("#"+menuid + "Div").show();
			$("#"+preNewMenuId + "Div").hide();
		}
		preNewMenuId = menuid;
	}
	
</script>

	<section id="content" class="full-width-bg gray-bg" style="padding-top: 30px;">
		<div class="row">
		
			<!-- Sidebar -->
			<div class="col-lg-3 col-md-3 col-sm-4 sidebar">
				<div class="sidebar-box white animate-onscroll">
					<ul class="shop-items-widget">
						<li>
							<div class="shop-item-content">
								<h6><a id="contact" style="cursor: pointer;font-weight: bold;font-size: 16px" onclick="openContent('contact')">联系方式</a></h6>
							</div>
						</li>
						<li>
							<div class="shop-item-content">
								<h6><a id="position" style="cursor: pointer;" onclick="openContent('position')">公司位置</a></h6>
							</div>
						</li>
					</ul>
					
				</div>
				<div class="sidebar-box white">
					<img src="<%=basePath %>static/front_UI/img/contact.jpg">
					<label>加盟热线：029-88868800</label>
				</div>						
				
			</div>
			<!-- /Sidebar -->
			
			<div class="col-lg-9 col-md-9 col-sm-8">
				<div id="contactDiv">
					<h4>服务热线：029-88868800</h4>
					<label>二维码：</label>
				</div>
				<div id="positionDiv" class="contact-map" style="display: none">
					<img src="<%=basePath %>static/front_UI/img/map.jpg" >
<!-- 					<iframe width="900" height="400" src=""></iframe> -->
				</div>
			</div>
		</div>	
	</section>
