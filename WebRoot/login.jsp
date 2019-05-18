<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系统登录</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/login" method="post">
	<fieldset>
		<legend>用户登陆</legend>
		用户名:<input type="text" name="username"><br>
		密 码:<input type="password" name="password"><br>
		<span style="color:red">${error_msg}</span><br>
		<input type="submit" value="提交">
	</fieldset>
	</form>
</body>
</html>