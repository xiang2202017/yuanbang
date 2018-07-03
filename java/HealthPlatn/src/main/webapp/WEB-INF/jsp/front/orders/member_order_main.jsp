<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="col-lg-12 col-md-12 col-sm-12 animate-onscroll">
	<div class="tabs">
		<div class="tab-header">
			<ul>
				<li style="padding: 6px 15px"><a id="d1" onclick="showList(1)">全部</a></li>
				<li style="padding: 6px 15px"><a id="d2" onclick="showList(2)">待付款</a></li>
				<li style="padding: 6px 15px"><a id="d3" onclick="showList(3)">待发货</a></li>
				<li style="padding: 6px 15px"><a id="d4" onclick="showList(4)">待收货</a></li>
				<li style="padding: 6px 15px"><a id="d5" onclick="showList(5)">待评价</a></li>
				<li style="padding: 6px 15px"><a id="d6" onclick="showList(6)">退款/售后</a></li>
			</ul>
		</div>
		
		<div id="listContentDiv" class="tab-content">
			
		</div>
	</div>
</div>

<script type="text/javascript">
	var pageurl = "";	//分页用
	var showDiv;		//分页用
	
	$(function(){
		showDiv = "#listContentDiv";
		if(orderTypeId == -1 && needRefresh == 1){//初次进来需要刷新
			showList(1);
		}else{
			showList(orderTypeId);
		}
	});
	
	//菜单选中样式
	function selectedOrderMenu(menuId){
		if(menuId != orderPreMenuId){
			$("#"+menuId).css("font-weight", "bold");
			$("#"+menuId).css("font-size", "16px");
			$("#"+orderPreMenuId).css("font-weight", "normal");
			$("#"+orderPreMenuId).css("font-size", "15px");
		}
		orderPreMenuId = menuId;
	}

	//显示订单列表
	function showList(type){
	    orderTypeId = type;
	    selectedOrderMenu("d"+type);
		pageurl = '<%=basePath%>web/member/getOrderList.do?type=' + orderTypeId;
		$.ajax({
            url:"<%=basePath%>web/member/getOrderList.do",
            data:{type:type},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listContentDiv").html(data);
                lastMemberPage.push($("#listDiv").html());
            }
        });
	}
	
	//分页必用（列表页面必须保留）
	function getPagingUrl(){
		return pageurl;
	}
</script>
