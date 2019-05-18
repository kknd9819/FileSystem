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
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>密  码:</td>
				<td>
				<input type="password" name="password">
				<span style="color:red">${error_msg}</span>
				</td>
			</tr>
		</table>
		
		<input type="submit" value="提交">
	</fieldset>
	</form>
</body>
</html>