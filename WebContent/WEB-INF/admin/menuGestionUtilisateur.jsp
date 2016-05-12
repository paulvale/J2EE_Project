<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div >
	<fieldset>
		<legend>Gestion des utilisateurs</legend>
		<p><a href="<c:url value="/listerUtilisateurs"/>">Voir liste des utilisateurs</a></p>
    	<p><a href="<c:url value="/creationUtilisateur"/>">Créer un nouveau client</a></p>
    </fieldset>
</div>