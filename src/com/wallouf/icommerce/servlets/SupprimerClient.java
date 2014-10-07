package com.wallouf.icommerce.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallouf.icommerce.beans.Client;

/**
 * Servlet implementation class SupprimerClient
 */
@WebServlet( "/SupprimerClient" )
public class SupprimerClient extends HttpServlet {
    private static final long  serialVersionUID  = 1L;

    public static final String PARAM_listeClient = "listeClient";
    public static final String PARAM_nomClient   = "nomClient";
    public static final String VUE               = "/WEB-INF/listerClient.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerClient() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        /* Récupération de la session depuis la requête */
        String nom = request.getParameter( PARAM_nomClient );
        if ( nom != null && !nom.isEmpty() ) {
            HttpSession session = request.getSession();
            Map<String, Client> listeClient = new HashMap<String, Client>();
            if ( session.getAttribute( PARAM_listeClient ) != null ) {
                try {
                    listeClient = (Map<String, Client>) session.getAttribute( PARAM_listeClient );
                } catch ( Exception e ) {
                }
            }
            listeClient.remove( nom );
            session.setAttribute( PARAM_listeClient, listeClient );
        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
