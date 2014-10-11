<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<li>Nom : <c:out value="${client.nom }" /> </li>
		<li>Prenom : <c:out value="${client.prenom }" /></li>
		<li>Adresse : <c:out value="${client.adresse }" /></li>
		<li>Courriel : <c:out value="${client.email }" /></li>
		<li>Telephone : <c:out value="${client.telephone }" /></li>
		<c:if test="${!empty client.image }">
			<li><a href='<c:url value="/images/${client.image }" />'>Voir image</a></li>
		</c:if>
	</ul>

</body>
</html>