package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SurveyList extends HttpServlet 
{
    public static final String VIEW        = "/WEB-INF/survey/SurveyList.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
        /* À la réception d'une requête GET, affichage de la liste des questionnaires */
        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
    }
}