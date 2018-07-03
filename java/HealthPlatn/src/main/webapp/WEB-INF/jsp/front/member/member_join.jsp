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
		var reason = $("#message").val();
		var mail = $("#mail").val();
		if(mail != null && mail != ""){
			if(!isEmail(mail)){
				alert("邮箱格式不正确！");
				return false;
			}
		}
		$.ajax({
            url:"<%=basePath%>web/member/addMemberJoin.do",
            dataType:"json",
            data:{memberno:memberno,message:reason,mail:mail},
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
	<h4>会员加盟</h4>
		<input id="hasNewsId" type="hidden" value="${currJoin != null ? '1' : '2' }">
			<!-- 如果已经有申请记录，则显示申请状态 -->
			<c:if test="${currJoin != null }">
				<ul id="currDiv">
					<li><label>您最近一次加盟申请时间：</label>${currJoin.createTime }</li>
					<li>
						<label>申请状态：</label>
						<c:if test="${currJoin.status == 1}">
							正在申请中...
						</c:if>
						<c:if test="${currJoin.status == 2}">
							您好，欢迎加盟！
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
						<td><label>会员姓名: </label></td>
						<td><input id="memberName" type="text" value="${member.memberName }" disabled="disabled"/></td>
					</tr>
					<tr>
						<td><label>手机号: </label></td>
						<td><input id="phone" type="text" value="${member.phone }" disabled="disabled"/></td>
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
							<td><label>邮箱: </label></td>
							<td><input type="text" id="mail"/></td>
						</tr>
						<tr>
							<td><label>加盟意见： </label></td>
							<td><textarea id="message" rows="5" cols="6"></textarea></td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="text-align:center;">
									<button onclick="submitApp()">提交申请</button>
					      			<button id="canclebtn" style="display: none" onclick="cancleApp()" >取消</button>
					      		</div>
							</td>
						</tr>
					</c:if>
				    <c:if test="${member.status == '2' }">
				    	<tr>
							<td><label>会员当前状态: </label></td>
							<td>
								<input value="已过期" disabled="disabled"/>
							</td>
						</tr>
				    </c:if>
			    </table>
			</div>
			
			<br><br>
		<a onclick="getRenewHistory()">查看历史申请记录</a>
		<div id="historyDiv" style="display: none">
			<c:choose>
				<c:when test="${joinlist == null || fn:length(joinlist) == 0  }">
					<p>您暂时还没有会员加盟申请记录！</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${joinlist }" var="item">
						<div class="blog-post big animate-onscroll">
							<p>您于${item.createTime }申请了会员加盟，申请结果为：
								<c:if test="${item.status == 1}">
									正在申请中...
								</c:if>
								<c:if test="${item.status == 2}">
									已加盟！
								</c:if>
								<c:if test="${item.status == 3}">
									已过期
								</c:if>
							</p>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
	   </div>
</div>

<div id="infoDiv" style="display: none">
	<h4>加盟申请已提交，请您在三个工作日之内前来门店办理加盟手续。</h4>
</div>