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
	<c:choose>
		<c:when test="${!empty sessionScope.listeClient }">
		<table>
			<thead>
				<tr>
					<th>Index</th>
					<th>Nom</th>
					<th>Prenom</th>
					<th>Adresse</th>
					<th>Telephone</th>
					<th>Email</th>
					<th>Image</th>
					<th>?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sessionScope.listeClient }" varStatus="status" var="liste">
					<c:if test="${!empty liste.value }">
					<tr>
						<td><c:out value="${status.index }" /></td>
						<td><c:out value="${liste.value.nom }" /></td>
						<td><c:out value="${liste.value.prenom }" /></td>
						<td><c:out value="${liste.value.adresse }" /></td>
						<td><c:out value="${liste.value.telephone }" /></td>
						<td><c:out value="${liste.value.email }" /></td>
						<c:choose>
							<c:when test="${!empty liste.value.image }">
								<td><a href='<c:url value="/images/${liste.value.image }" />'>Voir image</a></td>
							</c:when>
							<c:otherwise><td>No image available.</td></c:otherwise>
						</c:choose>
						<td><a href='<c:url value="supprimerClient" ><c:param name="nomClient" value="${liste.value.nom }"/></c:url>'>Supprimer ?</a></td>
					</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		</c:when>
		<c:otherwise>
			Il n'y a pas encore de client ! <a href="<c:url value="creationClient" />" >Créer un client ?</a>
		</c:otherwise>
	</c:choose>
</body>
</html>