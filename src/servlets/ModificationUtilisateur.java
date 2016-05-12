package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;

public class ModificationUtilisateur extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String VUE              = "/WEB-INF/admin/modificationUtilisateur.jsp";
    public static final String VUE_SUCCESS		= "/listerUtilisateurs";
    private static final String CHAMP_ACTIF		= "actifUtilisateur";
    private static final String CHAMP_ADMIN		= "adminUtilisateur";
    private static final String CHAMP_EMAIL     = "emailUtilisateur";
    
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
    
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération des champs et des valeurs */
    	String emailUtilisateur = getValeurParametre(request, CHAMP_EMAIL);
        String admin = getValeurParametre( request, CHAMP_ADMIN );
        String actif = getValeurParametre( request, CHAMP_ACTIF );
        
        boolean actifBoolean = actif == "actif" ? true : false;
        boolean adminBoolean = admin == "admin" ? true : false;
        
        utilisateurDao.modificationStatus(emailUtilisateur, actifBoolean, adminBoolean);

     	/* Redirection vers la liste utilisateur */
        response.sendRedirect( request.getContextPath() + VUE_SUCCESS );
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