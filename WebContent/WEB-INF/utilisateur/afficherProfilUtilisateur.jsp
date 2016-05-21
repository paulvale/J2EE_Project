<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Coucou je suis un utilisateur lambda</title>
	</head>	
	<body>
		<header>
		    <div class="container">
			<h2> Nom: ${ utilisateurSession.nom } Prenom: ${ utilisateurSession.prenom } </h2> <br/>
			</div>
		</header>
		
		<div>
			<h2>Questionnaire.</h2>
			<section id="Questionnaire">
			    <section id="page_1">
			    <table border="1">
			         <tr>
			             <td width="100pt"><b>Questionnaire</b></td>
			         </tr>
			         <c:forEach  items="${sessionScope.listSurveys}" var="listSurvey_affiche" varStatus="loop">
			         <tr>
			             <td>
			                
			                <!--<c:out value="${ sessionScope.mapSurveys[index].subject }"/>-->
			                 <a href="<c:url value="/questionnaireDisplay"><c:param name="idSurvey" value="${ listSurvey_affiche.getId() }" /></c:url>"><c:out value="${ listSurvey_affiche.id }"/>&nbsp;<c:out value="${ listSurvey_affiche.subject }"/> </a>
			             </td>
			         </tr>
			         </c:forEach>
			    </table>
			    </section>
			</section>
		</div>
		<div>
			<section id="Resultat">
				<h2>Les résultats de vos questionnaires.</h2>
				<table border="1">
				  <tr>
				  	<td width="100pt"><b>Questionnaire</b></td>
				  	<td width="100pt"><b>Résultat</b></td>
				  </tr>
				  <c:forEach items="${sessionScope.listSurveys}" var="listSurvey" varStatus="loop">
				      <tr>
				          <td><c:out value="${listSurvey.id}"/><c:out value="${listSurvey.subject}"/></td>
				          <td><c:out value="${sessionScope.mapResultats[listSurvey.id].score}"/></td>
				      </tr>
				  </c:forEach>
				  
				  
				</table>
			</section>
		</div>
	</body>
</html>