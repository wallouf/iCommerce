package com.wallouf.icommerce.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallouf.icommerce.beans.Client;
import com.wallouf.icommerce.beans.Commande;

/**
 * Servlet implementation class CreationCommande
 */
@WebServlet( "/CreationCommande" )
public class CreationCommande extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationCommande() {
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
        /**
         * Verification presence informations
         */
        if ( request.getParameter( "nomClient" ) == null || request.getParameter( "nomClient" ).isEmpty()
                || request.getParameter( "adresseClient" ) == null
                || request.getParameter( "adresseClient" ).isEmpty()
                || request.getParameter( "telephoneClient" ) == null
                || request.getParameter( "telephoneClient" ).isEmpty()
                || request.getParameter( "montantCommande" ) == null
                || request.getParameter( "montantCommande" ).isEmpty()
                || request.getParameter( "modePaiementCommande" ) == null
                || request.getParameter( "modePaiementCommande" ).isEmpty()
                || request.getParameter( "modeLivraisonCommande" ) == null
                || request.getParameter( "modeLivraisonCommande" ).isEmpty() ) {
            this.getServletContext().getRequestDispatcher( "/WEB-INF/creerCommande.jsp" ).forward( request, response );
        } else {
            Client nouveauClient = new Client( (String) request.getParameter( "nomClient" ),
                    (String) request.getParameter( "prenomClient" ), (String) request.getParameter( "adresseClient" ),
                    (String) request.getParameter( "emailClient" ), (String) request.getParameter( "telephoneClient" ) );
            request.setAttribute( "client", nouveauClient );
            Commande nouvelleCommande = new Commande( nouveauClient,
                    (String) request.getParameter( "modePaiementCommande" ),
                    (String) request.getParameter( "statutPaiementCommande" ),
                    (String) request.getParameter( "modeLivraisonCommande" ),
                    (String) request.getParameter( "statutLivraisonCommande" ), Double.valueOf( request
                            .getParameter( "montantCommande" ) ) );
            request.setAttribute( "commande", nouvelleCommande );
            this.getServletContext().getRequestDispatcher( "/WEB-INF/afficherCommande.jsp" )
                    .forward( request, response );
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
    }

}
