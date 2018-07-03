<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">

	$(function(){
		
	});
	
	//跳转到会员增加页面
	function toAddAddress(){
		$("#addlistDiv").hide();
		$("#addDiv").show();
		$("#chgDiv").hide();
	}
	
	//跳转到会员地址修改页面
	function toEditAddress(id, name, phone, address, postCode, isDefault){
		$("#addlistDiv").hide();
		$("#addDiv").hide();
		$("#chgDiv").show();
		
		$("#name").val(name);
		$("#phone").val(phone);
		$("#address").val(address);
		$("#postCode").val(postCode);
		$("#addressId").val(id);
		
		if(isDefault == '1'){
			$("#defaultCheck").attr("checked", true);
		}else{
			$("#defaultCheck").attr("checked", false);
		}
	}
	
	//删除收件地址
	function delAddress(id){
		$.ajax({
			url: '<%=basePath%>web/member/delAddress',
			data: {id:id},
			dataType: "json",
			type:"post",
			success : function(data){
				if(data.result == "success"){
					toMemberAddress();
				}else if(data.result == "fail"){
					alert("删除失败");
				} 
			} 
		});
	}
	
	//编辑地址
	function editAddress(){
		if(!checkC()) return;
		var name = $("#name").val();
		var phone = $("#phone").val();
		var address = $("#address").val();
		var postCode = $("#postCode").val();
		var id = $("#addressId").val();
		var isDefault = $("#defaultCheck").is(":checked") == true ? '1' : '2';
		$.ajax({
			url: '<%=basePath%>web/member/chgAddress',
			data: {id:id, name:name, phone:phone, address:address, postCode:postCode, isDefault:isDefault},
			dataType: "json",
			type:"post",
			success : function(data){
				if(data.result == "success"){
					toMemberAddress();
				}else if(data.result == "fail"){
					alert("修改失败");
				} 
			} 
		});
	}
	
	//新增地址
	function addAddress(){
		if(!checkA()) return;
		var name = $("#nameA").val();
		var phone = $("#phoneA").val();
		var address = $("#addressA").val();
		var postCode = $("#postCodeA").val();
		var id = $("#addressIdA").val();
		var isDefault = $("#defaultCheckA").is(":checked") == true ? '1' : '2';
		$.ajax({
			url: '<%=basePath%>web/member/addAddress',
			data: {name:name, phone:phone, address:address, postCode:postCode, isDefault:isDefault},
			dataType: "json",
			type:"post",
			success : function(data){
				if(data.result == "success"){
					toMemberAddress();
				}else if(data.result == "fail"){
					alert("添加失败");
				} 
			} 
		});
	}
	
	//增加检查
	function checkA(){
		if($("#nameA").val() == ""){
			alert("姓名不能为空");
			return false;
		}
		var phone = $("#phoneA").val();
		if(phone == ""){
			alert("电话号码不能为空");
			return false;
		}
		var result = isMobile(phone);
		if(!result){
			alert("您输入的手机号码格式不正确");
			return false;
		}
		if($("#addressA").val() == ""){
			alert("地址不能为空");
			return false;
		}
		if($("#postCodeA").val() != ""){
			result = isZipCode($("#postCodeA").val());
			if(!result){
				alert("您输入的邮编格式不正确");
				return false;
			}
		}
		return true;
	}
	
	//修改检查
	function checkC(){
		if($("#name").val() == ""){
			alert("姓名不能为空");
			return false;
		}
		var phone = $("#phone").val();
		if(phone == ""){
			alert("电话号码不能为空");
			return false;
		}
		var result = isMobile(phone);
		if(!result){
			alert("您输入的手机号码格式不正确");
			return false;
		}
		if($("#address").val() == ""){
			alert("地址不能为空");
			return false;
		}
		if($("#postCode").val() != ""){
			result = isZipCode($("#postCode").val());
			if(!result){
				alert("您输入的邮编格式不正确");
				return false;
			}
		}
		return true;
	}
	
	function cancleFunc(){
		$("#addlistDiv").show();
		$("#addDiv").hide();
		$("#chgDiv").hide();
		
		$("#nameA,#phoneA,#addressA,#postCodeA").val("");
		$("#defaultCheckA").attr("selected", false);
	}
	
	

</script>

<div id="addlistDiv">
	<h4>收货地址管理</h4>
	
	<div style="text-align: right;"><button onclick="toAddAddress()">添加新地址</button></div>
	<table>
		<c:forEach items="${addressList }" var="item">
			<tr>
				<td>
					${item.name }
					<span style="padding-left: 50px">${item.phone }</span>
					
				<br><br>
					${item.address }<span style="padding-left: 50px">${item.postCode }</span>
				<br><br>
					<c:if test="${item.isDefault == '1' }">
						<div style="float: left;vertical-align: middle;">
				    		<input id="ck" type="checkbox" disabled="disabled" checked="checked" ><label for="ck">默认地址</label>
						</div>
			    	</c:if>
<!-- 			    	<c:if test="${item.isDefault == '2' }"> -->
<!-- 			    		<input id="ck" type="checkbox" disabled="disabled" ><label for="ck">默认地址</label> -->
<!-- 			    	</c:if> -->
					<div style="text-align: right">
						<button onclick="toEditAddress('${item.id}', '${item.name }','${item.phone}','${item.address}','${item.postCode}','${item.isDefault }')">编辑</button>
			    		<button onclick="delAddress('${item.id}')">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	
</div>

<div id="chgDiv" style="display: none;">
	<h4>收货地址修改</h4>
	<input type="hidden" id="addressId"/>
	<table>
		<tr>
			<td><label>收件人：</label></td>
			<td><input id="name" type="text"></td> 
		</tr>
		<tr>
			<td><label>手机号码：</label></td>
			<td><input id="phone" type="text"></td>
		</tr>
		<tr>
		    <td><label>地址：</label></td><td><input id="address" type="text"></td> 
		</tr>
		<tr>
		    <td>邮政编码：</td><td><input type="text" id="postCode"></td>
		</tr>
	    <tr>
		    <td colspan="2">
		    	<div style="text-align: center;">
			    	<input id="defaultCheck" type="checkbox" ><label for="defaultCheck">设为默认地址</label>
			    	<span style="padding-left:30px;"></span>
			    	<button onclick="editAddress()">保存</button>
			    	<button onclick="cancleFunc()">取消</button>
		    	</div>
		    </td>
	    </tr>
	</table>
</div>

<div id="addDiv" style="display: none;">
	<h4>添加新地址</h4>
	<table>
		<tr>
			<td><label>收件人：</label></td>
			<td><input id="nameA" type="text"></td>
		</tr>
		<tr>
			<td><label>手机号码：</label></td>
			<td><input id="phoneA" type="text"></td>
		</tr>
		<tr>
			<td><label>地址：</label></td>
			<td><input id="addressA" type="text"></td>
		</tr>
		<tr>
			<td><label>邮政编码：</label></td>
			<td><input type="text" id="postCodeA" ></td>
		</tr>
	    
	    <tr>
		    <td colspan="2" valign="middle">
		    	<div style="text-align: center;">
			    	<input id="defaultCheckA" name="defaultCheckA" type="checkbox" ><label for="defaultCheckA">设为默认地址</label>
			    	<span style="padding-left:30px;"></span>
			    	<button onclick="addAddress()">保存</button>
			    	<button onclick="cancleFunc()">取消</button>
	    		</div>
		    </td>
	    </tr>
	</table>
</div>