<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<h4 style="text-align: center;">会员基本信息</h4>
<table>
  <tr>
    <td>会员姓名：</td>
    <td>${member.memberName }</td>
  </tr>
  <tr>
    <td>会员号：</td>
    <td>${member.memberNo }</td>
  </tr>
  <tr>
    <td>性别：</td>
    <td>${member.sex == 1 ? '男' : '女'}</td>
  </tr>
  <tr>
    <td>身份证号：</td>
    <td>${member.idcardNo }</td>
  </tr>
  <tr>
    <td>电话号码：</td>
    <td>${member.phone }</td>
  </tr>
  <tr>
    <td>会员状态：</td>
    <td>${member.status == '1' ? "正常" : "已过期"}</td>
  </tr>
  <tr>
    <td>注册时间：</td>
    <td><fmt:formatDate value="${member.createTime }" pattern="yyyy-MM-dd" /></td>
  </tr>
  <tr>
    <td>会员过期日：</td>
    <td><fmt:formatDate value="${member.expireDate }" pattern="yyyy-MM-dd" /></td>
  </tr>
  <tr>
    <td>所属分公司：</td>
    <td>${member.company }</td>
  </tr>
  <tr>
    <td>通信地址：</td>
    <td>${member.address }</td>
  </tr>
  <tr>
    <td>上次登录时间：</td>
    <td>${member.lastLogin }</td>
  </tr>
</table>


<!-- 	<ul> -->
<!-- 	      <li><label>会员姓名: </label>${member.memberName }</li> -->
<!-- 	      <li> -->
<!-- 	      	<label>性别：</label> -->
<!-- 	      	${member.sex == 1 ? '男' : '女'} -->
<!-- 	      </li> -->
<!-- 	      <li><label>身份证号: </label>${member.idcardNo }</li> -->
<!-- 	      <li> -->
<!-- 	      	<label>电话号码：</label>${member.phone } -->
<!-- 	      </li> -->
<!-- 	      <li> -->
<!-- 	      	<label>会员状态：</label>${member.status == '1' ? "正常" : "已过期"} -->
<!-- 	      </li> -->
<!-- 	      <li> -->
<!-- 	      	<label>注册时间：</label>${member.createTime } -->
<!-- 	      </li> -->
<!-- 	      <li> -->
<!-- 	      	<label>会员过期日：</label>${member.expireDate } -->
<!-- 	      </li> -->
<!-- 	      <li> -->
<!-- 	      	<label>所属分公司：</label>${member.company } -->
<!-- 	      </li> -->
<!-- 	      <li> -->
<!-- 	      	<label>通信地址：</label>${member.address } -->
<!-- 	      </li> -->
<!-- 	      <li> -->
<!-- 	      	<label>上次登录时间：</label>${member.lastLogin } -->
<!-- 	      </li> -->
<!--      </ul> -->