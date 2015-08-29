<%@ include file="include.jsp" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statements Management</title>

<style>
.error {
	color: #ff0000;
}
</style>

</head>

<body>

	<c:set var="buttonText">Add</c:set>
	<c:set var="actionText">
		<c:url value='/admin/statements' />
	</c:set>
	<c:if test="${statementForm.object.statementId != 0}">
		<c:set var="buttonText">Update</c:set>
		<c:set var="actionText">
			<c:url value='/admin/statements/${statementForm.object.statementId}' />
		</c:set>
	</c:if>
	<h2>Statement Form</h2>
	<form:form method="POST" action="${actionText}"
		modelAttribute="statementForm">
		<table>
			<tr>
				<td><label for="statement">Name: </label></td>
				<td><form:input path="object.statement" id="statement" /></td>
				<td><form:errors path="object.statement" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="metaStrings">Meta: </label></td>
				<td><form:textarea path="object.metaStrings" id="metaStrings"
						rows="5" cols="100" /></td>
				<td><form:errors path="object.metaStrings" cssClass="error" /></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" name="action"
					value="${buttonText}" /></td>
			</tr>

			<tr>
				<td><label for="filterText">Filter: </label></td>
				<td><form:input path="filter.filterText" id="filterText" /></td>
				<td><form:errors path="filter.filterText" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" name="filter"
					value="Filter" /></td>
			</tr>

		</table>
	</form:form>

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
			</tr>
		</c:forEach>
	</table>


	<br />
	<br /> Go back to
	<a href="<c:url value='/' />">Home</a>
</body>
</html>