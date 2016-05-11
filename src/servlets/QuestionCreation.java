package servlets;

import java.io.IOException;
import java.util.HashMap;
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

    public static final String VIEW   			= "/WEB-INF/question/DisplayQuestion.jsp";
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
            /* Alors récupération de la map des clients dans la session */
            HttpSession session = request.getSession();
            Map<String, Question> questions = (HashMap<String, Question>) session.getAttribute( QUESTIONS_SESSION );
            /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
            if ( null == questions ) 
            {
            	questions = new HashMap<String, Question>();
            }
            /* Puis ajout de la question courante dans la map
             La clé l'intitulé de la question, on suppose qu'il ne y avoir 2 questions identiques pour le moment */
            questions.put( question.getText(), question );
            /* Et enfin (ré)enregistrement de la map en session */
            session.setAttribute( QUESTIONS_SESSION, questions );
    	}
        
        if ( form.getErrors().isEmpty() ) 
        {
            /* Si aucune erreur, alors affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
        } 
        else 
        {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( FORMVIEW ).forward( request, response );
        }
    }
}
