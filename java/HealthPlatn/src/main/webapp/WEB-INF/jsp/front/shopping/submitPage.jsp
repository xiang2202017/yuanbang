<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<script src="<%=basePath %>static/front_UI/js/jquery-1.11.0.min.js"></script>

<script type="text/javascript">
alert(44);
	$(function(){
		var money = parent.document.getElementById("total").value;
		var address = parent.document.getElementById("address").value;
		alert(money);
		
		$("#totalMoney").val(money);
		$("#maddress").val(address);
		
		alert(5);
		
		$("#postData_form").attr("action",'<%=basePath %>web/member/zhifubaoPay');
		$("#postData_form").submit();
	});

</script>
</head>
<body>
	<form method="post" target="_self" id="postData_form"> 
	        <label>正在提交中，请稍等...</label>
	        <input id="totalMoney" name="totalMoney" type="hidden">
	        <input id="maddress" name="maddress" type="hidden">
	</form>
</body>
</html>
	
