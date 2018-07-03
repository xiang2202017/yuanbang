<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>

<script type="text/javascript">
$(function(){
	if(clock != null){
		clearInterval(clock);
		$("#getcodebtn").attr("disabled", false);
		$("#getcodebtn").text("点击发送验证码");
		curr = 60; //重置时间  
	}
});

var curr = 60;
var clock;

var codeResult = "success";
var phoneMsg = "";

//验证码重复获取倒计时
function doTimeNum(){
	$("#getcodebtn").attr("disabled", true);
	$("#getcodebtn").text(curr + "秒后重试");
	clock = setInterval(doLoop, 1000); //一秒执行一次
}

function doLoop(){
	curr--;  
	if(curr > 0){  
		$("#getcodebtn").text(curr+'秒后重试');  
	}else{   
		clearInterval(clock); //清除js定时器   
		$("#getcodebtn").attr("disabled", false);
		$("#getcodebtn").text("点击发送验证码");
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
		url: "<%=basePath%>web/member/member_getCode",
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
			}else if("phoneerror" == data.result){
				codeResult = "fail";
				phoneMsg = "该电话号码已经注册！";
				alert(data.msg);
			}
		}
	});
}


function registerCheck(){
		if($("#newname").val() == ""){
	 		$("#newname").tips({
				side : 1,
				msg : '会员姓名不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#newname").focus();
			return false;
	 	}
		if($("#newpw").val() == ""){
			$("#newpw").tips({
				side : 1,
				msg : '密码不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#newpw").focus();
			return false;
		} 
		if($("#newpwCert").val() == ""){
			$("#newpwCert").tips({
				side : 1,
				msg : '密码确认不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#newpwCert").focus();
			return false;
		}
		if($("#newpw").val() != $("#newpwCert").val()){
			alert("两次输入的密码不一致");
			$("#newpw").focus();
			return false;
		}
		
		if($("#idcardNo").val() == ""){
			$("#idcardNo").tips({
				side : 1,
				msg : '身份证不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#idcardNo").focus();
			return false;
		}else{
			var result = isIdCardNo($("#idcardNo").val());
			if(!result){
				return false;
			}	
		} 
		if($("#phone").val() == ""){
			$("#phone").tips({
				side : 1,
				msg : '电话不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#phone").focus();
			return false;
		}else{
			var result = isMobile($("#phone").val());
			if(!result){
				$("#phone").tips({
					side : 1,
					msg : '您输入的手机号码格式有误',
					bg : '#AE81FF',
					time : 3
				});

				$("#phone").focus();
				return false;
			}
		} 
		if($("#read").is(":checked") == false){
			alert("请同意用户协议");
			return false;
		}
		return true;
	}
	
	
	//注册
	function register(){
		if(codeResult == "fail"){
			alert(phoneMsg);
			return;
		}
		if(registerCheck()){
			var newname = $("#newname").val();
			var pwd = $("#newpw").val();
			var sex = $("#sex").val();
			var idcardNo = $("#idcardNo").val();
			var phone = $("#phone").val();
			var type = $("#memberType").val();
			var company = $("#company").val();
			var address = $("#address").val();
			var code = $("#smsCode").val();
			$.ajax({
				type: "POST",
				url: "<%=basePath%>web/member/member_register",
				data: {name:newname,pwd:pwd,idcardNo:idcardNo,sex:sex,phone:phone,code:code,type:type,company:company,address:address,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						toMainPage();
					}else if("exist" == data.result){
						$("#phone").tips({
							side : 1,
							msg : "电话号码或身份证已注册",
							bg : '#FF5080',
							time : 15
						});
						$("#phone").focus();
					}else if("apperror" == data.result){
						alert("程序未知错误");
					}else if("codeerror" == data.result){
						alert("手机验证码错误");
					}else if("timeerror" == data.result){
						alert("验证码超时，请重新获取");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
		         	alert(XMLHttpRequest.status);
		       }
			});
		}
	}
	
	//登录注册完成后跳转到会员主页面
	function toMainPage(){
		$.ajax({
            url:"<%=basePath%>web/member/toMemberMain",
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
	
	//弹出用户协议窗口
	function openWin(){
		$("#registerDIV").hide();
		$("#agreementDiv").show();
			
		document.getElementsByTagName('body')[0].scrollTop = 0;
	}
	
	//关闭用户协议
	function toRegisterDiv(){
		$("#registerDIV").show();
		$("#agreementDiv").hide();
	}
	
	//返回
	function back(){
		$("#mfDiv").html(lastPage);
	}
</script>

<div id="registerDIV" style="width: 70%" class="center-block">
	<h4 align="center">会员注册</h4>
      <label for="newname">*会员姓名: </label><input type="text" id="newname" name="newMemberName" />
      
      	<label>*性别：</label>
      	<select name="sex" id="sex">
      		<option value="1">男</option>
      		<option value="2">女</option>
      	</select>
      
      <label>*身份证号: </label><input type="text" id="idcardNo" name="idcardNo" />
      <label >*密码: </label><input type="password" id="newpw" name="password" />
      <label >*密码确认: </label><input type="password" id="newpwCert" name="passwordCert" />
      	
      	
      	<label>*电话号码：</label>
      	<div class="row" style="margin: 0;padding: 0;">
	      	<div style="float:left;padding:0;"><input type="text" id="phone" name="phone" style="width: 100%"></div>
	      	<div style="float:left;text-align: right;width=258px;padding-left: 6px">
	      		<button id="getcodebtn" onclick="getcode()">获取验证码</button><label>（有效时长：5分钟）</label>
	      	</div>
     	</div>
     	<br>
     	<label>*请输入验证码：</label>
     	<input type="text" id="smsCode" name="smsCode">
     
     	<br>
     	<label>*请输入您所属的分公司：</label>
     	<input type="text" id="company" name="company">
     
     	<br>
     	<label>请输入您的通信地址：</label>
     	<input type="text" id="address" name="address">
     
     	<br><br>
     	<input id="read" name="read" type="checkbox" ><label for="read"><a onclick="openWin()">同意用户协议</a></label>
      <br>
      <p align="center"><a onclick="register()" class="button">注册</a>&nbsp;&nbsp;<a onclick="back()" class="button">取消</a></p>
     
</div>

<div id="agreementDiv" style="display: none;">
	<div style="float:right;">
		<button onclick="toRegisterDiv()">X</button>
	</div>
	<div id="htmlcontentDiv">
	 ${agreement }
	</div>
	<div style="text-align: center;">
		<button onclick="toRegisterDiv()">关闭</button>
	</div>
	<br>
</div>