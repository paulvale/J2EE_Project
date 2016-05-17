<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Informations sur l'utilisateur</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
    	<c:import url="menuAdmin.jsp"/>
    	<c:import url="menuGestionUtilisateur.jsp" />
    	
    	<form method="post" action="<c:url value="/modificationUtilisateur"/>">
               <fieldset>
                    <legend>Modification du statut ( actif et admin) de l'utilisateur selectionné</legend>
            		<label for="nomUtilisateur">Nom</label>
						<input type="text" id="nomUtilisateur" name="nomUtilisateur" value="<c:out value="${utilisateur.nom}"/>" readonly="readonly" />
					<br />
					
            		<label for="prenomUtilisateur">Prénom</label>
						<input type="text" id="prenomUtilisateur" name="prenomUtilisateur" value="<c:out value="${utilisateur.prenom}"/>" readonly="readonly" />
					<br />
            		
            		<label for="emailUtilisateur">Email</label>
						<input type="text" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${utilisateur.email}"/>" readonly="readonly" />
					<br />
					
					<label for="actifUtilisateur">Activation</label>
						<input type="radio" name="actifUtilisateur" value="actif" ${utilisateur.actif==true?'CHECKED':''}> Actif
  						<input type="radio" name="actifUtilisateur" value="inactif" ${utilisateur.actif==false?'CHECKED':''}> Inactif 
					<br />
					
					<label for="adminUtilisateur">Admin</label>
						<input type="radio" name="adminUtilisateur" value="actif" ${utilisateur.admin==true?'CHECKED':''} > Actif
  						<input type="radio" name="adminUtilisateur" value="inactif" ${utilisateur.admin==false?'CHECKED':''}> Inactif
					<br />
                </fieldset>
                
                <p class="info">${ form.resultat }</p>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
       </form>
    </body>
</html>