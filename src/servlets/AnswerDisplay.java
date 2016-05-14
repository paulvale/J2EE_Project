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


import beans.Answer;
import beans.Question;
import dao.DAOFactory;
import dao.AnswerDao;

public class AnswerDisplay extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY      	= "daofactory";
	public static final String ATTRIBUTEANSWER 			= "answer";
	public static final String ANSWERS_SESSION			= "answers";
    public static final String ID_ANSWER 				= "idAnswer";
    public static final String ATTRIBUTEFORM   			= "form";
    
	public static final String VIEW             = "/WEB-INF/answer/DisplayAnswer.jsp";
	public static final String LISTVIEW         = "/WEB-INF/survey/SurveyList.jsp";
	
    
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(ID_ANSWER);
    	Answer answer = null;
    	HttpSession session = request.getSession();
    	Long lId = null;
    	if(sId != null)
    	{
    		lId = Long.parseLong(sId);
    	}
    	
    	List<Answer> answers = (List<Answer>) session.getAttribute( ANSWERS_SESSION );
    	
    	boolean bError = false;
    	if ( answers == null ) 
        {
    		System.out.println("Pas de liste en session");
    		bError = true;
        }
        else
        {
        	
        	answer = find( answers, lId );
    		if( answer != null)
    		{
    			session.setAttribute( ATTRIBUTEANSWER, answer );
    		}
    		else
    		{
    			System.out.println("Error - Pas de réponse avec cet id");
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
	
}