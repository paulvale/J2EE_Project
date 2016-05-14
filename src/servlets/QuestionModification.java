package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Question;
import dao.DAOFactory;
import dao.QuestionDao;
import forms.QuestionCreationForm;

public class QuestionModification extends HttpServlet 
{
	public static final String 		CONF_DAO_FACTORY 	= "daofactory";
	
	public static final String 		ATTRIBUTEQUESTION 	= "question";
	public static final String 		QUESTIONS_SESSION	= "questions";
	public static final String 		ALL_QUESTIONS_SESSION 	= "allQuestions";
    public static final String 		ID_QUESTION 		= "idQuestion";
    public static final String 		ATTRIBUTEFORM   	= "form";
    public static final String 		ID_SURVEY 			= "idParameter";
    private static final String 	TEXTFIELD       	= "textField";
    private static final String 	ERROREXISTENCE		= "Cette question existe déjà, il ne peut y avoir deux question avec le même intitulé.";
    
	public static final String 		VIEW            	= "/questionDisplay";
	public static final String 		SURVEYVIEW         	= "/surveyDisplay";
	public static final String 		FORMVIEW	        = "/WEB-INF/question/ModifyQuestion.jsp";
	
	private QuestionDao          	m_questionDao;
	
	public void init() throws ServletException 
    {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.m_questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(ID_QUESTION);
		boolean bError = false;
		
		Question question = null;
		if(sId != null)
		{
	    	HttpSession session = request.getSession();
	    	

	    	Long lId = Long.parseLong(sId);
	    	
	    	List<Question> questions = (ArrayList<Question>) session.getAttribute( QUESTIONS_SESSION );
	    	
	    	if ( questions == null ) 
	        {
	    		System.out.println("Pas de liste en sessions");
	    		bError = true;
	        }
	        else
	        {
	        	
	        	question = find(questions, lId);
	    		if( question != null)
	    		{
	    			session.setAttribute( ATTRIBUTEQUESTION, question );
	    		}
	    		else
	    		{
	    			System.out.println("Pas de question avec cet id");
	    			bError = true;
	    		}
	        }
		}
    	
    	
    	if(!bError)
    	{
    		this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
    	}
    	else
    	{
    		response.sendRedirect( request.getContextPath() + SURVEYVIEW + "?" + ID_SURVEY + "=" + question.getSurvey().getId().toString());
    	}	
    }
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	QuestionCreationForm form = new QuestionCreationForm(m_questionDao);
    	Question question = form.modifyQuestion(request);
    	
    	
    	request.setAttribute(ATTRIBUTEQUESTION, question);
    	request.setAttribute(ATTRIBUTEFORM, form);
    	List<Question> questions = null;
    	
    	/* Récupération de la map des surveys dans la session */
    	HttpSession session = request.getSession();
    	
    	if(form.getErrors().isEmpty())
    	{
    		String idQuestion = question.getId().toString();
    		/* Puis ajout de la question courante dans la liste */
    		questions = (List<Question>) session.getAttribute( QUESTIONS_SESSION );
    		Map<Long, Question> allQuestions = (HashMap<Long, Question>) session.getAttribute(ALL_QUESTIONS_SESSION);
    		remove(questions, question.getId());
    		questions.add(question);
    		allQuestions.remove(question.getId());
    		allQuestions.put(question.getId(), question);
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( QUESTIONS_SESSION, questions );
            response.sendRedirect( request.getContextPath() + VIEW + "?" + ID_QUESTION + "=" + idQuestion);
    	}
    	else
    	{	
    		this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
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
	
	public List<Question> remove(List<Question> questions, Long lId)
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
		questions.remove(question);
		return questions;
	}
}
