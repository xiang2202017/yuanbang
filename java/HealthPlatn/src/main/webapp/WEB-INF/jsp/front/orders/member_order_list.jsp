<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<c:choose>
		<c:when test="${orderlist == null || fn:length(orderlist) == 0 }">
			<div style="text-align: center;">
				<br>
				<br>
				暂时没有相关数据!
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach items="${orderlist }" var="order">
				<table>
					<tr>
						<td colspan="4">
							订单编号：<a onclick="getOrderDetail('${order.id}')">${order.orderNo }</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单创建时间：${order.createTime }
						</td>
					</tr>
					<c:forEach items="${ order.productlist }" var="product">
						<tr>
							<td width="170"><img src="${product.productImg }" width="120" height="100"></td>
							<td>${product.productName }</td>
							<td>数量：${product.num }</td>
							<td>
								单价：${product.price }
								<c:if test="${product.status != null && product.status != 4  }">
										<a onclick="checkRefund('${order.id}','${product.id }','${product.refundId }')">（退款中）</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td>订单状态：<font color="orange">
							<c:if test="${order.status == 1 }">待付款</c:if>
							<c:if test="${order.status == 2 }">待发货</c:if>
							<c:if test="${order.status == 3 }">待收货</c:if>
							<c:if test="${order.status == 4 }">交易完成</c:if>
							<c:if test="${order.status == 5 }">交易已关闭</c:if>
							</font>
						</td>
						<td colspan="3" align="right">
							<div style="text-align: right;">
								商品共计：${ order.money}元   
								<c:if test="${order.logisticsMoney != '' && order.logisticsMoney != null }">
									邮费另计：${order.logisticsMoney }元
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="right">
							<div style="text-align: right;">
								<c:if test="${order.status == 1}">
									<a onclick="goOnPay()">付款&nbsp;&nbsp;&nbsp;</a>
								</c:if>
								<c:if test="${order.logisticsNo != null }">
									<a onclick="checkLogistic('${order.id}','${order.logisticsNo }')">查看物流&nbsp;&nbsp;&nbsp;</a>
								</c:if>
								<c:if test="${order.status == 3 && order.hasRefund == 2 }">
									<a onclick="certReceive('${order.id}')">确认收货&nbsp;&nbsp;&nbsp;</a>
								</c:if>
								<c:if test="${order.status == 4 || order.status == 5 || order.status == 1}">
									<a onclick="delOrder('${order.id}')">删除订单&nbsp;&nbsp;&nbsp;</a>
								</c:if>
								<c:if test="${order.status == 4 && order.hasRefund == 2}">
									<a onclick="addComment('${order.id}')">追加评价&nbsp;&nbsp;&nbsp;</a>
								</c:if>
							</div>
						</td>
					</tr>
				</table>
				<br/>
			</c:forEach>
		</c:otherwise>
	</c:choose>

<!-- 分页 -->
<div >
	<div class="divider"></div>
	<div style="float: right;padding-top: 0px;margin-top: 0px;">${page.frontPageStr}</div>
</div>

<script type="text/javascript">
	
	//查看订单详情
	function getOrderDetail(orderId){
		$.ajax({
            url:"<%=basePath%>web/member/getOrderDetail.do",
            data:{orderId:orderId},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
                lastMemberPage.push(data);
            }
        });
	}
	
	//查看退款进度
	function checkRefund(orderId, detailId, refundId){
		$.ajax({
            url:"<%=basePath%>web/member/getRefundDetail.do",
            data:{orderId:orderId, detailId:detailId, refundId:refundId},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
                lastMemberPage.push(data);
            }
        });
	}

	//查看物流信息
	function checkLogistic(orderId, logisticNo){
		lastPage = $("#listDiv").html();
		needRefresh = -1;//查看物流信息后返回不需要刷新
		$.ajax({
            url:"<%=basePath%>web/member/getLogistics.do",
            data:{logisticNo:logisticNo},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
                lastMemberPage.push(data);
            }
        });
	}
	
	//确认收货
	function certReceive(orderId){
		if(!confirm("是否确认收货？")){
   			return;
	    }
		$.ajax({
            url:"<%=basePath%>web/member/certReceive.do",
            data:{"orderId":orderId},
            dataType:"json",
            type:"post",
            success:function(data){
            	if(data.result == "success"){
	                //div加载页面
	                showList(typeId);
            	}else{
            		alert("确认失败！");
            	}
            }
        });
	}
	
	//删除订单
	function delOrder(orderId){
		if(!confirm("确认删除吗？")){
   			return;
	    }
		$.ajax({
            url:"<%=basePath%>web/member/delOrder.do",
            data:{orderId:orderId},
            dataType:"json",
            type:"post",
            success:function(data){
            	if(data.result == "success"){
	                //div加载页面
	                showList(typeId);
            	}else{
            		alert("删除失败");
            	}
            }
        });
	}
	
	//添加评论
	function addComment(orderId){
	
	}
	
	//继续付款
	function goOnPay(){
	
	}
</script>
