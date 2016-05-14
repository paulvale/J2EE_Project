package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Answer;
import beans.Question;
import dao.DAOFactory;
import dao.AnswerDao;

public class QuestionDisplay extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY      	= "daofactory";
	public static final String ATTRIBUTEQUESTION		= "question";
	public static final String SURVEYS_SESSION			= "surveys";
	public static final String QUESTIONS_SESSION       	= "questions";
	public static final String ANSWERS_SESSION       	= "answers";
	public static final String ID_QUESTION 				= "idQuestion";
    public static final String ATTRIBUTEFORM   			= "form";
    
	public static final String VIEW             = "/WEB-INF/question/DisplayQuestion.jsp";
	public static final String LISTVIEW         = "/WEB-INF/survey/SurveyList.jsp";
	
	private AnswerDao          m_answerDao;
    
	
    public void init() throws ServletException 
    {
        this.m_answerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
    }
	
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(ID_QUESTION);
    	Question question = null;
    	HttpSession session = request.getSession();
    	
    	Long lId = Long.parseLong(sId);
    	
    	List<Question> questions = (ArrayList<Question>) session.getAttribute( QUESTIONS_SESSION );
    	
    	boolean bError = false;
    	if ( questions == null ) 
        {
    		bError = true;
        }
        else
        {
        	
        	question = find(questions, lId);
    		if( question != null)
    		{
    			session.setAttribute( ATTRIBUTEQUESTION, question );
    			List<Answer> listAnswers = m_answerDao.lister(lId);
                session.setAttribute( ANSWERS_SESSION, listAnswers );
    		}
    		else
    		{
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
