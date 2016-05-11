<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="/questionCreation"/>">
                <fieldset>
                    <legend>Informations questionnaire</legend>
                    
                    
                    <%-- Si et seulement si la Map des questionnaires en session n'est pas vide, alors on crée la liste déroulante --%>
                    <c:if test="${ !empty sessionScope.surveys }">
                    <div id="oldSurvey">
                        <select name="surveysList" id="surveysList">
                            <option value="">Choisissez un questionnaire...</option>
                            <%-- Boucle sur la map des surveys --%>
                            <c:forEach items="${ sessionScope.surveys }" var="mapSurveys">
                            <%--  L'expression EL ${mapSurveys.value} permet de cibler l'objet Survey stocké en tant que valeur dans la Map, 
                                  et on cible ensuite simplement ses propriétés nom et prenom comme on le ferait avec n'importe quel bean. --%>
                            <option value="${ mapSurveys.value.id }">${ mapSurveys.value.subject }</option>
                            </c:forEach>
                        </select>
                        <span class="erreur">${form.errors['surveysList']}</span>
                    	<br />
                    </div>
                    </c:if>
                </fieldset>
                <fieldset>
                    <legend>Informations question</legend>
                    
                    <label for="questionField">Intitulé <span class="requis">*</span></label>
                    <input type="text" id="questionField" name="questionField" value="" size="30" maxlength="30"  />
                    <span class="erreur">${form.errors['questionField']}</span>
                    <br />
                    
                    <label for="orderField">Ordre <span class="requis">*</span></label>
                    <input type="text" id="orderField" name="orderField" value="" size="30" maxlength="30"  />
                    <span class="erreur">${form.errors['orderField']}</span>
                    <br />
                    
                    <input type="radio" name="statusQuestionField" value="active" checked />  Actif
					<input type="radio" name="statusQuestionField" value="inactive" /> Inactif<br>
                    <br />
                    
                    <p class="info">${ form.result }</p>
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
        
        <%-- Inclusion de la bibliothèque jQuery. Vous trouverez des cours sur JavaScript et jQuery aux adresses suivantes :
               - http://www.siteduzero.com/tutoriel-3-309961-dynamisez-vos-sites-web-avec-javascript.html 
               - http://www.siteduzero.com/tutoriel-3-659477-un-site-web-dynamique-avec-jquery.html 
               
             Si vous ne souhaitez pas télécharger et ajouter jQuery à votre projet, vous pouvez utiliser la version fournie directement en ligne par Google :
             <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script> 
             <script src="<c:url value="/inc/jquery.js"/>"></script>
        --%>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script> 
        
        
    </body>
</html>