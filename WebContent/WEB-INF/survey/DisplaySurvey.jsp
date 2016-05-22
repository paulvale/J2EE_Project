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
                <p>Actif : 
					<c:choose>
				    	<c:when test="${survey.active == 'active'}">
				    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
				    	</c:when>
				    	<c:otherwise>
				    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
				    	</c:otherwise>
					</c:choose>
                </p>
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
			                    <th>Active</th>
			                    <th class="action">Modifier</th>
                    			<th class="action">Supprimer</th> 
                    			<th class="action">Informations</th>                           
			                </tr>
			                <%-- Parcours de la Map des questionnaires en session, et utilisation de l'objet varStatus. --%>
			                <c:forEach items="${ sessionScope.questions }" var="listQuestions" varStatus="loop">
			                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
			                <tr class="${loop.index % 2 == 0 ? 'pair' : 'impair'}">
			                    <%-- Affichage des propriétés du bean Survey, qui est stocké en tant que valeur de l'entrée courante de la map --%>
			                    
			                    <%-- Lien vers la servlet de suppression, avec passage l'id de la question - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
			                    <td>
			                    	<c:out value="${ listQuestions.text }"/> 
			                    </td>
			                    <td>
			                    	<c:out value="${ listQuestions.order }"/>
			                    </td>
			                    <td>
			                    	<c:choose>
								    	<c:when test="${listQuestions.active == 'active'}">
								    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
								    	</c:when>
								    	<c:otherwise>
								    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
								    	</c:otherwise>
									</c:choose>
			                    </td>
			                    
			                    <td class="action">
			                    	<a href="<c:url value="/questionModification"><c:param name="idQuestion" value="${ listQuestions.id }" /><c:param name="idParameter" value="${ survey.id }" /></c:url>">
			                            <img src="<c:url value="/inc/modifier.png"/>" alt="Modifier" />
			                        </a>
			                    </td>
			                    <td class="action">
			                    	<a href="<c:url value="/questionDeletion"><c:param name="idQuestion" value="${ listQuestions.id }" /><c:param name="idParameter" value="${ survey.id }" /></c:url>">
			                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Modifier" />
			                        </a>
			                    </td>
			                    <td class="action">
			                    	<a href="<c:url value="/questionDisplay"><c:param name="idQuestion" value="${ listQuestions.id }" /></c:url>">
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