<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Affichage d'une question</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="../admin/menuAdmin.jsp" />
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
            <p class="info">${ message }</p>
            <c:if test="${ !errors }">
            	<p>Identifiant de la question : <c:out value="${ question.id }"/></p>
            	<p>Initulé de la question : <c:out value="${ question.text }"/></p>
                <p>Sujet du questionnaire associé : <c:out value="${ question.survey.subject }"/></p>
                <p>Active :
	                <c:choose>
				    	<c:when test="${question.active == 'active'}">
				    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
				    	</c:when>
				    	<c:otherwise>
				    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
				    	</c:otherwise>
					</c:choose>
                </p>
                <p>Ordre d'apparition dans le questionnaire : <c:out value="${ question.order }"/></p>
                <div id="body">
			        <c:choose>
			            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
			            <c:when test="${ empty sessionScope.answers }">
			                <p class="erreur">Aucune réponse enregistrée.</p>
			            </c:when>
			            <%-- Sinon, affichage du tableau. --%>
			            <c:otherwise>
			            <p>Liste des réponses: </p>
			            <table>
			                <tr>
			                    <th>Intitulé</th>
			                    <th>Ordre d'apparition</th>
			                    <th>Actif</th>
			                    <th>Valide</th>
			                    <th class="action">Modifier</th>
			                    <th class="action">Supprimer</th>
			                    <th class="action">Informations</th>                       
			                </tr>
			                <%-- Parcours de la Map des questionnaires en session, et utilisation de l'objet varStatus. --%>
			                <c:forEach items="${ sessionScope.answers }" var="listAnswers" varStatus="loop">
			                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
			                <tr class="${loop.index % 2 == 0 ? 'pair' : 'impair'}">
			                    <%-- Affichage des propriétés du bean Survey, qui est stocké en tant que valeur de l'entrée courante de la map --%>
			                    
			                    <%-- Lien vers la servlet de suppression, avec passage l'id de la question - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
			                    <td>
			                    	<c:out value="${listAnswers.text }" />
			                    </td>
			                    <td>
			                    	<c:out value="${ listAnswers.order }"/>
			                    </td>
			                    <td>
				                    <c:choose>
								    	<c:when test="${listAnswers.active == 'active'}">
								    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
								    	</c:when>
								    	<c:otherwise>
								    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
								    	</c:otherwise>
									</c:choose>
			                    </td>
			                    <td>
				                    <c:choose>
								    	<c:when test="${listAnswers.valide == 'valide'}">
								    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
								    	</c:when>
								    	<c:otherwise>
								    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
								    	</c:otherwise>
									</c:choose>
			                    </td>
			                    <td class="action">
			                    	<a href="<c:url value="/answerModification"><c:param name="idAnswer" value="${ listAnswers.id }" /><c:param name="idQuestion" value="${ listAnswers.question.id }" /></c:url>">
			                            <img src="<c:url value="/inc/modifier.png"/>" alt="Modifier" />
			                        </a>
			                    </td>
			                    <td class="action">
			                        <a href="<c:url value="/answerDeletion"><c:param name="idAnswer" value="${ listAnswers.id }" /><c:param name="idQuestion" value="${ listAnswers.question.id }" /></c:url>">
			                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
			                        </a>
			                    </td>
			                    <td class="action">
			                        <a href="<c:url value="/answerDisplay"><c:param name="idAnswer" value="${ listAnswers.id }" /></c:url>">
			                            <img src="<c:url value="/inc/info.png"/>" alt="Info" />
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