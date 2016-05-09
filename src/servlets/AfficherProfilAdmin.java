package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;

public class AfficherProfilAdmin extends HttpServlet {
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_USER_SESSION = "utilisateurSession";
    public static final String VUE              = "/WEB-INF/admin/afficherProfilAdmin.jsp";
    public Utilisateur myUser;

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
            /* Affichage de la page du profil */
    		/* Récupération de la session depuis la requête */
        	HttpSession session = request.getSession();
        	
        	if(myUser == null) {
        		if(session.getAttribute(ATT_USER_SESSION)!= null ){
            		request.setAttribute( ATT_USER_SESSION, session.getAttribute(ATT_USER_SESSION) );
        		}
        	}
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}