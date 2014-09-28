<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>Données du client :</p>
	<ul>
		<li>Nom : ${commande.client.nom }</li>
		<li>Prenom : ${commande.client.prenom }</li>
		<li>Adresse : ${commande.client.adress }</li>
		<li>Courriel : ${commande.client.mail }</li>
		<li>Telephone : ${commande.client.phone }</li>
	</ul>
	
	<p>Données du commande :</p>
	<ul>
		<li>Date : ${commande.dateCommande }</li>
		<li>Montant : ${commande.montant }</li>
		<li>Mode de paiement : ${commande.modeDePaiement }</li>
		<li>Statu du paiement : ${commande.statutDePaiement }</li>
		<li>Mode de livraison : ${commande.modeDeLivraison }</li>
		<li>Statut de livraison : ${commande.statutDeLivraison }</li>
	</ul>

</body>
</html>