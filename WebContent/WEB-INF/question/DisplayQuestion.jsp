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
            	<p>Identifiant : <c:out value="${ question.id }"/></p>
                <p>Sujet du questionnaire : <c:out value="${ question.survey.subject }"/></p>
                <p>Statut : <c:out value="${ question.active }"/></p>
                <p>Ordre d'apparition dans le questionnaire : <c:out value="${ question.order }"/></p>
            </c:if>
        </div>
    </body>
</html>