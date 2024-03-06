<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/reset.css" rel="stylesheet" type="text/css" />
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script src="https://unpkg.com/htmx.org@1.9.10"></script>

	<title>Conway</title>
</head>
<body>

	<div id="controllers">
		<form id="configuration"
			hx-post="${pageContext.request.contextPath}/population"
			hx-target="#population"
			hx-swap="innerHTML">

			<label id="dimension">
				Dimension:
				<input type="range" min="3" max="36" name="dimension" value="${setting.dimension}"/>
				<span>${setting.dimension}</span>
			</label>
			<label id="radius">
				Radius:
				<input type="range" min="1.5" max="20" step="0.1" name="radius" value="${setting.radius}"/>
				<span>${setting.radius}</span>
			</label>
			<label id="cells">
				Cells:
				<input type="number" name="cells" value="${setting.cells}"/>
			</label>

			<button type="submit">New Seed</button>
		</form>
		<div>
			<button id="next-tick"
				hx-on:click="nextTick()">Next Tick</button>
			<button id="auto-play">Auto Play</button>
			<button id="load-session"
				hx-post="${pageContext.request.contextPath}/population/import"
				hx-vals='js:{"populationText": localStorage.getItem("population")}'
				hx-on::after-request="drawPopulation()"
				hx-swap="none">Load</button>
		</div>
	</div>

	<div id="population">
		<c:if test="${not empty population}">
			<jsp:include page="/WEB-INF/jsp/population.jsp" />
		</c:if>
	</div>

	<script>
		var ctx = "${pageContext.request.contextPath}";
	</script>
	<script src="js/home.js" type="text/javascript"></script>

</body>
</html>
