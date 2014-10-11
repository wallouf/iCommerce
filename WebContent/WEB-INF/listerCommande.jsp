<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css" />" />
	<title>Liste des commandes</title>
</head>
<body>
    <c:import url="inc/menu.jsp" />
	<c:choose>
		<c:when test="${!empty sessionScope.listeCommande }">
		<table>
			<thead>
				<tr>
					<th>Index</th>
					<th>Nom</th>
					<th>Date</th>
					<th>Montant</th>
					<th>Mode de paiement</th>
					<th>Statut de paiement</th>
					<th>Mode de livraison</th>
					<th>Statut de livraison</th>
					<th>?</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${sessionScope.listeCommande }" varStatus="status" var="liste">
				<c:if test="${!empty liste.value }">
				<tr>
					<td><c:out value="${liste.key }" /></td>
					<td><c:out value="${liste.value.client.nom }" /></td>
					<td><joda:format value="${ liste.value.date }" pattern="dd/MM/yyyy HH:mm:ss"/></td>
					<td><c:out value="${liste.value.montant }" /></td>
					<td><c:out value="${liste.value.modeDePaiement }" /></td>
					<td><c:out value="${liste.value.statutDePaiement }" /></td>
					<td><c:out value="${liste.value.modeDeLivraison }" /></td>
					<td><c:out value="${liste.value.statutDeLivraison }" /></td>
					<td><a href='<c:url value="supprimerCommande" ><c:param name="idCommande" value="${liste.value.id }"/></c:url>'>Supprimer ?</a></td>
				</tr>
				</c:if>
			</c:forEach>
			</tbody>
		</table>
		</c:when>
		<c:otherwise>
			Il n'y a pas encore de commande ! <a href="<c:url value="creationCommande" />" >Créer une commande ?</a>
		</c:otherwise>
	</c:choose>
</body>
</html>