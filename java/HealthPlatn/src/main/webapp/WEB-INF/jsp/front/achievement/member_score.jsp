<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table>
  <tr>
    <td>会员姓名：</td>
    <td>${member.memberName }</td>
  </tr>
  <tr>
    <td>会员号：</td>
    <td>${member.memberNo}</td>
  </tr>
  <tr>
    <td>会员状态：</td>
    <td>${member.status == '1' ? "正常" : "已过期"}</td>
  </tr>
  <tr>
    <td>当前积分：</td>
    <td><font color="orange" size="5">${member.score }</font></td>
  </tr>
  <tr>
    <td>累计消费：</td>
    <td><font color="orange" size="5">${member.totalConsume }</font></td>
  </tr>
</table>
