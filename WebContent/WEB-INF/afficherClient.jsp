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
		<li>Nom : ${client.nom }</li>
		<li>Prenom : ${client.prenom }</li>
		<li>Adresse : ${client.adress }</li>
		<li>Courriel : ${client.mail }</li>
		<li>Telephone : ${client.phone }</li>
	</ul>

</body>
</html>