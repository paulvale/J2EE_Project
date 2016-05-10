<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Affichage d'un questionnaire</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
            <p class="info">${ message }</p>
            <c:if test="${ !errors }">
            	<p>Identifiant : <c:out value="${ survey.id }"/></p>
                <p>Sujet : <c:out value="${ survey.subject }"/></p>
                <p>Statut : <c:out value="${ survey.active }"/></p>
                <p>Date de création : <joda:format value="${ survey.creationDate }" pattern="dd/MM/yyyy HH:mm:ss"/></p>
            </c:if>
        </div>
    </body>
</html>