package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Survey;
import dao.DAOFactory;
import dao.SurveyDao;

public class ListerQuestionnairesUtilisateur extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_LISTE		= "listeQuestionnaires";
	
    public static final String VUE              = "/WEB-INF/utilisateur/listeQuestionnaire.jsp";
    
    private SurveyDao           surveyDao;
    private List<Survey>		listSurveys;
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Survey */
        this.surveyDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getSurveyDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* À la réception d'une requête GET, affichage de la liste des clients */
    	listSurveys = surveyDao.lister();
    	
    	request.setAttribute( ATT_LISTE, listSurveys );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
