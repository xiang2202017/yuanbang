<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
<script type="text/javascript">
	var memberPreMenuId = -1;
	var orderPreMenuId = -1;
	var orderTypeId = -1;
	
	var lastMemberPage = new Array();
	
	//菜单选中样式
	function selectedMemberMenu(menuId){
		if(menuId != memberPreMenuId){
			$("#"+menuId).css("font-weight", "bold");
			$("#"+menuId).css("font-size", "16px");
			$("#"+memberPreMenuId).css("font-weight", "normal");
			$("#"+memberPreMenuId).css("font-size", "15px");
		}
		memberPreMenuId = menuId;
	}
	
	//跳转到会员消息页面
	function toMemberMsg(menuId){
		selectedMemberMenu(menuId);
		$.ajax({
            url:"<%=basePath%>web/member/toMsg.do",
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	
	//跳转到会员资料
	function toMemberInfo(menuId){
		selectedMemberMenu(menuId);
		$.ajax({
            url:"<%=basePath%>web/member/toMemberInfo.do",
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	
	//跳转到会员邮寄地址页面
	function toMemberAddress(menuId){
		selectedMemberMenu(menuId);
		$.ajax({ 
			url : "<%=basePath%>web/member/toAddress.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		}); 
	}
	
	//跳转到会员密码修改页面
	function toMemberPassword(menuId){
		selectedMemberMenu(menuId);
		$.ajax({ 
			url : "<%=basePath%>web/member/toMemberPassword.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		}); 
	}
	
	//跳转到会员电话号码修改页面
	function toMemberPhone(menuId){
		selectedMemberMenu(menuId);
		$.ajax({ 
			url : "<%=basePath%>web/member/toMemberPhone.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		}); 
	}
	
	//跳转到会员续约页面
	function toMemberRenew(menuId){
		selectedMemberMenu(menuId);
		$.ajax({ 
			url : "<%=basePath%>web/member/toMemberRenew.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		});
	}
	
	//跳转到会员解约页面
	function toMemberCancel(menuId){
		selectedMemberMenu(menuId);
		$.ajax({ 
			url : "<%=basePath%>web/member/toMemberCancel.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		});
	}
	
	//跳转到会员加盟页面
	function toMemberJoin(menuId){
		selectedMemberMenu(menuId);
		$.ajax({ 
			url : "<%=basePath%>web/member/toMemberJoin.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		});
	}
	
	//我的订单
	function toOrder(){
		selectedMemberMenu('m2');
	    needRefresh = 1;//订单页面初始化需要刷新
	    lastMemberPage = new Array();
	    orderPreMenuId = -1;
		$.ajax({ 
			url : "<%=basePath%>web/member/toMemberOrderMain.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#listDiv").html(data);
			} 
		});
	}
	
	function memberLoginOut(){
		$.ajax({ 
			url : "<%=basePath%>web/member/memberLoginOut.do", 
			dataType:"html",
            type:"post",
			success : function(data){ 
				$("#mfDiv").html(data);
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
						<ul class="shop-items-widget">
							<li>
								<div class="shop-item-content">
									<h6><a id="m1" style="cursor: pointer;" onclick="toMemberMsg('m1')">消息中心</a></h6>
								</div>
							</li>
							<li>
								<div class="shop-item-content">
									<h6><a id="m2" style="cursor: pointer;" onclick="toOrder('m2')">我的订单</a></h6>
								</div>
							</li>
							<li>
								<div class="shop-item-content">
									<h6><a id="m3" style="cursor: pointer;" onclick="toMemberInfo('m4')">个人资料管理</a></h6>
								</div>
							</li>
							<li>
								<div class="shop-item-content">
									<ul>
										<li><a id="m4" style="cursor: pointer;" onclick="toMemberInfo('m4')">基本信息</a></li>
										<li><a id="m5" style="cursor: pointer;" onclick="toMemberAddress('m5')">收货地址管理</a></li>
										<li><a id="m6" style="cursor: pointer;" onclick="toMemberPhone('m6')">手机修改</a></li>
										<li><a id="m7" style="cursor: pointer;" onclick="toMemberPassword('m7')">密码修改</a></li>
									</ul>
								</div>
							</li>
							<li>
								<div class="shop-item-content">
									<h6><a id="m8" style="cursor: pointer;" onclick="toMemberJoin('m8')">会员加盟</a></h6>
								</div>
							</li>
							<li>
								<div class="shop-item-content">
									<h6><a id="m9" style="cursor: pointer;" onclick="toMemberRenew('m9')">会员续约</a></h6>
								</div>
							</li>
							<li>
								<div class="shop-item-content">
									<h6><a id="m10" style="cursor: pointer;" onclick="toMemberCancel('m10')">会员解约</a></h6>
								</div>
							</li>
						</ul>
					</div>
					
				</div>
				<!-- /Sidebar -->
				<div class="col-lg-9 col-md-9 col-sm-8">
					<div id="listDiv" style="vertical-align: middle;">
						<p>欢迎您，${member.memberName }!&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="memberLoginOut()">退出</a></p>
					</div>
				</div>			
			</div>
		</section>
	</section>
