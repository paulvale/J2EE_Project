package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Question;
import dao.QuestionDao;
import dao.DAOException;
import dao.DAOFactory;

public class QuestionDeletion extends HttpServlet 
{
    public static final String CONF_DAO_FACTORY 	= "daofactory";
    public static final String PARAM_ID_QUESTION  	= "idQuestion";
    public static final String QUESTIONS_SESSION  	= "questions";
    public static final String ALL_QUESTIONS_SESSION 	= "allQuestions";
    public static final String ID_SURVEY 			= "idParameter";

    public static final String VIEW              = "/surveyDisplay";

    private QuestionDao          questionDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* Récupération du paramètre */
        String idQuestion = getValeurParametre( request, PARAM_ID_QUESTION );
        Long id = Long.parseLong( idQuestion );
        
        String idSurvey = getValeurParametre( request, ID_SURVEY );

        /* Récupération de la Map des questions enregistrés en session */
        HttpSession session = request.getSession();
        List<Question> questions = (ArrayList<Question>) session.getAttribute( QUESTIONS_SESSION );
        Map<Long, Question> allQuestions = (HashMap<Long, Question>) session.getAttribute( ALL_QUESTIONS_SESSION );

        /* Si l'id du client et la Map des clients ne sont pas vides */
        if ( id != null && questions != null ) 
        {
            try 
            {
            	Question question = find(questions, id);
                /* Alors suppression du client de la BDD */
                questionDao.delete( question );
                /* Puis suppression du client de la Map */
                questions.remove( question );
                allQuestions.remove(id);
                
            } 
            catch ( DAOException e ) 
            {
                e.printStackTrace();
            }
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( QUESTIONS_SESSION, questions );
            session.setAttribute( ALL_QUESTIONS_SESSION, allQuestions );
        }
        
        /* Redirection vers la fiche récapitulative */
        response.sendRedirect( request.getContextPath() + VIEW + "?" + ID_SURVEY + "=" + idSurvey);
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
    
    public Question find(List<Question> questions, Long lId)
	{
		Question question = null;
		for(int i = 0; i < questions.size(); i++)
		{
			if(questions.get(i).getId().equals(lId))
			{
				question = questions.get(i);
				break;
			}
		}
		return question;
	}
}