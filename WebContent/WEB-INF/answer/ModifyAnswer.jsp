<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Modification d'une réponse</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="/answerModification"/>">
                <fieldset>
                    <legend>Informations question</legend>
                    
                    
                    <%-- Si et seulement si la Map des questionnaires en session n'est pas vide, alors on crée la liste déroulante --%>
                    <c:if test="${ !empty sessionScope.allQuestions }">
                    <div id="oldQuestion">
                        <select name="questionsList" id="questionsList">
                            <option value="">Choisissez une question...</option>
                            <%-- Boucle sur la map des surveys --%>
                            <c:forEach items="${ sessionScope.allQuestions }" var="mapQuestions">
                            <%--  L'expression EL ${mapQuestions.value} permet de cibler l'objet Question stocké en tant que valeur dans la Map, 
                                  et on cible ensuite simplement ses propriétés nom et prenom comme on le ferait avec n'importe quel bean. --%>
                            	<c:choose>
                            		<c:when test="${ mapQuestions.value.id == answer.question.id}">
                            			<option value="${ mapQuestions.value.id }" selected>${ mapQuestions.value.text }</option>
                            		</c:when>
                            		<c:otherwise>
                            			<option value="${ mapQuestions.value.id }">${ mapQuestions.value.text }</option>
                            		</c:otherwise>
                            	</c:choose>
                            	
                            </c:forEach>
                        </select>
                        <span class="erreur">${form.errors['questionsList']}</span>
                    	<br />
                    </div>
                    </c:if>
                </fieldset>
                <fieldset>
                    <legend>Informations réponse</legend>
                    
                    <label for="questionField">Intitulé <span class="requis">*</span></label>
                    <input type="text" id="answerField" name="answerField" value="${ answer.text }" size="30" maxlength="100"  />
                    <span class="erreur">${form.errors['answerField']}</span>
                    <br />
                    
                    <label for="orderField">Ordre <span class="requis">*</span></label>
                    <input type="text" id="orderField" name="orderField" value="${ answer.order }" size="30" maxlength="30"  />
                    <span class="erreur">${form.errors['orderField']}</span>
                    <br />
                    <c:choose>
                    	<c:when test="${ answer.active == 'active'}">
                    		<input type="radio" name="statusAnswerField" value="active" checked />  Actif
							<input type="radio" name="statusAnswerField" value="inactive" /> Inactif<br>
		                    <br />
                    	</c:when>
                    	<c:otherwise>
                    		<input type="radio" name="statusAnswerField" value="active" />  Actif
							<input type="radio" name="statusAnswerField" value="inactive" checked/> Inactif<br>
                   			<br />
                    	</c:otherwise>
                    </c:choose>
                    
                    <c:choose>
                    	<c:when test="${ answer.valide == 'valide'}">
                    		<input type="radio" name="validityField" value="valide" checked />  Valide
							<input type="radio" name="validityField" value="invalide"  /> Invalide<br>
		                    <br />
                    	</c:when>
                    	<c:otherwise>
                    		<input type="radio" name="validityField" value="valide"  />  Valide
							<input type="radio" name="validityField" value="invalide" checked /> Invalide<br>
		                    <br />
                    	</c:otherwise>
                    </c:choose>
                    
                    
                    
                    <p class="info">${ form.result }</p>
                </fieldset>
                <input type="hidden" name="idAnswer" value = "${ sessionScope.answer.id }"/>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>    
    </body>
</html>