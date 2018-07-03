<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div style="padding-bottom: 10px">
	<div style="float: left;"><font style="font-weight: bold;" size="4px">订单详情</font></div>
	<div style="float: right;"><a class="button" onclick="detailBack()" id="detailBtn">返回</a></div>
</div>

<table>
	<tr>
		<td colspan="4">
			订单编号：${order.orderNo }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单创建时间：${order.createTime }
		</td>
	</tr>
	<c:forEach items="${productlist }" var="product">
		<tr>
			<td  width="170"><img src="${product.productImg }" width="120" height="100"></td>
			<td>${product.productName }</td>
			<td>数量：${product.num }</td>
			<td>
				单价：${product.price }
			</td>
			<c:choose>
				<c:when test="${product.status != null  }">
					<c:if test="${ product.status != 4 }">
						<td>
							<div style="text-align: right;">
								<a onclick="getRefundDetail('${order.id}','${product.id }','${product.refundId }')">退款中</a>
							</div>
						</td>
					</c:if>
					<c:if test="${ product.status == 4 }">
						<td>
							<div style="text-align: right;">
								<a onclick="getRefundDetail('${order.id}','${product.id }','${product.refundId }')">已退款</a>
							</div>
						</td>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${(order.status == 2 || order.status == 3)  }">
						<td>
							<div style="text-align: right;">
								<a onclick="goRefund('${order.id}','${product.id }')">退款</a>
							</div>	
						</td>
					</c:if>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
</table>
<br>
<table>
	<tr>
		<td>
			当前状态：
		</td>
		<td>
			<font color="orange">
			<c:if test="${order.status == 1 }">待付款</c:if>
			<c:if test="${order.status == 2 }">待发货</c:if>
			<c:if test="${order.status == 3 }">待收货</c:if>
			<c:if test="${order.status == 4 }">交易完成</c:if>
			<c:if test="${order.status == 5 }">交易已关闭</c:if>
			</font>
		</td>
	</tr>
	<tr>
		<td>收件人：</td>
		<td>${order.receiver }</td>
	</tr>
	<tr>
		<td>收件人电话：</td>
		<td>${order.receiverPhone }</td>
	</tr>
	<tr>
		<td>收件地址：</td>
		<td>${order.receiverAddress }</td>
	</tr>
	
	<c:if test="${(order.payType != null)  }">
		<tr>
			<td>支付方式：</td>
			<td>
				<c:if test="${order.payType == 1 }">支付宝</c:if>
				<c:if test="${order.payType == 2 }">微信</c:if>
				<c:if test="${order.payType == 3 }">银联</c:if>
			</td>
		</tr>
		<tr>
			<td>支付交易号：</td>
			<td>${order.payNo }</td>
		</tr>
		<tr>
			<td>支付时间：</td>
			<td>${order.payTime }</td>
		</tr>
		<tr>
			<td>支付总额：</td>
			<td>￥${order.payMoney + order.logisticsMoney }&nbsp;&nbsp;&nbsp;&nbsp;其中包含邮费￥${order.logisticsMoney }</td>
		</tr>
	</c:if>
	<c:if test="${order.sendTime != null }">
		<tr>
			<td>发货时间：</td>
			<td>${order.sendTime }</td>
		</tr>
		<tr>
			<td>物流单号：</td>
			<td><a onclick="viewLogistics('${order.logisticsNo}')">${order.logisticsNo }</a></td>
		</tr>
	</c:if>
	<c:if test="${order.desc != null }">
		<tr>
			<td>备注：</td>
			<td>${order.desc }</td>
		</tr>
	</c:if>
</table>

<table>

</table>

<script type="text/javascript">
	//退款申请
	function goRefund(orderId,detailId){
		$.ajax({
            url:"<%=basePath%>web/member/goRefund.do",
            data:{orderId:orderId, detailId:detailId},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
                lastMemberPage.push(data);
            }
        });
	}
	
	//退款查看
	function getRefundDetail(orderId,detailId,refundId){
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
	
	//查看物流
	function viewLogistics(no){
		$.ajax({
            url:"<%=basePath%>web/member/getLogistics.do",
            data:{logisticNo:no},
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
                lastMemberPage.push(data);
            }
        });
	}
	
	function detailBack(){
		needRefresh = -1;
		$("#detailBtn").attr("disabled", true);
		lastMemberPage.pop();
		$("#listDiv").html(lastMemberPage[lastMemberPage.length - 1]);
	}
</script>
