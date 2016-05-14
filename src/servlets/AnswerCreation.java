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

import beans.Answer;
import dao.AnswerDao;
import dao.DAOFactory;
import forms.AnswerCreationForm;
import javafx.util.Pair;

public class AnswerCreation extends HttpServlet 
{
	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATTRIBUTEANSWER 		= "answer";
    public static final String ATTRIBUTEFORM     	= "form";
    public static final String ANSWERS_SESSION 		= "answers";
    public static final String ID_ANSWER			= "idAnswer";

    public static final String VIEW   			= "/answerDisplay";
    public static final String FORMVIEW     	= "/WEB-INF/answer/CreateAnswer.jsp";
    
    private AnswerDao          m_answerDao;
    
    public void init() throws ServletException 
    {
        /* Récupération d'une instance de notre DAO Question */
        this.m_answerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
    }
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	/* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	/* Préparation de l'objet formulaire */
    	AnswerCreationForm form = new AnswerCreationForm(m_answerDao);

        /* Traitement de la requête et récupération du bean en résultant */
        Answer answer = form.createAnswer( request );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATTRIBUTEANSWER, answer );
        request.setAttribute( ATTRIBUTEFORM, form );
        
        if ( form.getErrors().isEmpty() ) 
        {
        	String idAnswer = answer.getId().toString();
            /* Alors récupération de la map des clients dans la session */
            HttpSession session = request.getSession();
            List<Answer> answers = (ArrayList<Answer>) session.getAttribute( ANSWERS_SESSION );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            if ( null == answers ) 
            {
            	answers = new ArrayList<Answer>();
            }
            
            answers.add( answer );
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( ANSWERS_SESSION, answers );
            /* Si aucune erreur, alors affichage de la fiche récapitulative */
            response.sendRedirect( request.getContextPath() + VIEW + "?" + ID_ANSWER + "=" + idAnswer);
    	}
        else 
        {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
        }
    }
}
