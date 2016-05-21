<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta charset="utf-8" />
        <title>Affichage de votre profil administrateur</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>	
	<body>
		<c:import url="menuUtilisateur.jsp"/>
        <div id="corps">
            <p class="info"> Votre profil : </p>
            <%-- Vérification de la présence d'un objet utilisateur en session --%>
            <c:if test="${!empty utilisateurSession}">
            	<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            	<p>Nom : <c:out value="${ utilisateurSession.nom }"/></p>
            	<p>Prénom : <c:out value="${ utilisateurSession.prenom }"/></p>
            	<p>Companie : <c:out value="${ utilisateurSession.company }"/></p>
            	<p>Numéro de téléphone : <c:out value="${ utilisateurSession.phone }"/></p>
            	<p>Email : <c:out value="${ utilisateurSession.email }"/></p>
            </c:if>
        </div>
	</body>
</html>