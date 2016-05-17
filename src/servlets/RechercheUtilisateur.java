package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.UtilisateurDao;
import dao.DAOFactory;

public class RechercheUtilisateur extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_LISTE		= "listeUtilisateurs";
    
    private static final String CHAMP_RECHERCHE    = "rechercheUtilisateur";
    private static final String CHAMP_VALEUR   	   = "valeurUtilisateur";

    public static final String VUE       = "/listerRechercheUtilisateurs";
    public static final String VUE_FORM         = "/WEB-INF/admin/rechercheUtilisateur.jsp";

    private UtilisateurDao          utilisateurDao;
    private List<Utilisateur>		utilisateursListe;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* À la réception d'une requête GET, affichage de la liste des clients */
    	String recherche = getValeurChamp( request, CHAMP_RECHERCHE );
        String valeur = getValeurChamp( request, CHAMP_VALEUR );
        
        if(recherche.equals("prenom")){
        	// Recherche par prénom
        	utilisateursListe = utilisateurDao.trouverParPrenom(valeur);
        } else {
        	// Recherche par société
        	utilisateursListe = utilisateurDao.trouverParCompany(valeur);
        }
        
    	request.setAttribute( ATT_LISTE, utilisateursListe );    	
    	 this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    	
    }
    
    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
}