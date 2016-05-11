<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head> 
        <meta charset="utf-8" />
        <title>Création d'un questionnaire</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="/surveyModification"/>">
                <fieldset>
                    <legend>Informations du questionnaire</legend>
                    
                    <label for="subjectField">Sujet <span class="requis">*</span></label>
					<input type="text" id="subjectField" name="subjectField" value="${sessionScope.survey.subject}" size="50" maxlength="50" />
					<br />
					<span class="erreur">${form.errors['subjectField']}</span>
					<br />
					
                    <c:choose>
						<c:when test="${ sessionScope.survey.active == 'active'}">
							<input type="radio" name="statusField" value="active" checked />  Actif
							<input type="radio" name="statusField" value="inactive" /> Inactif<br>
							<br />
						</c:when>
						<c:otherwise>
							<input type="radio" name="statusField" value="active"  />  Actif
							<input type="radio" name="statusField" value="inactive" checked /> Inactif<br>
							<br />
						</c:otherwise>
					</c:choose>
					<span class="erreur">${form.errors['statusField']}</span>
					<br />
					<p class="info">${ form.result }</p>
                </fieldset>
                <input type="hidden" name="idField" value = "${ sessionScope.survey.id }"/>
                <input type="hidden" name="dateField" value = "${ sessionScope.survey.creationDate.toString() }"/>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
            
        </div>
    </body>
</html>