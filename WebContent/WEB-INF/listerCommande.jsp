<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des commandes</title>
</head>
<body>
    <c:import url="inc/menu.jsp" />
	<table>
		<thead>
			<tr><th>Index</th><th>Nom</th><th>Prenom</th><th>Adresse</th><th>Telephone</th><th>Email</th></tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${!empty sessionScope.listeCommande }">
					<c:forEach items="${sessionScope.listeCommande }" varStatus="status" var="liste">
						<tr>
							<td><c:out value="${status.index }" /></td>
							<td><c:out value="${liste.value.date }" /></td>
							<td><c:out value="${liste.value.montant }" /></td>
							<td><c:out value="${liste.value.modeDePaiement }" /></td>
							<td><c:out value="${liste.value.statutDePaiement }" /></td>
							<td><c:out value="${liste.value.modeDeLivraison }" /></td>
							<td><c:out value="${liste.value.statutDeLivraison }" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					Il n'y a pas encore de commande ! <a href="<c:url value="creationCommande" />" >Créer une commande ?</a>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</body>
</html>