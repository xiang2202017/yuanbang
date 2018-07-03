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
	function showRenewApplication(){
		$("#appDiv").show();
		$("#canclebtn").show();
		$("#currDiv").hide();
	}
	
	//隐藏申请窗口
	function cancleApp(){
		$("#appDiv").hide();
		$("#canclebtn").hide();
		$("#currDiv").show();
	}

	//续约申请提交
	function submitApp(){
		var memberno = $("#memberId").val();
		var term = $("#requestTerm").val();
		$.ajax({
            url:"<%=basePath%>web/member/memberRenew.do",
            dataType:"json",
            data:{memberno:memberno, term:term},
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
	<h4>会员续约</h4>
		<input id="hasNewsId" type="hidden" value="${renew != null && renew.status != 5 ? '1' : '2' }">
			<!-- 如果已经有申请记录，则显示申请状态 -->
			<c:if test="${renew != null }">
				<ul id="currDiv">
					<li><label>您最近一次续约申请时间：</label>${renew.createTime }</li>
					<li>
						<label>申请状态：</label>
						<c:if test="${renew.status == 1}">
							正在申请中...
						</c:if>
						<c:if test="${renew.status == 2}">
							恭喜您，申请已通过！请您尽快到门店办理续约手续！
						</c:if>
						<c:if test="${renew.status == 3}">
							您的申请失败！<a onclick="showRenewApplication()">点击此处</a>重新申请续约
						</c:if>
						<c:if test="${renew.status == 4}">
							已签约！<br>
							签约时间：${renew.dealTime }<br>
							签约时长：${renew.requestTerm }年<br>
							会员有限期至：${member.expireDate }
						</c:if>
						<c:if test="${renew.status == 5}">
							您提交的申请已过期，<a onclick="showRenewApplication()">点击此处</a>重新申请续约
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
					<tr>
						<td><label>会员当前状态: </label></td>
						<td><input type="text" value="${member.status == '1' ? '正常' : '已过期' }" disabled="disabled"/></td>
					</tr>
					<tr>
						<td><label >请选择申请时长: </label></td>
						<td>
							<select id="requestTerm">
				      			<option value="1" selected="selected">一年</option>
				      			<option value="2">两年</option>
				      			<option value="3">三年</option>
				      		</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div style="text-align:center">
								<button onclick="submitApp()">提交申请</button>
								<button id="canclebtn" style="display: none" onclick="cancleApp()" >取消</button>
							</div>
						</td>
					</tr>
				</table>
			</div>
		<a onclick="getRenewHistory()">查看历史申请记录</a>
		<div id="historyDiv" style="display: none">
			<c:choose>
				<c:when test="${renewlist == null || fn:length(renewlist) == 0  }">
					<p>您暂时还没有会员续约申请记录！</p>
				</c:when>
				<c:otherwise>
					<div class="blog-post big animate-onscroll">
					<c:forEach items="${renewlist }" var="item">
							<p>您于${item.createTime }申请了会员续约，申请结果为：
								<c:if test="${item.status == 1}">
									正在申请中...
								</c:if>
								<c:if test="${item.status == 2}">
									已通过！
								</c:if>
								<c:if test="${item.status == 3}">
									失败
								</c:if>
								<c:if test="${item.status == 4}">
									已签约
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
	<h4>续约申请已提交</h4>
</div>