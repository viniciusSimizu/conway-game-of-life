<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/reset.css" rel="stylesheet" type="text/css" />
	<link href="css/index.css" rel="stylesheet" type="text/css" />
	<script src="https://unpkg.com/htmx.org@1.9.10"></script>

	<title>Conway</title>
</head>
<body>

	<form id="configuration"
		hx-post="${pageContext.request.contextPath}/population"
		hx-target="#population"
		hx-swap="innerHTML">

		<tags:InputNumber description="Dimension" name="dimension" value="${setting.dimension}" id="dimension" />
		<tags:InputNumber description="Radius" name="radius" value="${setting.radius}" id="radius" />
		<tags:InputNumber description="Cells" name="cells" value="${setting.cells}" id="cells" />

		<button type="submit">New Seed</button>
	</form>

	<div id="population">
		<c:if test="${not empty state}">
			<%@ attribute name="population" value="${state}" %>
			<jsp:include page="/WEB-INF/jsp/population.jsp" />
		</c:if>
	</div>
</body>
</html>
