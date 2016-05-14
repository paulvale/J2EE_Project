package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Survey;
import dao.SurveyDao;
import dao.DAOException;
import dao.DAOFactory;

public class SurveyDeletion extends HttpServlet 
{
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PARAM_ID_SURVEY  = "idSurvey";
    public static final String SURVEYS_SESSION  = "surveys";

    public static final String VIEW              = "/surveyList";

    private SurveyDao          surveyDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.surveyDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSurveyDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* Récupération du paramètre */
        String idSurvey = getValeurParametre( request, PARAM_ID_SURVEY );
        Long id = Long.parseLong( idSurvey );

        /* Récupération de la Map des clients enregistrés en session */
        HttpSession session = request.getSession();
        Map<Long, Survey> surveys = (HashMap<Long, Survey>) session.getAttribute( SURVEYS_SESSION );

        /* Si l'id du client et la Map des clients ne sont pas vides */
        if ( id != null && surveys != null ) 
        {
            try 
            {
                /* Alors suppression du client de la BDD */
                surveyDao.delete( surveys.get( id ) );
                /* Puis suppression du client de la Map */
                surveys.remove( id );
            } 
            catch ( DAOException e ) 
            {
                e.printStackTrace();
            }
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( SURVEYS_SESSION, surveys );
        }

        /* Redirection vers la fiche récapitulative */
        response.sendRedirect( request.getContextPath() + VIEW );
    }

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String fieldName ) 
    {
        String valeur = request.getParameter( fieldName );
        if ( valeur == null || valeur.trim().length() == 0 ) 
        {
            return null;
        } 
        else 
        {
            return valeur;
        }
    }
}
