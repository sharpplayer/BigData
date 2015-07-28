<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statements Form</title>

<style>
.error {
	color: #ff0000;
}
</style>

</head>

<body>

	<c:set var="buttonText">Add</c:set>
	<c:set var="actionText">
		<c:url value='/html/statements' />
	</c:set>
	<c:if test="${statementForm.object.id != 0}">
		<c:set var="buttonText">Update</c:set>
		<c:set var="actionText">
			<c:url value='/html/statements/${statementForm.object.id}' />
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
			<td><a href="<c:url value='/html/statements' />">New</a></td>
		</tr>
		<c:forEach items="${statements}" var="item">
			<tr>
				<td>${item.id}</td>
				<td><a href="<c:url value='/html/statements/${item.id}' />">${item.statement}</a></td>
			</tr>
		</c:forEach>
	</table>


	<br />
	<br /> Go back to
	<a href="<c:url value='/' />">Home</a>
</body>
</html>