<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Questions Management</title>
</head>
<body>
	<c:set var="buttonText">Add</c:set>
	<c:set var="actionText">
		<c:url value='/admin/questions' />
	</c:set>
	<c:if test="${questionForm.object.questionId != 0}">
		<c:set var="buttonText">Update</c:set>
		<c:set var="actionText">
			<c:url value='/admin/questions/${questionForm.object.questionId}' />
		</c:set>
	</c:if>
	<h2>Question Form</h2>
	<form:form method="POST" action="${actionText}"
		modelAttribute="questionForm">
		<table>
			<tr>
				<td></td>
				<td><label for="description">Description: </label></td>
				<td><form:input path="object.descriptionText" id="description" /></td>
				<td><form:errors path="object.descriptionText" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:radiobutton path="filter.selectedAnswer" value="0" /></td>
				<td><label for="question">Question: </label></td>
				<td><form:input path="object.question" id="question" /></td>
				<td><form:errors path="object.question" cssClass="error" /></td>
			</tr>
			<c:set var="start" value="0" />
			<c:forEach items="${questionForm.object.answers}" varStatus="vs">
				<c:set var="start" value="${vs.index}" />
				<tr>
					<td><form:radiobutton path="filter.selectedAnswer"
							value="${vs.index + 1}" /></td>
					<td><label for="answers[${vs.index}]">Answer
							${vs.index + 1}: </label></td>
					<td><form:input path="object.answers[${vs.index}]"
							id="answers[${vs.index}]" /></td>
				</tr>
			</c:forEach>
			<c:forEach var="loop" begin="${start + 1}"
				end="${questionForm.slots}">
				<tr>
					<td><form:radiobutton path="filter.selectedAnswer"
							value="${loop + 1}" /></td>
					<td><label for="answers[${loop}]">Answer ${loop + 1}:
					</label></td>
					<td><form:input path="object.answers[${loop}]"
							id="answers[${loop}]" /></td>
				</tr>
			</c:forEach>
			<c:if test="${questionForm.object.questionId != 0}">
				<tr>
					<td></td>
					<td>Preview:</td>
					<td><form:select path="filter.previewMethod"
							items="${previewList}" /></td>
				</tr>
			</c:if>
			<tr>
				<td></td>
				<td colspan="3"><input type="submit" name="action"
					value="${buttonText}" /> <c:if
						test="${questionForm.object.questionId != 0}">
					
					&nbsp;<input type="submit" name="preview" value="Preview" />
					</c:if></td>
			</tr>

			<tr>
				<td></td>
				<td><label for="filterTextQD">Question Description Filter: </label></td>
				<td><form:input path="filter.descriptionFilterText"
						id="filterTextQD" /></td>
				<td><form:errors path="filter.descriptionFilterText"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td><label for="filterTextQ">Question Filter: </label></td>
				<td><form:input path="filter.filterText" id="filterTextQ" /></td>
				<td><form:errors path="filter.filterText" cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td><label for="filterTextS">Statement Filter: </label></td>
				<td><form:input path="filter.statementFilter.filterText"
						id="filterTextS" /></td>
				<td><form:errors path="filter.statementFilter.filterText"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3"><input type="submit" name="filter"
					value="Filter" /></td>
			</tr>

		</table>

		<h2>List of Questions</h2>

		<table>
			<tr>
				<td></td>
				<td>Question</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="<c:url value='/admin/questions' />">New</a></td>
			</tr>
			<c:forEach items="${questions}" var="item">
				<tr>
					<td>${item.questionId}</td>
					<td><a href="<c:url value='/admin/questions/${item.questionId}' />">${item.description}</a></td>
					<td>${item.question}</td>
				</tr>
			</c:forEach>
		</table>

		<h2>List of Statements</h2>

		<table>
			<tr>
				<td></td>
				<td>Statement</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="<c:url value='/admin/statements' />">New</a></td>
			</tr>
			<c:forEach items="${statements}" var="item">
				<tr>
					<td>${item.statementId}</td>
					<td><a href="<c:url value='/admin/statements/${item.statementId}' />">${item.statement}</a></td>
					<td><input type="submit" name="insert${item.statementId}"
						value="Insert" /></td>
				</tr>
			</c:forEach>
		</table>
	</form:form>

	<br />
	<br /> Go back to
	<a href="<c:url value='/' />">Home</a>

</body>
</html>