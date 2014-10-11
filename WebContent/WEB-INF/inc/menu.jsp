<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
	<legend>Menu:</legend>
	<ul>
		<li><a href="<c:url value="creationClient" />">Créer un client</a></li>
		<li><a href="<c:url value="creationCommande" />">Créer une commande</a></li>
		<li><a href="<c:url value="listerClient" />">Lister les clients</a></li>
		<li><a href="<c:url value="listerCommande" />">Lister les commandes</a></li>
	</ul>
</fieldset>
<br />