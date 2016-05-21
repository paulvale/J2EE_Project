package filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Survey;
import beans.Question;
import beans.Answer;
import dao.SurveyDao;
import dao.QuestionDao;
import dao.AnswerDao;
import dao.DAOFactory;

public class PreloadingFilter implements Filter 
{
    public static final String CONF_DAO_FACTORY      	= "daofactory";
    public static final String ATT_SESSION_SURVEY   	= "surveys";
    public static final String ATT_SESSION_QUESTION 	= "allQuestions";
    public static final String ATT_SESSION_ANSWER   	= "answers";

    private SurveyDao          surveyDao;
    private QuestionDao        questionDao;
    private AnswerDao          answerDao;

    public void init( FilterConfig config ) throws ServletException 
    {
        /* Récupération d'une instance de nos DAO Client et Commande */
        this.surveyDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSurveyDao();
        this.questionDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException 
    {
        /* Cast de l'objet request */
        HttpServletRequest request = (HttpServletRequest) req;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        /*
         * Si la map des clients n'existe pas en session, alors l'utilisateur se
         * connecte pour la première fois et nous devons précharger en session
         * les infos contenues dans la BDD.
         */
        if ( session.getAttribute( ATT_SESSION_SURVEY ) == null ) 
        {
            /*
             * Récupération de la liste des questionnaires existants, et enregistrement
             * en session
             */
            List<Survey> listSurvey = surveyDao.lister();
            Map<Long, Survey> mapSurveys = new HashMap<Long, Survey>();
            for ( Survey survey : listSurvey ) 
            {
                mapSurveys.put( survey.getId(), survey );
            }
            session.setAttribute( ATT_SESSION_SURVEY, mapSurveys );
        }

        /*
         * De même pour la map des questions et celle des reponses
         */
        
        if ( session.getAttribute( ATT_SESSION_QUESTION ) == null ) 
        {
            /*
             * Récupération de la liste des questionnaires existants, et enregistrement
             * en session
             */
            List<Question> listQuestions = questionDao.lister();
            Map<Long, Question> mapQuestions = new HashMap<Long, Question>();
            for ( Question question : listQuestions ) 
            {
            	mapQuestions.put( question.getId(), question );
            }
            session.setAttribute( ATT_SESSION_QUESTION, mapQuestions );
        }

        /* Pour terminer, poursuite de la requête en cours */
        chain.doFilter( request, res );
    }

    public void destroy() 
    {
    }
}