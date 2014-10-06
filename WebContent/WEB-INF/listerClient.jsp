<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
	<title>Liste des clients</title>
</head>
<body>
    <c:import url="inc/menu.jsp" />
	<table>
		<thead>
			<tr><th>Index</th><th>Nom</th><th>Prenom</th><th>Adresse</th><th>Telephone</th><th>Email</th></tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${!empty sessionScope.listeClient }">
				<c:forEach items="${sessionScope.listeClient }" varStatus="status" var="liste">
					<tr>
						<td><c:out value="${status.index }" /></td>
						<td><c:out value="${liste.value.nom }" /></td>
						<td><c:out value="${liste.value.prenom }" /></td>
						<td><c:out value="${liste.value.adress }" /></td>
						<td><c:out value="${liste.value.phone }" /></td>
						<td><c:out value="${liste.value.mail }" /></td>
					</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					Il n'y a pas encore de client ! <a href="<c:url value="creationClient" />" >Créer un client ?</a>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</body>
</html>