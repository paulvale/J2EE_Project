package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Resultat;
import beans.Utilisateur;
import dao.DAOFactory;
import dao.ResultDao;

public class AfficherScore extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_LISTE		= "listeScores";
    public static final String ATT_USER_SESSION = "utilisateurSession";
	
    public static final String VUE              = "/WEB-INF/utilisateur/afficherScore.jsp";
    
    private ResultDao           resultDao;
    private List<Resultat>		listScores;
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Survey */
        this.resultDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getResultDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* À la réception d'une requête GET, affichage de la liste des clients */
    	/* Récupération de la session depuis la requête */
    	HttpSession session = request.getSession();
    	Utilisateur utilisateur = (Utilisateur) session.getAttribute(ATT_USER_SESSION);
    	listScores = resultDao.listerScore(utilisateur.getId());
    	
    	request.setAttribute( ATT_LISTE, listScores );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
