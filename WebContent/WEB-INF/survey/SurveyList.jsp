<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des questionnaires existants</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div id="body">
        <c:choose>
            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.surveys }">
                <p class="erreur">Aucun questionnaire enregistré.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Sujet</th>
                    <th>Statut</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des questionnaires en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.surveys }" var="mapSurveys" varStatus="loop">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${loop.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Survey, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td>
                    	<a href="<c:url value="/surveyDisplay"><c:param name="idParameter" value="${ mapSurveys.key }" /></c:url>"><c:out value="${ mapSurveys.value.subject }"/> </a>
                    </td>
                    <td><c:out value="${ mapSurveys.value.active }"/></td>
                    <%-- Lien vers la servlet de suppression, avec passage du sujet du questionnaire - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                    	<a href="<c:url value="/surveyModification"><c:param name="idParameter" value="${ mapSurveys.key }" /></c:url>">
                            <img src="<c:url value="/inc/edit.gif"/>" alt="Modifier" />
                        </a>
                        <a href="<c:url value="/surveyDeletion"><c:param name="idSurvey" value="${ mapSurveys.key }" /></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>