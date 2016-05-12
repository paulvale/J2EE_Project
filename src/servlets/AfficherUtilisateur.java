package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;

public class AfficherUtilisateur extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String VUE              = "/WEB-INF/admin/afficherUtilisateur.jsp";
    
    private UtilisateurDao          utilisateurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    		/* Récupération du paramètre */
    		String emailUtilisateur = getValeurParametre( request, ATT_USER );
        	Utilisateur utilisateur = utilisateurDao.trouver(emailUtilisateur);
            
            request.setAttribute( ATT_USER, utilisateur );	
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
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