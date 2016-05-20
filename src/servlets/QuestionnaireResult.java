package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import dao.ResultDao;
import dao.SurveyDao;
import forms.QuestionCreationForm;
import forms.ResultForm;
import dao.AnswerDao;

public class QuestionnaireResult extends HttpServlet {
	
	public static final String CONF_DAO_FACTORY      	= "daofactory";
	public static final String ATT      	            = "test";
	public static final String VIEW      	            = "/WEB-INF/utilisateur/afficherProfilUtilisateur.jsp";
	public static final String QUESTIONS_SESSION     	= "questions";
	public static final String ATTRIBUTESURVEY          = "survey";
	public static final String ATTRIBUTELISTE           = "liste";
	public static final String ATTRIBUTEREPONSES        = "reponses";
	public static final String ATTRIBUTESCORE           = "score";
	
	
	private ResultDao          m_resultDao;
	private AnswerDao          m_answerDao;
	
	public void init() throws ServletException {
        this.m_resultDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getResultDao();
        this.m_answerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
    }
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Long> liste = new ArrayList<Long>();
		Answer reponse = new Answer();
		List<Answer> reponses= new ArrayList<Answer>(); 
		float score = 0;
		
		//C'est pour conserver toutes les valeurs qui est transmit par la rêquete.
		Map<String, String[]> map = request.getParameterMap();
		
		
		//Conserver tous les ids de toutes les réponses de l'utilisateur dans list 
		Collection<String[]> list = map.values();
		//System.out.println();

	
		//Par rapport ça, on peut obtenir toutes les réponses de l'utilisateur, conservé dans la liste réponses.
		for(String[] id : list){
			//System.out.println(id[0]);
			String lId = id[0];//request.getParameter("answer.*");
			Long lIndex = Long.parseLong(lId);
			liste.add(lIndex);
			reponse=m_answerDao.find(lIndex);
			reponses.add(reponse);	
		}
		
		//Après, on conserve les réponses de l'utilisateur dans le tableau  
		
    	ResultForm form = new ResultForm(m_resultDao);
        form.createResult(reponses);
        
        Long lId = reponses.get(0).getIdQuestionnaireFK();
        score=form.calculResultat(reponses,lId);
		
		
		//request.setAttribute( ATT, lId );
		//request.setAttribute( "lIndex", lIndex );
	    //session.setAttribute(ATTRIBUTELISTE, liste);
	    //session.setAttribute(ATTRIBUTEREPONSES, reponses);
	    //session.setAttribute(ATTRIBUTESCORE, score);
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );		
	}
}
