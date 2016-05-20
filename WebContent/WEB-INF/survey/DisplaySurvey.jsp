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
    	<c:import url="../admin/menuAdmin.jsp" />
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
            <p class="info">${ message }</p>
            <c:if test="${ !errors }">
            	<p>Identifiant : <c:out value="${ survey.id }"/></p>
                <p>Sujet : <c:out value="${ survey.subject }"/></p>
                <p>Statut : <c:out value="${ survey.active }"/></p>
                <p>Date de création : <joda:format value="${ survey.creationDate }" pattern="dd/MM/yyyy HH:mm:ss"/></p>
                <div id="body">
			        <c:choose>
			            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
			            <c:when test="${ empty sessionScope.questions }">
			                <p class="erreur">Aucune question enregistrée.</p>
			            </c:when>
			            <%-- Sinon, affichage du tableau. --%>
			            <c:otherwise>
			            <p>Liste des questions: </p>
			            <table>
			                <tr>
			                    <th>Intitulé</th>
			                    <th>Ordre d'apparition</th>
			                    <th>Statut</th>
			                    <th class="action">Action</th>                    
			                </tr>
			                <%-- Parcours de la Map des questionnaires en session, et utilisation de l'objet varStatus. --%>
			                <c:forEach items="${ sessionScope.questions }" var="listQuestions" varStatus="loop">
			                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
			                <tr class="${loop.index % 2 == 0 ? 'pair' : 'impair'}">
			                    <%-- Affichage des propriétés du bean Survey, qui est stocké en tant que valeur de l'entrée courante de la map --%>
			                    
			                    <%-- Lien vers la servlet de suppression, avec passage l'id de la question - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
			                    <td>
			                    	<a href="<c:url value="/questionDisplay"><c:param name="idQuestion" value="${ listQuestions.id }" /></c:url>"><c:out value="${ listQuestions.text }"/> </a>
			                    </td>
			                    <td>
			                    	<c:out value="${ listQuestions.order }"/>
			                    </td>
			                    <td><c:out value="${ listQuestions.active }"/></td>
			                    
			                    <td class="action">
			                    	<a href="<c:url value="/questionModification"><c:param name="idQuestion" value="${ listQuestions.id }" /><c:param name="idParameter" value="${ survey.id }" /></c:url>">
			                            <img src="<c:url value="/inc/edit.gif"/>" alt="Modifier" />
			                        </a>
			                        <a href="<c:url value="/questionDeletion"><c:param name="idQuestion" value="${ listQuestions.id }" /><c:param name="idParameter" value="${ survey.id }" /></c:url>">
			                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
			                        </a>
			                    </td>
			                </tr>
			                </c:forEach>
			            </table>
			            </c:otherwise>
			        </c:choose>
				</div>
            </c:if>
        </div>
    </body>
</html>