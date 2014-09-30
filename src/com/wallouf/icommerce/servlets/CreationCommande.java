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
    private static final String PARAM_nomClient               = "nomClient";
    private static final String PARAM_prenomClient            = "prenomClient";
    private static final String PARAM_adresseClient           = "adresseClient";
    private static final String PARAM_telephoneClient         = "telephoneClient";
    private static final String PARAM_emailClient             = "emailClient";

    private static final String PARAM_modePaiementCommande    = "modePaiementCommande";
    private static final String PARAM_statutPaiementCommande  = "statutPaiementCommande";
    private static final String PARAM_modeLivraisonCommande   = "modeLivraisonCommande";
    private static final String PARAM_statutLivraisonCommande = "statutLivraisonCommande";
    private static final String PARAM_montantCommande         = "montantCommande";

    private static final String ATT_client                    = "client";
    private static final String ATT_commande                  = "commande";
    private static final String ATT_message                   = "message";
    private static final String ATT_error                     = "error";

    private static final long   serialVersionUID              = 1L;

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
         * Creation des variables
         */
        String nom = request.getParameter( PARAM_nomClient );
        String prenom = request.getParameter( PARAM_prenomClient );
        String adresse = request.getParameter( PARAM_adresseClient );
        String telephone = request.getParameter( PARAM_telephoneClient );
        String email = request.getParameter( PARAM_emailClient );

        String modePaiement = request.getParameter( PARAM_modePaiementCommande );
        String statutPaiement = request.getParameter( PARAM_statutPaiementCommande );
        String modeLivraison = request.getParameter( PARAM_modeLivraisonCommande );
        String statutLivraison = request.getParameter( PARAM_statutLivraisonCommande );
        Double montant = 0.0;

        String message = "";
        boolean error = false;
        String vue = "/WEB-INF/afficherCommande.jsp";
        /**
         * Verification presence informations
         */
        if ( nom == null || adresse == null || telephone == null || montant == null || modeLivraison == null
                || modePaiement == null ) {
            vue = "/WEB-INF/creerCommande.jsp";
        } else if ( nom.trim().isEmpty() || adresse.trim().isEmpty() || telephone.trim().isEmpty() || montant.isNaN()
                || modeLivraison.trim().isEmpty()
                || modePaiement.trim().isEmpty() ) {
            message = "You need to fill all information !";
            error = true;
            vue = "/WEB-INF/creerCommande.jsp";
        } else {
            message = "Your command was successfully created !";
        }
        /**
         * Verification du montant
         */
        if ( montant != null && !montant.isNaN() ) {
            montant = Double.valueOf( request.getParameter( PARAM_montantCommande ) );
        }

        Client nouveauClient = new Client( nom, prenom, adresse, email, telephone );
        Commande nouvelleCommande = new Commande( nouveauClient, modePaiement, statutPaiement, modeLivraison,
                statutLivraison, montant );

        request.setAttribute( ATT_client, nouveauClient );
        request.setAttribute( ATT_commande, nouvelleCommande );
        request.setAttribute( ATT_error, error );
        request.setAttribute( ATT_message, message );
        this.getServletContext().getRequestDispatcher( vue ).forward( request, response );
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
