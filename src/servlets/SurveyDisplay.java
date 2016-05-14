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

import beans.Question;
import beans.Survey;
import dao.DAOFactory;
import dao.QuestionDao;

public class SurveyDisplay extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY      	= "daofactory";
	public static final String ATTRIBUTESURVEY 			= "survey";
	public static final String SURVEYS_SESSION			= "surveys";
	public static final String QUESTIONS_SESSION       	= "questions";
    public static final String ID_SURVEY 				= "idParameter";
    public static final String ATTRIBUTEFORM   			= "form";
    
	public static final String VIEW             = "/WEB-INF/survey/DisplaySurvey.jsp";
	public static final String LISTVIEW         = "/WEB-INF/survey/SurveyList.jsp";
	
	private QuestionDao          m_questionDao;
    
    public void init() throws ServletException 
    {
        /* Récupération d'une instance de notre DAO Question */
        this.m_questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
    }
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(ID_SURVEY);
    	Survey survey = null;
    	HttpSession session = request.getSession();
    	Long lId = null;
    	if(sId != null)
    	{
    		lId = Long.parseLong(sId);
    	}
    	
    	Map<Long, Survey> surveys = (HashMap<Long, Survey>) session.getAttribute( SURVEYS_SESSION );
    	
    	boolean bError = false;
    	if ( surveys == null ) 
        {
    		System.out.println("Pas de map en sessions");
    		bError = true;
        }
        else
        {
        	
        	survey = surveys.get( lId );
    		if( survey != null)
    		{
    			session.setAttribute( ATTRIBUTESURVEY, survey );
    			List<Question> listQuestions = m_questionDao.lister(lId);
                session.setAttribute( QUESTIONS_SESSION, listQuestions );
    		}
    		else
    		{
    			System.out.println("Error - Pas de questionnaire avec cet id");
    			bError = true;
    		}
        	
        }
    	
    	if(!bError)
    	{
    		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
    	}
    	else
    	{
    		this.getServletContext().getRequestDispatcher( LISTVIEW ).forward( request, response );
    	}
		
    }
}
