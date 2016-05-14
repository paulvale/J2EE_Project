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
import dao.DAOException;
import dao.DAOFactory;

public class AnswerDeletion extends HttpServlet 
{
    public static final String CONF_DAO_FACTORY 	= "daofactory";
    public static final String ID_ANSWER  			= "idAnswer";
    public static final String ANSWERS_SESSION  	= "answers";
    public static final String ID_QUESTION			= "idQuestion";

    public static final String VIEW              = "/questionDisplay";

    private AnswerDao          answerDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.answerDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAnswerDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* Récupération du paramètre */
        String idAnswer = getValeurParametre( request, ID_ANSWER );
        Long id = Long.parseLong( idAnswer );
        
        String idQuestion = getValeurParametre( request, ID_QUESTION );

        /* Récupération de la Map des questions enregistrés en session */
        HttpSession session = request.getSession();
        List<Answer> answers = (ArrayList<Answer>) session.getAttribute( ANSWERS_SESSION );

        /* Si l'id du client et la Map des clients ne sont pas vides */
        if ( id != null && answers != null ) 
        {
            try 
            {
            	Answer answer = find(answers, id);
                /* Alors suppression du client de la BDD */
                answerDao.delete( answer );
                /* Puis suppression du client de la Map */
                answers.remove( answer );
            } 
            catch ( DAOException e ) 
            {
                e.printStackTrace();
            }
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( ANSWERS_SESSION, answers );
        }
        
        /* Redirection vers la fiche récapitulative */
        response.sendRedirect( request.getContextPath() + VIEW + "?" + ID_QUESTION + "=" + idQuestion);
    }

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String fieldName ) 
    {
        String valeur = request.getParameter( fieldName );
        if ( valeur == null || valeur.trim().length() == 0 ) 
        {
            return null;
        } 
        else 
        {
            return valeur;
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