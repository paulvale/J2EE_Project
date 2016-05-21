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
            <c:when test="${ empty listeQuestionnaires }">
                <p class="erreur">Aucun questionnaire accessible ...</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Id</th>
                    <th>Sujet</th>
                    <th class= "action">Action</th>                 
                </tr>
                <%-- Parcours de la Map des questionnaires en session, et utilisation de l'objet varSurvey. --%>
                <c:forEach items="${ listeQuestionnaires }" var="varQuestionnaire" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ varQuestionnaire.id }"/></td>
                    <td><c:out value="${ varQuestionnaire.subject }"/></td>
                    <%-- Lien vers la servlet de modification, avec passage de l'id de l'utilisateur - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/questionnaireDisplay"><c:param name="idSurvey" value="${ varQuestionnaire.id }" /></c:url>">
                            <img src="<c:url value="/inc/modifier.png"/>" alt="Modifier" />
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