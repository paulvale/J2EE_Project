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
import beans.Question;
import beans.Survey;
import dao.DAOFactory;
import dao.QuestionDao;
import dao.SurveyDao;
import dao.AnswerDao;

public class QuestionnaireDisplay extends HttpServlet {

	public static final String CONF_DAO_FACTORY      	= "daofactory";
	public static final String ATTRIBUTEQUESTION		= "question";
	public static final String ATTRIBUTESURVEY			= "survey";
	public static final String QUESTIONS_SESSION       	= "questions";
	public static final String ANSWERS_SESSION          = "answers";
	public static final String ALL_ANSWERS_SESSION      = "allanswers";
	public static final String ID_SURVEY 				= "idSurvey";
	public static final String ID_QUESTION 				= "idQuestion";
	public static final String ATTRIBUTEFORM   			= "form";
	
	public static final String VIEW             = "/WEB-INF/utilisateur/afficherQuestionnaire.jsp";
	public static final String LISTVIEW         = "/WEB-INF/utilisateur/afficherProfilUtilisateur.jsp";
	
	private SurveyDao          m_surveyDao;
	private QuestionDao        m_questionDao;
	private AnswerDao          m_answerDao;
	
	public void init() throws ServletException  {
		/* R®¶cup®¶ration d'une instance de notre DAO Question */
	    this.m_surveyDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSurveyDao();
	    this.m_questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
	    this.m_answerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
	}
	
	 public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		 String sId = request.getParameter(ID_SURVEY);
		 Survey survey = null;
		 HttpSession session = request.getSession();
		 
		 Long lId = Long.parseLong(sId);
		 survey = m_surveyDao.find(lId);
		 session.setAttribute(ATTRIBUTESURVEY, survey);
		 
		 List<Question> questions = new ArrayList<Question>();
		 questions = m_questionDao.lister(lId);
		 
		 List<Answer> answers = new ArrayList<Answer>();
		 Map<Long, List<Answer>> allanswers = new HashMap<Long, List<Answer>>();
		 
		 for(int i=0; i < questions.size(); i++) {			 
			Long lIndex = questions.get(i).getId(); 
            answers = m_answerDao.lister(lIndex);
            allanswers.put(lIndex, answers);
		 }
		 
		 session.setAttribute(QUESTIONS_SESSION, questions);
		 session.setAttribute(ALL_ANSWERS_SESSION, allanswers);
		 
		 this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );	 
	 }	 
}
