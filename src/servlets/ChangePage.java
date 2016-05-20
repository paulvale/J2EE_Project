package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UtilisateurDao;
import dao.DAOFactory;

import beans.Utilisateur;
import forms.ConnexionForm;

public class ChangePage extends HttpServlet {
    
	public static final String VUE_1             = "/WEB-INF/utilisateur/ecran_stagiaire_1.jsp";
	public static final String VUE_2             = "/WEB-INF/utilisateur/ecran_stagiaire_2.jsp";
	public static final String VUE_3             = "/WEB-INF/utilisateur/ecran_stagiaire_3.jsp";
	public static final String VUE_4             = "/WEB-INF/utilisateur/ecran_stagiaire_4.jsp";
	public static final String VUE_PUBLIC        = "/WEB-INF/utilisateur/accespublic.jsp";
	public static final String ATT_SESSION_USER  = "utilisateurSession";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        HttpSession session = request.getSession();
        Object utilisateur = session.getAttribute(ATT_SESSION_USER);
        
        String URI = request.getRequestURI();
        request.setAttribute("URI", URI);
        
		if( (utilisateur!=null)){
			if(URI.equals("/SR03_Project2/1.changepage")){
				this.getServletContext().getRequestDispatcher( VUE_1 ).forward( request, response );
			} else if (URI.equals("/SR03_Project2/2.changepage")) {
                this.getServletContext().getRequestDispatcher( VUE_2 ).forward( request, response );
			} else if (URI.equals("/SR03_Project2/3.changepage")){
				this.getServletContext().getRequestDispatcher( VUE_3 ).forward( request, response );
			} else if (URI.equals("/SR03_Project2/4.changepage")){
				this.getServletContext().getRequestDispatcher( VUE_4 ).forward( request, response );
			}
		} else {
			this.getServletContext().getRequestDispatcher( VUE_PUBLIC ).forward( request, response );
		}
    }

}
