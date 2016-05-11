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
            <form method="post" action="<c:url value="/surveyCreation"/>">
                <fieldset>
                    <legend>Informations du questionnaire</legend>
                    <c:import url="/inc/inc_survey_form.jsp" />
                    <p class="info">${ form.result }</p>
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>