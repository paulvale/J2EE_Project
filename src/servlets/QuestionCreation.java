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
import dao.DAOFactory;
import dao.QuestionDao;
import forms.QuestionCreationForm;


public class QuestionCreation extends HttpServlet 
{

	public static final String CONF_DAO_FACTORY 	= "daofactory";
	public static final String ATTRIBUTEQUESTION 	= "question";
    public static final String ATTRIBUTEFORM     	= "form";
    public static final String QUESTIONS_SESSION 	= "questions";
    public static final String ALL_QUESTIONS_SESSION 	= "allQuestions";
    public static final String ID_QUESTION			= "idQuestion";

    public static final String VIEW   			= "/questionDisplay";
    public static final String FORMVIEW     	= "/WEB-INF/question/CreateQuestion.jsp";
    
    private QuestionDao          m_questionDao;
    
    public void init() throws ServletException 
    {
        /* Récupération d'une instance de notre DAO Question */
        this.m_questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
    }
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	/* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	
    	/* Préparation de l'objet formulaire */
    	QuestionCreationForm form = new QuestionCreationForm(m_questionDao);

        /* Traitement de la requête et récupération du bean en résultant */
        Question question = form.createQuestion( request );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATTRIBUTEQUESTION, question );
        request.setAttribute( ATTRIBUTEFORM, form );
        
        if ( form.getErrors().isEmpty() ) 
        {
        	String idQuestion = question.getId().toString();
            /* Alors récupération de la map des clients dans la session */
            HttpSession session = request.getSession();
            List<Question> questions = (ArrayList<Question>) session.getAttribute( QUESTIONS_SESSION );
            Map<Long, Question> allQuestions = (HashMap<Long, Question>) session.getAttribute(ALL_QUESTIONS_SESSION);
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            if ( null == questions ) 
            {
            	questions = new ArrayList<Question>();
            }
            /* Puis ajout de la question courante dans la map
             La clé l'intitulé de la question, on suppose qu'il ne y avoir 2 questions identiques pour le moment */
            questions.add( question );
            allQuestions.put(question.getId(), question);
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( QUESTIONS_SESSION, questions );
            session.setAttribute( ALL_QUESTIONS_SESSION, allQuestions );
            
            /* Si aucune erreur, alors affichage de la fiche récapitulative */
            response.sendRedirect( request.getContextPath() + VIEW + "?" + ID_QUESTION + "=" + idQuestion);
    	}
        else 
        {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
        }
    }
}
