<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Informations sur l'utlisateur</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="menuAdmin.jsp"/>
    	<c:import url="menuGestionUtilisateur.jsp" />
    	
        <div id="corps">
            <p class="info"> Informations sur l'utilisateur sélectionné : </p>
            	<p>Nom : <c:out value="${ utilisateur.nom }"/></p>
            	<p>Prénom : <c:out value="${ utilisateur.prenom }"/></p>
            	<p>Companie : <c:out value="${ utilisateur.company }"/></p>
            	<p>Numéro de téléphone : <c:out value="${ utilisateur.phone }"/></p>
            	<p>Email : <c:out value="${ utilisateur.email }"/></p>
            	<p>Actif : 
            		<c:choose>
					    <c:when test="${utilisateur.actif}">
					   		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
					   	</c:when>
					   	<c:otherwise>
					   		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
				    	</c:otherwise>
					</c:choose>
				</p>
				<p>Admin : 
            		<c:choose>
					    <c:when test="${utilisateur.admin}">
					   		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
					   	</c:when>
					   	<c:otherwise>
					   		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
				    	</c:otherwise>
					</c:choose>
				</p>
				
        </div>
    </body>
</html>