<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste de votre recherche</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="menuUtilisateur.jsp"/>
        <div id="corps">
        <c:choose>
            <%-- Si aucun questionnaire n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty listeScores }">
                <p class="erreur">Aucun score en ligne ...</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Id</th>
                    <th>Sujet</th>
                    <th>Score</th>                 
                </tr>
                <%-- Parcours de la Map des questionnaires en session, et utilisation de l'objet varSurvey. --%>
                <c:forEach items="${ listeScores }" var="varScore" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ varScore.id }"/></td>
                    <td><c:out value="${ varScore.idQuestionnaire }"/></td>
                    <td><c:out value="${ varScore.score }"/></td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>