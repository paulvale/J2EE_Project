package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UtilisateurDao;
import dao.DAOFactory;
import dao.ResultDao;
import dao.SurveyDao;
import beans.Resultat;
import beans.Survey;
import beans.Utilisateur;
import forms.ConnexionForm;

public class Connexion extends HttpServlet {    
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_SESSION_USER = "utilisateurSession";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_SURVEYS  = "mapSurveys";
    public static final String ATT_SESSION_SURVEYS_LISTE  = "listSurveys";
    public static final String ATT_SESSION_SCORES = "mapResultats";

    public static final String VUE_SUCCES_ADMIN       = "/afficherProfilAdmin";
    public static final String VUE_SUCCES       	  = "/afficherProfilUtilisateur";
    public static final String VUE_FORM               = "/WEB-INF/connexion.jsp";

    private UtilisateurDao          utilisateurDao;
    private ResultDao               m_resultDao;
    private SurveyDao               m_surveyDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
        this.m_resultDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getResultDao();
        this.m_surveyDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSurveyDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException { 

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
        	/* Récupération de la session depuis la requête */
            HttpSession session = request.getSession();

            /**
             * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
             * Utilisateur à la session, sinon suppression du bean de la session.
             */
            if ( form.getErreurs().isEmpty() ) {
                session.setAttribute( ATT_SESSION_USER, utilisateur );
            } else {
                session.setAttribute( ATT_SESSION_USER, null );
            }
        	
        	if (utilisateur.getAdmin()){
        		response.sendRedirect( request.getContextPath() + VUE_SUCCES_ADMIN);
        		//this.getServletContext().getRequestDispatcher( VUE_SUCCES_ADMIN ).forward( request, response );
        	}else {        		
        		//Cette partie est pour afficher la partie de Résultat dans l'écran de l'utilisateur.
        		List<Survey> listSurveys = m_surveyDao.lister();
        		List<Resultat>  mapResultats = m_resultDao.listerScore(utilisateur.getId());
        		
        		session.setAttribute( ATT_SESSION_SURVEYS_LISTE, listSurveys );
        		session.setAttribute( ATT_SESSION_SCORES, mapResultats );
        		
        		//this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        		response.sendRedirect( request.getContextPath() + VUE_SUCCES);
        	}
            
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}