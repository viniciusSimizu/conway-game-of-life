<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table>
	<c:forEach var="row" items="${population}">
	<tr>
		<c:forEach var="isAlive" items="${row}">
		<td class="${isAlive ? "alive" : "not-alive"}"></td>
		</c:forEach>
	</tr>
	</c:forEach>
</table>

<script>
		localStorage.setItem("population", "${populationText}");
</script>
