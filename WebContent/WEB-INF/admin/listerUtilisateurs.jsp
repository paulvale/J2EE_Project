<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Liste des clients existants</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <c:import url="menuAdmin.jsp"/>
        <c:import url="menuGestionUtilisateur.jsp" />
        <div id="corps">
        <c:choose>
            <%-- Si aucun client n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty listeUtilisateurs }">
                <p class="erreur">Aucun utilisateur enregistré.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Entreprise</th>
                    <th>Actif</th>
                    <th class="action">Modifier</th>
                    <th class="action">Supprimer</th> 
                    <th class="action">Informations</th>                   
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ listeUtilisateurs }" var="mapUtilisateurs" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ mapUtilisateurs.nom }"/></td>
                    <td><c:out value="${ mapUtilisateurs.prenom }"/></td>
                    <td><c:out value="${ mapUtilisateurs.company }"/></td>
                    <td>
                    	<c:choose>
					    	<c:when test="${mapUtilisateurs.actif}">
					    		<img src="<c:url value="/inc/actif.png"/>" alt="Actif" />
					    	</c:when>
					    	<c:otherwise>
					    		<img src="<c:url value="/inc/inactif.png"/>" alt="Inactif" />
					    	</c:otherwise>
						</c:choose>
                    </td>
                    
                    <%-- Lien vers la servlet de modification, avec passage de l'id de l'utilisateur - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/modificationUtilisateur"><c:param name="utilisateur" value="${ mapUtilisateurs.email }" /></c:url>">
                            <img src="<c:url value="/inc/modifier.png"/>" alt="Modifier" />
                        </a>
                    </td>

                    <%-- Lien vers la servlet de suppression, avec passage de l'id de l'utilisateur - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/suppressionUtilisateur"><c:param name="idUtilisateur" value="${ mapUtilisateurs.id }" /></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
                        </a>
                    </td>
                    
                    <%-- Lien vers la servlet d'information, avec passage de l'id de l'utilisateur - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/afficherUtilisateur"><c:param name="utilisateur" value="${ mapUtilisateurs.email }" /></c:url>">
                            <img src="<c:url value="/inc/info.png"/>" alt="Info" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>