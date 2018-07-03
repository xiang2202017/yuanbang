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
		url: "<%=basePath%>web/member/member_getChgPhoneCode",
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

	function changePhone(){
		var phone = $("#phone").val();
		var code = $("#validateCode").val();
		$.ajax({
            url:"<%=basePath%>web/member/updateMemberPhone.do",
            dataType:"json",
            data:{phone:phone, code:code},
            type:"post",
            success:function(data){
            	if("success" == data.result){
            		showSuccess();
				}else if("apperror" == data.result) {
					alert("电话修改失败");
				}
            }
        });
	}
	
	function showSuccess(){
		$("#phonediv").hide();
		$("#succDiv").show();
	}

</script>
<div id="phonediv">
	<h4>修改手机号码</h4>
	<table>
		<tr >
			<td>请输入新的手机号码：</td>
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
			<td>请输入验证码：</td>
			<td><input type="text" id="validateCode" name="validateCode"></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<p align="center"><a class="button" onclick="changePhone()">提交</a></p>
			</td>
		</tr>
	</table>
</div>
<div id="succDiv" style="display: none;">
	<h5>手机号码修改成功</h5>
</div>