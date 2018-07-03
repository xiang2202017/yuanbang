<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
$(function(){
	var hasId = $("#hasNewsId").val();
	if(hasId == "1"){
		$("#appDiv").hide();
	}else{
		$("#appDiv").show();
	}
});

	//显示申请窗口
	function showCancleApplication(){
		$("#appDiv").show();
		$("#canclebtn").show();
		$("#currDiv").hide();
	}
	
	//显示会员续约申请窗口
	function showRenewApplication(){
		toMemberRenew();
	}
	
	//隐藏申请窗口
	function cancleApp(){
		$("#appDiv").hide();
		$("#canclebtn").hide();
		$("#currDiv").show();
	}

	//解约申请提交
	function submitApp(){
		var memberno = $("#memberId").val();
		var reason = $("#reason").val();
		$.ajax({
            url:"<%=basePath%>web/member/memberCancle.do",
            dataType:"json",
            data:{memberno:memberno,reason:reason},
            type:"post",
            success:function(data){
            	if("success" == data.result){
            		showSuccess();
				}else if("apperror" == data.result) {
					alert("申请提交失败");
				}
            }
        });
	}
	
	function getRenewHistory(){
		$("#historyDiv").toggle();
	}
	
	function showSuccess(){
		$("#chgDiv").hide();
		$("#infoDiv").show();
	}
</script>

<div id="chgDiv">
	<h4>会员解约</h4>
		<input id="hasNewsId" type="hidden" value="${cancel != null ? '1' : '2' }">
			<!-- 如果已经有申请记录，则显示申请状态 -->
			<c:if test="${cancel != null }">
				<ul id="currDiv">
					<li><label>您最近一次解约申请时间：</label>${cancel.createTime }</li>
					<li>
						<label>申请状态：</label>
						<c:if test="${cancel.status == 1}">
							正在申请中...
						</c:if>
						<c:if test="${cancel.status == 2}">
							您好，申请已通过！请您前往门店办理解约手续！
						</c:if>
						<c:if test="${cancel.status == 3}">
							您的申请失败！<a onclick="showCancleApplication()">点击此处</a>重新申请解约
						</c:if>
						<c:if test="${cancel.status == 4}">
							您的会员已解约，过期时间为：${member.expireDate }！
						</c:if>
						<c:if test="${member.status == '2'}">
							您的经销会员身份已过有效期，<a onclick="showRenewApplication()">点击此处</a>重新申请续约
						</c:if>
					</li>
				</ul>
			</c:if>
			<!-- 如果还没有申请记录 -->
			<div id="appDiv" style="display: none;" >
				<table>
					<tr>
						<td><label>您的会员号: </label></td>
						<td><input id="memberId" type="text" value="${member.memberNo }" disabled="disabled"/></td>
					</tr>
					<tr>
						<td><label>会员有效期截止: </label></td>
						<td><fmt:formatDate value="${member.expireDate }" pattern="yyyy-MM-dd" /></td>
					</tr>
					<c:if test="${member.status == '1' }">
						<tr>
							<td><label>会员当前状态: </label></td>
							<td><input type="text" value="正常" disabled="disabled"/></td>
						</tr>
						<tr>
							<td><label>解约原因：</label></td>
							<td><textarea id="reason" rows="5" cols="6"></textarea></td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="text-align: center;">
									<button value="提交申请"  onclick="submitApp()">提交申请</button>
					      			<button id="canclebtn" style="display: none" onclick="cancleApp()" >取消</button>
								</div>
				      		</td>
						</tr>
					</c:if>
					<c:if test="${member.status == '2' }">
						<tr>
							<td><label>会员当前状态: </label></td>
							<td><input type="text" value="已过期" disabled="disabled"/></td>
						</tr>
					</c:if>
			    </table>
			</div>
		<a onclick="getRenewHistory()">查看历史申请记录</a>
		<div id="historyDiv" style="display: none">
			<c:choose>
				<c:when test="${cancellist == null || fn:length(cancellist) == 0  }">
					<p>您暂时还没有会员解约申请记录！</p>
				</c:when>
				<c:otherwise>
					<div class="blog-post big animate-onscroll">
					<c:forEach items="${cancellist }" var="item">
							<p>您于${item.createTime }申请了会员解约，申请结果为：
								<c:if test="${item.status == 1}">
									正在申请中...
								</c:if>
								<c:if test="${item.status == 2}">
									申请通过！
								</c:if>
								<c:if test="${item.status == 3}">
									申请失败
								</c:if>
								<c:if test="${item.status == 4}">
									已解约
								</c:if>
								<c:if test="${item.status == 5}">
									已过期
								</c:if>
							</p>
					</c:forEach>
				</div>
				</c:otherwise>
			</c:choose>
	   </div>
</div>

<div id="infoDiv" style="display: none">
	<h4>解约申请已提交</h4>
</div>