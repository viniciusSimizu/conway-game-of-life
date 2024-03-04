<%@ attribute name="id" required="true" %>
<%@ attribute name="description" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="value" required="false" %>

<label>
	${description}:
	<input id="${id}" type="number" name="${name}" value="${value}"/>
</label>
