package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;

public class ListerUtilisateurs extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_LISTE		= "listeUtilisateurs";

    public static final String VUE        = "/WEB-INF/admin/listerUtilisateurs.jsp";
    
    private UtilisateurDao          utilisateurDao;
    private List<Utilisateur>		utilisateursListe;
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, affichage de la liste des clients */
    	utilisateursListe = utilisateurDao.lister();
    	
    	request.setAttribute( ATT_LISTE, utilisateursListe );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}