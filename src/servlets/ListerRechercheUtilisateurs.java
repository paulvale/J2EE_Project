package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import beans.Utilisateur;

public class ListerRechercheUtilisateurs extends HttpServlet {
    public static final String ATT_LISTE		= "listeUtilisateurs";

    public static final String VUE        = "/WEB-INF/admin/listerRechercheUtilisateurs.jsp";
    
    private List<Utilisateur>		utilisateursListe;

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {    	
    	/* Récupération du paramètre */
    	utilisateursListe = (List<Utilisateur>) request.getAttribute(ATT_LISTE);

    	request.setAttribute( ATT_LISTE, utilisateursListe );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* Récupération du paramètre */
    	utilisateursListe = (List<Utilisateur>) request.getAttribute(ATT_LISTE);

    	request.setAttribute( ATT_LISTE, utilisateursListe );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}