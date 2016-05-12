package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;

public class SuppressionUtilisateur extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PARAM_NOM_UTILISATEUR = "idUtilisateur";

    public static final String VUE              = "/listerUtilisateurs";
    
    private UtilisateurDao          utilisateurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération du paramètre */
        String stringUtilisateur = getValeurParametre( request, PARAM_NOM_UTILISATEUR );
        int idUtilisateur = Integer.parseInt(stringUtilisateur)
        		;
        /* On va supprimer la personne que l'on a recu en parametre */
        utilisateurDao.supprimer(idUtilisateur);

        /* Redirection vers la fiche récapitulative */
        response.sendRedirect( request.getContextPath() + VUE );
    }

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}