<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Login Form</title>
</head>
<body>
	<h2>Login Here</h2>


	<div
		style="text-align: center; padding: 30px; border: 1px solid green; width: 250px;">
		<c:url var="actionUrl" value='/login' />
		<c:if test="${param.error != null }">
			<p>Invalid username or password</p>
		</c:if>
		<form:form id="loginForm" method="post" action="${actionUrl}"
			modelAttribute="user" role="form">

			<table>
				<tr>
					<td colspan="2" style="color: red">${message}</td>

				</tr>
				<tr>
					<td>User Name:</td>
					<td><input id="username" type="text" name="username" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input id="password" type="password" name="password" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Login" /></td>

				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>
