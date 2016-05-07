<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un utilisateur</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="menuAdmin.jsp" />
        <div>
            <form method="post" action="<c:url value="/creationUtilisateur"/>">
               <fieldset>
                    <legend>Informations Utilisateur</legend>
                    <label for="nomUtilisateur">Nom <span class="requis">*</span></label>
						<input type="text" id="nomUtilisateur" name="nomUtilisateur" value="<c:out value="${utilisateur.nom}"/>" size="30" maxlength="30" />
						<span class="erreur">${form.erreurs['nomUtilisateur']}</span>
					<br />

					<label for="prenomUtilisateur">Prénom </label>
						<input type="text" id="prenomUtilisateur" name="prenomUtilisateur" value="<c:out value="${utilisateur.prenom}"/>" size="30" maxlength="30" />
						<span class="erreur">${form.erreurs['prenomUtilisateur']}</span>
					<br />

					<label for="companyUtilisateur">Entreprise </label>
						<input type="text" id="companyUtilisateur" name="companyUtilisateur" value="<c:out value="${utilisateur.company}"/>" size="30" maxlength="60" />
						<span class="erreur">${form.erreurs['companyUtilisateur']}</span>
					<br />

					<label for="phoneUtilisateur">Numéro de téléphone <span class="requis">*</span></label>
						<input type="text" id="phoneUtilisateur" name="phoneUtilisateur" value="<c:out value="${utilisateur.phone}"/>" size="30" maxlength="30" />
						<span class="erreur">${form.erreurs['phoneUtilisateur']}</span>
					<br />

					<label for="emailUtilisateur">Adresse email</label>
						<input type="email" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${utilisateur.email}"/>" size="30" maxlength="60" />
						<span class="erreur">${form.erreurs['emailUtilisateur']}</span>
					<br />
					
					<label for="actifUtilisateur">Activation (marque actif si oui)</label>
						<input type="text" id="actifUtilisateur" name="actifUtilisateur" value="<c:out value="${utilisateur.actif}"/>" size="30" maxlength="60" />
						<span class="erreur">${form.erreurs['actifUtilisateur']}</span>
					<br />
					
					<label for="adminUtilisateur">Admin (marque admin si oui)</label>
						<input type="text" id="adminUtilisateur" name="adminUtilisateur" value="<c:out value="${utilisateur.admin}"/>" size="30" maxlength="60" />
						<span class="erreur">${form.erreurs['adminUtilisateur']}</span>
					<br />
					
					<label for="passwordUtilisateur">Mot de Passe <span class="requis">*</span></label>
						<input type="text" id="passwordUtilisateur" name="passwordUtilisateur" value="<c:out value="${utilisateur.motDePasse}"/>" size="30" maxlength="60" />
						<span class="erreur">${form.erreurs['passwordUtilisateur']}</span>
					<br />
                </fieldset>
                <p class="info">${ form.resultat }</p>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>