package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UtilisateurDao;
import dao.DAOFactory;

import beans.Utilisateur;
import forms.ConnexionForm;

public class Connexion extends HttpServlet {    
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";

    public static final String VUE_SUCCES_ADMIN       = "/WEB-INF/admin/afficherProfilAdmin.jsp";
    public static final String VUE_SUCCES       	  = "/WEB-INF/utilisateur/afficherProfilUtilisateur.jsp";
    public static final String VUE_FORM               = "/WEB-INF/connexion.jsp";

    private UtilisateurDao          utilisateurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException { 
        /*
         * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
         * dans le web.xml
         */
        //String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm(utilisateurDao);

        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.connecterUtilisateur( request );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_USER, utilisateur );
        request.setAttribute( ATT_FORM, form );

        /* Si aucune erreur */
        if ( form.getErreurs().isEmpty() ) {
            /* Affichage de la vue de l'utilisateur
             * C'est ici que je vais pouvoir le rediriger vers l'une ou l'autre de mes pages
             *  */
        	if (utilisateur.getAdmin()){
        		this.getServletContext().getRequestDispatcher( VUE_SUCCES_ADMIN ).forward( request, response );
        	}else {
        		this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        	}
            
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}