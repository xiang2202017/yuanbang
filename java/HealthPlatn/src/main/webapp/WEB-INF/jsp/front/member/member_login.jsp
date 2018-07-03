<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>

<script type="text/javascript">
	
	//注册登录界面控制
// 	function toRegister(){
// 		$("#loginDIV,#registerDIV").toggle();
// 	}

	//登录
	function login(){
		if(loginCheck()){
			var mno = $("#mno").val();
			var pwd = $("#mpw").val();
			$.ajax({
				type: "POST",
				url: "<%=basePath%>web/member/member_login",
				data: {no:mno,pwd:pwd,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						toMainPage();
					}else if("usererror" == data.result){
						$("#mno").tips({
							side : 1,
							msg : "用户名或密码有误",
							bg : '#FF5080',
							time : 15
						});
						$("#mno").focus();
					}else if("apperror" == data.result){
						alert("程序发生未知错误");
					}else if(data.result == "roleerror"){
						alert("用户验证失败");
					}
				}
			});
		}
	}
	
	
	function loginCheck(){
	 	if($("#mno").val() == ""){
	 		$("#mno").tips({
				side : 1,
				msg : '会员号不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#mno").focus();
			return false;
	 	}
		if($("#mpw").val() == ""){
			$("#mpw").tips({
				side : 1,
				msg : '密码不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#mpw").focus();
			return false;
		}
		return true; 
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
	
	function openRegister(){
		$.ajax({
            url:"<%=basePath%>web/member/to_member_register",
            dataType:"html",
            data: {tm:new Date().getTime()},
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
	
	//忘记密码
	function forgetPwd(){
		$.ajax({
            url:"<%=basePath%>web/member/to_member_forgetPwd",
            dataType:"html",
            data: {tm:new Date().getTime()},
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
</script>
<style>  
.col-center-block {  
    float: none;  
    display: block;  
    margin-left: auto;  
    margin-right: auto;  
}  
</style>
<div class="row" style="margin: 0;padding: 5px; border-right:1px solid #ddd;border-left:1px solid #ddd; background-color: #fafbfd ">
	<div class="col-sm-5" style="border:1px solid #ddd;margin: 0;padding: 0">
		<img alt="" src="<%=basePath %>static/front_UI/img/timg.jpg" height="500px" width="100%">
	</div>	
	
	<div class="col-sm-7 " >
	
		<div style="margin:0 auto;width: 60%" class="center-block">
			<h5 align="center">会员登录</h5>
			<br><br>
			
			<div ><span>会员号:</span> <input id="mno" name="mno" type="text" width="45" placeholder="请输入会员号" value=""></div>
			<br>
			<div><span>密码: <input type="password" id="mpw" name="password" placeholder="请输入密码" value=""></span></div>
			<br><br>
			<div align="center">
				<a href="#" class="button" onclick="login()">提交</a>
				<a onclick="openRegister()">注册</a>
				<div align="right" style="font-size: 10px">
					<a onclick="forgetPwd()">忘记密码</a>
				</div>
			</div>
		</div>
		
	</div>
</div>

