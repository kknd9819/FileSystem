<%@page import="java.sql.Timestamp"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="model.User" %>
<%@page import="model.Upload" %>
<%@page import="dao.UploadDao" %>
<%@page import="java.util.List" %>
<%@page import="dao.impl.UploadDaoImpl" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.File" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%
	User user =  (User)session.getAttribute("user");
	UploadDao uploadDao = new UploadDaoImpl();
	List<Upload> list = uploadDao.findAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文件列表页</title>
<style>
	td{text-align:center;}
	span{color:red;}
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath }/upload" method="post" enctype="multipart/form-data">
		文件上传:<input type="file" id="file" name="file">
		<button type="button" id="btn">提交</button>
		<span>注：压缩包的命名叫自己的名字! 例如:张三.zip</span>
	</form>
	<!-- 获取文件列表 -->
	<table border="1" cellspacing="0" width="100%">
		<tr>
			<th>文件名</th>
			<th>上传时间</th>
			<th>上传用户</th>
		</tr>
		<%if(user.getUsername().equals("admin")){ %>
		<%for(Upload upload : list){%>
			<tr>
				<td>
					<a href="${pageContext.request.contextPath }/download?filePath=<%=URLEncoder.encode(upload.getAbsolutePath(),"utf-8")%>&fileName=<%=upload.getUuidName()%>"><%=upload.getUuidName()%></a>
				</td>
				<td><%=upload.getUploadTimeString() %></td>
				<td><%=upload.getUsername() %></td>
			</tr>
		<%} %>
		<%} else {%>
		<%for(Upload upload : list){%>
			<tr>
				<td><%=upload.getUuidName()%></td>
				<td><%=upload.getUploadTimeString() %></td>
				<td><%=upload.getUsername() %></td>
			</tr>
		<%} %>
		<%} %>
	</table>
	<script>
		var btn = document.getElementById("btn");
		btn.onclick = function(){
			var file = document.getElementById("file");
			var index = file.value.lastIndexOf(".");
			var suffix = file.value.substr(index+1);
			var arr = ["zip","rar","7z"];
			var flag = false;
			for(var i=0;i<arr.length;i++){
				if(arr[i] == suffix){
					flag = true;
					break;
				}
			}
			if(flag){ //如果是允许的后缀名则提交表单
				document.forms[0].submit();
			} else {
				alert("上传失败! 上传的文件必须是压缩包");
			}
		}
	</script>
</body>
</html>