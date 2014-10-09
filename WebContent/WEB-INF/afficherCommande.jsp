<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
<title>Insert title here</title>
</head>
<body>
    <c:import url="inc/menu.jsp" />
    <strong><c:out value="${message }"></c:out></strong><br/>
	<p>Données du client :</p>
	<ul>
		<li>Nom : <c:out value="${commande.client.nom }" /></li>
		<li>Prenom : <c:out value="${commande.client.prenom }" /></li>
		<li>Adresse : <c:out value="${commande.client.adresse }" /></li>
		<li>Courriel : <c:out value="${commande.client.email }" /></li>
		<li>Telephone : <c:out value="${commande.client.telephone }" /></li>
		<c:if test="${!empty client.image }">
			<li><a href='<c:url value="/images/${commande.client.image }" />'>Voir image</a></li>
		</c:if>
	</ul>
	
	<p>Données du commande :</p>
	<ul>
		<li>Date : <c:out value="${commande.dateCommande }" /></li>
		<li>Montant : <c:out value="${commande.montant }" /></li>
		<li>Mode de paiement : <c:out value="${commande.modeDePaiement }" /></li>
		<li>Statu du paiement : <c:out value="${commande.statutDePaiement }" /></li>
		<li>Mode de livraison : <c:out value="${commande.modeDeLivraison }" /></li>
		<li>Statut de livraison : <c:out value="${commande.statutDeLivraison }" /></li>
	</ul>

</body>
</html>