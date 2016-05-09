<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
	<p><a href="<c:url value="/afficherProfilAdmin"/>">Afficher mon profil</a></p>
    <p><a href="<c:url value="/creationUtilisateur"/>">Gestion Utilisateur</a></p>
    <p><a href="<c:url value=""/>">Gestion Questionnaire</a></p>
    <p><a href="<c:url value="/deconnexion"/>">Déconnexion</a></p>
</div>