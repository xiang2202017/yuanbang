<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
$(function(){
	if(clock != null){
		clearInterval(clock);
		$("#codebtn").attr("disabled", false);
		$("#codebtn").text("点击发送验证码");
		curr = 60; //重置时间  
	}
});

var curr = 60;
var clock;

var codeResult = "success";
var phoneMsg = "";

//验证码重复获取倒计时
function doTimeNum(){
	$("#codebtn").attr("disabled", true);
	$("#codebtn").text(curr + "秒后重试");
	clock = setInterval(doLoop, 1000); //一秒执行一次
}

function doLoop(){
	curr--;  
	if(curr > 0){  
		$("#codebtn").text(curr+'秒后重试');  
	}else{   
		clearInterval(clock); //清除js定时器   
		$("#codebtn").attr("disabled", false);
		$("#codebtn").text("点击发送验证码");
		curr = 60; //重置时间  
	} 
}

//获取短信验证码
function getcode(){
	var phone = $("#phone").val();
	if(phone == ""){
		alert("请输入电话号码");
		return false;
	}
	var result = isMobile(phone);
	if(!result){
		alert("您输入的手机号码格式有误");
		return false;
	}
	
	doTimeNum();
	
	$.ajax({
		type: "POST",
		url: "<%=basePath%>web/member/member_getForgetPwdCode",
		data:{phone:phone},
		dataType:'json',
		cache: false,
		success: function(data){
			if("success" == data.result){
				codeResult = "success";
				alert("短信验证码发送成功");
			}else if("error" == data.result){
				var msg = data.msg;
				codeResult = "fail";
				alert(msg);
			}else if("numerror" == data.result){
				codeResult = "fail";
				phoneMsg = "已经达到今天验证码的最大发送次数";
				alert(data.msg);
			}
		}
	});
}

	function checkPw(){
		var newpwd = $("#newpw").val();
		var newpwdcer = $("#newpwCert").val();
		if(newpwd == "" || newpwdcer == ""){
			alert("新密码不能为空");
			return false;
		}
		if(newpwd != newpwdcer){
			alert("两次输入的密码不一致");
			return false;
		}
		return true;
	}

	function changePwd(){
		if(codeResult == "fail"){
			alert(phoneMsg);
			return;
		}
		if(checkPw()){
			var phone = $("#phone").val();
			var code = $("#validateCode").val();
			var newpw = $("#newpw").val();
			$.ajax({
	            url:"<%=basePath%>web/member/updateMemberForgetPwd.do",
	            dataType:"json",
	            data:{phone:phone, code:code, newPwd:newpw},
	            type:"post",
	            success:function(data){
	            	if("success" == data.result){
	            		showSuccess();
					}else if("apperror" == data.result) {
						alert("密码重设失败");
					}
	            }
	        });
	    }
	}
	
	function showSuccess(){
		$("#phonediv").hide();
		$("#succDiv").show();
	}
	
	//返回
	function back(){
		$("#mfDiv").html(lastPage);
	}

</script>
<div id="phonediv">
	<h4>忘记密码</h4>
	<table>
		<tr >
			<td align="right">请输入手机号码：</td>
			<td>
				<div>
					<div style="float: left;">
						<input type="text" id="phone" name="phone" />
					</div>
					<div style="float: left;padding-left:20px">
						<button id="codebtn" onclick="getcode()">获取验证码</button>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right">请输入验证码：</td>
			<td><input type="text" id="validateCode" name="validateCode"></td>
		</tr>
		<tr>
			<td align="right">请输入新密码:</td>
			<td><input type="password" id="newpw" name="password"/></td>
		</tr>
		<tr>
			<td align="right">请输入新密码:</td>
			<td><input type="password" id="newpwCert" name="passwordCert"/></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<p align="center"><a class="button" onclick="changePwd()">提交</a>&nbsp;&nbsp;<a onclick="back()" class="button">取消</a></p>
			</td>
		</tr>
	</table>
</div>
<div id="succDiv" style="display: none;">
	<h5>新密码设置成功</h5><br>
	<a onclick="back()">重新登录</a>
</div>