package com.wallouf.icommerce.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallouf.icommerce.beans.Client;

/**
 * Servlet implementation class CreationClient
 */
@WebServlet( "/CreationClient" )
public class CreationClient extends HttpServlet {
    private static final String PARAM_nomClient       = "nomClient";
    private static final String PARAM_prenomClient    = "prenomClient";
    private static final String PARAM_adresseClient   = "adresseClient";
    private static final String PARAM_telephoneClient = "telephoneClient";
    private static final String PARAM_emailClient     = "emailClient";

    private static final String ATT_client            = "client";
    private static final String ATT_message           = "message";
    private static final String ATT_error             = "error";

    private static final long   serialVersionUID      = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationClient() {
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

        String message = "";
        boolean error = false;
        String vue = "/WEB-INF/afficherClient.jsp";
        /**
         * Verification presence informations
         */
        if ( nom == null || telephone == null
                || adresse == null ) {
            vue = "/WEB-INF/creerClient.jsp";
        } else if ( nom.trim().isEmpty() || telephone.trim().isEmpty()
                || adresse.trim().isEmpty() ) {

            message = "You need to fill all information !";
            error = true;
            vue = "/WEB-INF/creerClient.jsp";
        } else {
            message = "Client was successfully created !";
        }
        Client nouveauClient = new Client( nom, prenom, adresse, email, telephone );

        request.setAttribute( ATT_client, nouveauClient );
        request.setAttribute( ATT_message, message );
        request.setAttribute( ATT_error, error );

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
