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
import dao.DAOFactory;
import dao.SurveyDao;
import forms.SurveyCreationForm;

public class SurveyModification extends HttpServlet 
{
	public static final String 		CONF_DAO_FACTORY 	= "daofactory";
	
	public static final String 		ATTRIBUTESURVEY 	= "survey";
	public static final String 		SURVEYS_SESSION		= "surveys";
    public static final String 		PARAMID 			= "idParameter";
    public static final String 		ATTRIBUTEFORM   	= "form";
    
    private static final String 	SUBJECTFIELD       	= "subjectField";
    private static final String 	ERROREXISTENCE		= "Ce sujet existe déjà, il ne peut y avoir deux questionnaires avec le même sujet.";
    
	public static final String 		VIEW            	= "/WEB-INF/survey/DisplaySurvey.jsp";
	public static final String 		LISTVIEW         	= "/WEB-INF/survey/SurveyList.jsp";
	public static final String 		FORMVIEW	        = "/WEB-INF/survey/ModifySurvey.jsp";
	
	private SurveyDao          		m_surveyDao;
	
	public void init() throws ServletException 
    {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.m_surveyDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSurveyDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
		
		String sId = request.getParameter(PARAMID);
		boolean bError = false;
		
		if(sId != null)
		{
			Survey survey = null;
	    	HttpSession session = request.getSession();
	    	

	    	Long lId = Long.parseLong(sId);
	    	
	    	Map<Long, Survey> surveys = (HashMap<Long, Survey>) session.getAttribute( SURVEYS_SESSION );
	    	
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
	    		}
	    		else
	    		{
	    			System.out.println("Pas de questionnaire avec cet id");
	    			bError = false;
	    		}
	        }
		}
    	
    	
    	if(!bError)
    	{
    		this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
    	}
    	else
    	{
    		this.getServletContext().getRequestDispatcher( LISTVIEW ).forward( request, response );
    	}	
    }
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	SurveyCreationForm form = new SurveyCreationForm(m_surveyDao);
    	Survey survey = form.modifySurvey(request);
    	
    	
    	request.setAttribute(ATTRIBUTESURVEY, survey);
    	request.setAttribute(ATTRIBUTEFORM, form);
    	Map<Long, Survey> surveys = null;
    	
    	/* Récupération de la map des surveys dans la session */
    	HttpSession session = request.getSession();
    	
    	if ( form.getErrors().isEmpty() ) 
    	{
            surveys = (HashMap<Long, Survey>) session.getAttribute( SURVEYS_SESSION );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            surveys.remove(survey.getId());
            surveys.put(survey.getId(), survey);
    	}
    	
    	
    	if(form.getErrors().isEmpty())
    	{
    		/* Puis ajout du client courant dans la map */
            surveys.put( survey.getId(), survey );
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( SURVEYS_SESSION, surveys );
    		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
    	}
    	else
    	{	
    		this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
    	}
    }
}
