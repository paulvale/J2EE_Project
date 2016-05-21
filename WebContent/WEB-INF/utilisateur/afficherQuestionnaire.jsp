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
        <h1> Questionnaire en cours <h1>
        <h1> Id:${sessionScope.survey.getId()}</h1>
        <h1> Sujet:${sessionScope.survey.getSubject()}</h1>
        <form method="post" action="questionnaireResult">
            <c:forEach items="${ sessionScope.questions }" var="ListQuestions" varStatus="loop">
            	<div class="question">
            		<h2>Q<c:out value="${ ListQuestions.getId()}" /> - <c:out value="${ ListQuestions.getText()}" /> </h2>
                <c:forEach items="${ sessionScope.allanswers[ ListQuestions.id ] }" var="ListAnswers" varStatus="loop">
                    <input type="checkbox" name="answer.${ListAnswers.id}" value="${ ListAnswers.id }">
                    <c:out value="${ ListAnswers.getText() }"/></br>
                </c:forEach>
            	</div>
            </c:forEach>
            <input type="submit" value="Submit"></input>
            <input type="reset" value="Reset"></input> 
        </form>
   </body>
</html>