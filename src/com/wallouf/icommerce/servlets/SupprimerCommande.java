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

import com.wallouf.icommerce.beans.Commande;

/**
 * Servlet implementation class SupprimerCommande
 */
@WebServlet( "/SupprimerCommande" )
public class SupprimerCommande extends HttpServlet {
    public static final String PARAM_listeCommande = "listeCommande";
    public static final String PARAM_indexCommande = "indexCommande";
    public static final String VUE                 = "/WEB-INF/listerCommande.jsp";
    private static final long  serialVersionUID    = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerCommande() {
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
        String index = request.getParameter( PARAM_indexCommande );
        if ( index != null && !index.isEmpty() ) {
            HttpSession session = request.getSession();
            Map<String, Commande> listeCommande = new HashMap<String, Commande>();
            if ( session.getAttribute( PARAM_listeCommande ) != null ) {
                try {
                    listeCommande = (Map<String, Commande>) session.getAttribute( PARAM_listeCommande );
                } catch ( Exception e ) {
                }
            }
            listeCommande.remove( index );
            session.setAttribute( PARAM_listeCommande, listeCommande );
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
