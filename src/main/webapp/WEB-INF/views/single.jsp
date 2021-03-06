<%@ include file="include.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Preview</title>
</head>
<body>
	<h2>Question</h2>
	<form method="POST">
		<table>
			<c:forEach items="${question.questionStatements}" varStatus="vs">
				<c:if test="${vs.index == 0 }">
					<tr>
						<td colspan="2">${vs.current.statement.statement }</td>
					</tr>
				</c:if>
				<c:if test="${vs.index > 0 }">
					<tr>
						<td>${vs.current.statement.statement }</td>
						<td><input type="radio" name="response"
							value="${vs.current.statement.statementId }"
							${vs.index == 1 ? 'checked' : '' } /></td>
					</tr>
				</c:if>
			</c:forEach>
			<sec:csrfInput />
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
	<h2>Previous Answer</h2>
	<ul>
		<c:forEach items="${answers}" var="answer">
			<li>${answer.selection.statement }
				<ul>
					<c:forEach items="${answer.factors}" var="factor">
						<li>${factor}</li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ul>

</body>
</html>