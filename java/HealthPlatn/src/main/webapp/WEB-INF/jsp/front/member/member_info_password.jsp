<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	function checkPw(){
		var oripwd = $("#origPw").val();
		var newpwd = $("#newpw").val();
		var newpwdcer = $("#newpwCert").val();
		if(oripwd == ""){
			alert("原密码不能为空");
			return false;
		}
		if(newpwd == "" || newpwdcer == ""){
			alert("新密码不能为空");
			return false;
		}
		if(newpwd != newpwdcer){
			alert("两次输入的密码不一致");
			return false;
		}
		if(oripwd == newpwd){
			alert("新密码与原始密码一致");
			return false;
		}
		return true;
	}

	function resetPassword(){
		if(checkPw()){
			var oripwd = $("#origPw").val();
			var newpwd = $("#newpw").val();
			$.ajax({
	            url:"<%=basePath%>web/member/updateMemberPassword.do",
	            dataType:"json",
	            data:{idoripwd:oripwd, newpwd:newpwd},
	            type:"post",
	            success:function(data){
	            	if("success" == data.result){
	            		showSuccess();
					}else if("pwderror" == data.result){
						$("#origPw").tips({
							side : 1,
							msg : '您输入的原始密码不对',
							bg : '#AE81FF',
							time : 3
						});
						$("#origPw").focus();
					}else if("apperror" == data.result) {
						alert("密码修改失败");
					}
	            }
	        });
		}
	}
	
	//跳转到成功页面
	function showSuccess(){
		$("#chgDiv").hide();
		$("#infoDiv").show();
	}
	
</script>

<div id="chgDiv">
	<h4>密码修改</h4>
	<table>
		<tr>
			<td>请输入原密码:</td>
			<td><input type="password" id="origPw" /></td>
		</tr>
		<tr>
			<td>请输入新密码:</td>
			<td><input type="password" id="newpw" name="password" /></td>
		</tr>
		<tr>
			<td>请输入新密码:</td>
			<td><input type="password" id="newpwCert" name="passwordCert" /></td>
		</tr>
		<tr >
			<td colspan="2" align="center" >
				<div style="text-align: center"><button onclick="resetPassword()">提交</button></div>
			</td>
		</tr>
	</table>
<!-- 	<ul> -->
<!-- 	      <li><label>请输入原密码: </label><input type="password" id="origPw" /></li> -->
<!-- 	      <li><label >请输入新密码: </label><input type="password" id="newpw" name="password" /></li> -->
<!-- 	      <li><label >密码确认: </label><input type="password" id="newpwCert" name="passwordCert" /></li> -->
<!-- 	      <li> -->
<!-- 	      </li> -->
<!--      </ul> -->
</div>

<div id="infoDiv" style="display: none">
	<h4>密码修改成功</h4>
</div>