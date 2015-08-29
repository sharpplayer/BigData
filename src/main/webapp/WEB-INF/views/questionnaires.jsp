<%@ include file="include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Questionnaires Management</title>
<style>
.error {
	color: #ff0000;
}
</style>
</head>
<body>
	<c:set var="buttonText">Add</c:set>
	<c:set var="actionText">
		<c:url value='/admin/questionnaires' />
	</c:set>
	<c:if test="${questionnaireForm.object.id != 0}">
		<c:set var="buttonText">Update</c:set>
		<c:set var="actionText">
			<c:url value='/admin/questionnaires/${questionnaireForm.object.id}' />
		</c:set>
	</c:if>
	<h2>Questionnaire Form</h2>
	<form:form method="POST" action="${actionText}"
		modelAttribute="questionnaireForm">
		<table>
			<tr>
				<td></td>
				<td><label for="description">Description: </label></td>
				<td><form:input path="object.description" id="description" /></td>
				<td><form:errors path="object.description" cssClass="error" /></td>
			</tr>
			<c:set var="start" value="0" />
			<c:forEach items="${questionnaireForm.object.questionText}"
				varStatus="vs">
				<c:set var="start" value="${vs.index + 1}" />
				<tr>
					<td><form:radiobutton path="filter.selectedQuestion"
							value="${vs.index}" /></td>
					<td><label for="questions[${vs.index}]">Question
							${vs.index + 1}: </label></td>
					<td><form:input path="object.questionText[${vs.index}]"
							id="questions[${vs.index}]" /></td>
					<td><form:errors path="object.questionText[${vs.index}]"
							cssClass="error" /></td>
				</tr>
			</c:forEach>
			<c:forEach var="loop" begin="${start}"
				end="${questionnaireForm.slots}">
				<tr>
					<td><form:radiobutton path="filter.selectedQuestion"
							value="${loop}" /></td>
					<td><label for="questions[${loop}]">Question ${loop + 1}:
					</label></td>
					<td><form:input path="object.questionText[${loop}]"
							id="questions[${loop}]" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td colspan="3"><input type="submit" name="action"
					value="${buttonText}" /></td>
			</tr>

			<tr>
				<td></td>
				<td><label for="filterTextQ">Questionnaire Filter: </label></td>
				<td><form:input path="filter.filterText" id="filterTextQ" /></td>
				<td><form:errors path="filter.filterText" cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td><label for="filterTextQD">Question Description Filter: </label></td>
				<td><form:input path="filter.questionFilter.descriptionFilterText"
						id="filterTextQD" /></td>
				<td><form:errors path="filter.questionFilter.descriptionFilterText"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td><label for="filterTextQn">Question Filter: </label></td>
				<td><form:input path="filter.questionFilter.filterText"
						id="filterTextQn" /></td>
				<td><form:errors path="filter.questionFilter.filterText"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3"><input type="submit" name="filter"
					value="Filter" /></td>
			</tr>

		</table>

		<h2>List of Questionnaires</h2>

		<table>
			<tr>
				<td></td>
				<td>Questionnaires</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="<c:url value='/admin/questionnaires' />">New</a></td>
			</tr>
			<c:forEach items="${questionnaires}" var="item">
				<tr>
					<td>${item.questionnaireId}</td>
					<td><a
						href="<c:url value='/admin/questionnaires/${item.questionnaireId}' />">${item.description}</a></td>
				</tr>
			</c:forEach>
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
					<td><input type="submit" name="insert${item.questionId}"
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