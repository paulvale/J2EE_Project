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
        <h1>SurveyId:${sessionScope.survey.getId()} , SurveySubject:${sessionScope.survey.getSubject()}</h1><br/>
        <form method="post" action="questionnaireResult">
            <c:forEach items="${ sessionScope.questions }" var="ListQuestions" varStatus="loop">
                <h2><c:out value="${ ListQuestions.getId()}" />
                <c:out value="${ ListQuestions.getText()}" />
                <c:forEach items="${ sessionScope.allanswers[ ListQuestions.id ] }" var="ListAnswers" varStatus="loop">
                    <input type="checkbox" name="answer.${ListAnswers.id}" value="${ ListAnswers.id }"><c:out value="${ ListAnswers.getText() }"/>
                </c:forEach></h2>
            </c:forEach>
            <input type="submit" value="Submit"></input>
            <input type="reset" value="Reset"></input> 
        </form>
   </body>
</html>