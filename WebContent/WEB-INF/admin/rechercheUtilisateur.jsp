<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Recherche d'un utilisateur</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="menuAdmin.jsp"/>
        <c:import url="menuGestionUtilisateur.jsp" />
        <div>
            <form method="post" action="<c:url value="/rechercheUtilisateur"/>">
               <fieldset>
                    <legend>Recherche d'un utilisateur</legend>
                    <label for="prenomUtilisateur">Par :</label>
						<input type="radio" name="rechercheUtilisateur" value="prenom" CHECKED> son prénom
  						<input type="radio" name="rechercheUtilisateur" value="company"> sa boîte 
					<br />
                    <label for="valeurUtilisateur">Recherche</label>
						<input type="text" id="valeurUtilisateur" name="valeurUtilisateur" value="<c:out value="${recherche.valeur}"/>" size="30" maxlength="30" />
						<span class="erreur">${form.erreurs['valeurUtilisateur']}</span>
					<br />
                </fieldset>
                <p class="info">${ form.resultat }</p>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>