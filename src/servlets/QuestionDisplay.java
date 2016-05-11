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
import beans.Survey;
import dao.DAOFactory;
import dao.AnswerDao;

public class QuestionDisplay extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY      	= "daofactory";
	public static final String ATTRIBUTEQUESTION		= "question";
	public static final String SURVEYS_SESSION			= "surveys";
	public static final String QUESTIONS_SESSION       	= "questions";
    public static final String PARAMID 					= "idParameter";
    public static final String ATTRIBUTEFORM   			= "form";
    
	public static final String VIEW             = "/WEB-INF/question/DisplayQuestion.jsp";
	public static final String LISTVIEW         = "/WEB-INF/survey/DisplaySurvey.jsp";
	
	private AnswerDao          m_AnswerDao;
    
	/*
    public void init() throws ServletException 
    {
        this.m_AnswerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
    }
	*/
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(PARAMID);
    	Question question = null;
    	HttpSession session = request.getSession();
    	
    	Long lId = Long.parseLong(sId);
    	
    	List<Question> questions = (ArrayList<Question>) session.getAttribute( QUESTIONS_SESSION );
    	
    	boolean bError = false;
    	if ( questions == null ) 
        {
    		System.out.println("Pas de liste de questions en sessions");
    		bError = true;
        }
        else
        {
        	
        	question = find(questions, lId);
    		if( question != null)
    		{
    			session.setAttribute( ATTRIBUTEQUESTION, question );
    			//List<Question> listQuestions = m_questionDao.lister(lId);
    			/*
    			Map<Long, Question> mapQuestions = new HashMap<Long, Question>();
    			for ( Question question : listQuestions ) 
                {
                    mapQuestions.put( question.getId(), question );
                }
                */
                //session.setAttribute( QUESTIONS_SESSION, listQuestions );
    		}
    		else
    		{
    			System.out.println("Pas de questionnaire avec cet id");
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
