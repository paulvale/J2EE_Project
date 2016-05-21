<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<fieldset>
		<legend>Menu Stagiaire</legend>
		<p><a href="<c:url value="/afficherProfilUtilisateur"/>">Afficher mon profil</a></p>
    	<p><a href="<c:url value="/listerQuestionnairesUtilisateur"/>">Liste questionnaires</a></p>
    	<p><a href="<c:url value="/afficherScore"/>">Liste résultats</a></p>
    	<p><a href="<c:url value="/deconnexion"/>">Déconnexion</a></p>
    </fieldset>
</div>