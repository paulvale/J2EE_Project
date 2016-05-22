<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Affichage d'une réponse</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="../admin/menuAdmin.jsp" />
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
            <p class="info">${ message }</p>
            <c:if test="${ !errors }">
            	<p>Identifiant de la réponse : <c:out value="${ answer.id }"/></p>
                <p>Intitulé de la réponse : <c:out value="${ answer.text }"/></p>
                <p>Intitulé de la question associée : <c:out value="${ answer.question.text }"/></p>
                <p>Active :
                	<c:choose>
				    	<c:when test="${mapSurveys.value.active == 'active'}">
				    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
				    	</c:when>
				    	<c:otherwise>
				    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
				    	</c:otherwise>
					</c:choose>
                </p>
                <p>Validité : <c:out value="${ answer.valide }"/></p>
                <p>Ordre d'apparition dans la question : <c:out value="${ answer.order }"/></p>
            </c:if>
        </div>
    </body>
</html>