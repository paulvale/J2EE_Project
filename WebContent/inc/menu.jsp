<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
	<p><a href="<c:url value="/surveyList"/>">Liste des questionnaires</a></p>
	<p><a href="<c:url value="/surveyCreation"/>">Créer un nouveau questionnaire</a></p>
	<p><a href="<c:url value="/questionCreation"/>">Créer une nouvelle question</a></p>
	<p><a href="<c:url value="/answerCreation"/>">Créer un nouvelle réponse</a></p>
	<!--  
    <p><a href="<c:url value="/clientCreation"/>">Créer un nouveau client</a></p>
    <p><a href="<c:url value="/orderCreation"/>">Créer une nouvelle commande</a></p>
    <p><a href="<c:url value="/listeClients"/>">Liste des clients</a></p>
    <p><a href="<c:url value="/listeCommandes"/>">Liste des commandes</a></p>
    -->
</div>