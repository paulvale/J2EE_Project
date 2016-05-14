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

import beans.Answer;
import dao.DAOFactory;
import dao.AnswerDao;
import forms.AnswerCreationForm;

public class AnswerModification extends HttpServlet 
{
	public static final String 		CONF_DAO_FACTORY 	= "daofactory";
	
	public static final String 		ATTRIBUTEANSWER 	= "answer";
	public static final String 		ANSWERS_SESSION		= "answers";
    public static final String 		ID_ANSWER	 		= "idAnswer";
    public static final String 		ATTRIBUTEFORM   	= "form";
    public static final String 		ID_QUESTION 		= "idQuestion";
    private static final String 	TEXTFIELD       	= "textField";
    private static final String 	ERROREXISTENCE		= "Cette réponse existe déjà, il ne peut y avoir deux question avec le même intitulé.";
    
	public static final String 		VIEW            	= "/answerDisplay";
	public static final String 		QUESTIONVIEW        = "/questionDisplay";
	public static final String 		FORMVIEW	        = "/WEB-INF/answer/ModifyAnswer.jsp";
	
	private AnswerDao          	m_answerDao;
	
	public void init() throws ServletException 
    {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.m_answerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(ID_ANSWER);
		boolean bError = false;
		
		Answer answer = null;
		if(sId != null)
		{
	    	HttpSession session = request.getSession();
	    	

	    	Long lId = Long.parseLong(sId);
	    	
	    	List<Answer> answers = (ArrayList<Answer>) session.getAttribute( ANSWERS_SESSION );
	    	
	    	if ( answers == null ) 
	        {
	    		bError = true;
	        }
	        else
	        {
	        	
	        	answer = find(answers, lId);
	    		if( answer != null)
	    		{
	    			session.setAttribute( ATTRIBUTEANSWER, answer );
	    		}
	    		else
	    		{
	    			System.out.println("Pas de réponse avec cet id");
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
    		response.sendRedirect( request.getContextPath() + QUESTIONVIEW + "?" + ID_QUESTION + "=" + answer.getQuestion().getId().toString());
    	}	
    }
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
		
    	AnswerCreationForm form = new AnswerCreationForm(m_answerDao);
    	Answer answer = form.modifyAnswer(request);
    	
    	
    	request.setAttribute(ATTRIBUTEANSWER, answer);
    	request.setAttribute(ATTRIBUTEFORM, form);
    	List<Answer> answers = null;
    	
    	/* Récupération de la map des surveys dans la session */
    	HttpSession session = request.getSession();
    	
    	if(form.getErrors().isEmpty())
    	{
    		String idAnswer = answer.getId().toString();
    		
    		/* Puis ajout de la question courante dans la liste */
    		answers = (List<Answer>) session.getAttribute( ANSWERS_SESSION );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
    		remove(answers, answer.getId());
    		answers.add(answer);
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( ANSWERS_SESSION, answers );
            response.sendRedirect( request.getContextPath() + VIEW + "?" + ID_ANSWER + "=" + idAnswer);
    	}
    	else
    	{	
    		this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
    	}
    }
	
	public Answer find(List<Answer> answers, Long lId)
	{
		Answer answer = null;
		for(int i = 0; i < answers.size(); i++)
		{
			if(answers.get(i).getId().equals(lId))
			{
				answer = answers.get(i);
				break;
			}
		}
		return answer;
	}
	
	public List<Answer> remove(List<Answer> answers, Long lId)
	{
		Answer answer = null;
		for(int i = 0; i < answers.size(); i++)
		{
			if(answers.get(i).getId().equals(lId))
			{
				answer = answers.get(i);
				break;
			}
		}
		answers.remove(answer);
		return answers;
	}
}
