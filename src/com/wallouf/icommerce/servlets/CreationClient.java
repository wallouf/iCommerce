package com.wallouf.icommerce.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallouf.icommerce.beans.Client;
import com.wallouf.icommerce.forms.CreationClientForm;

/**
 * Servlet implementation class CreationClient
 */
@WebServlet( "/CreationClient" )
public class CreationClient extends HttpServlet {

    private static final String ATT_client       = "client";
    public static final String  CHEMIN           = "chemin";
    private static final String ATT_clientForm   = "form";
    private static final String vueForm          = "/WEB-INF/creerClient.jsp";
    private static final String vueAfficher      = "/WEB-INF/afficherClient.jsp";

    private static final long   serialVersionUID = 1L;

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

        this.getServletContext().getRequestDispatcher( vueForm ).forward( request, response );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        CreationClientForm clientForm = new CreationClientForm();

        Client nouveauClient = clientForm.creerClient( request, chemin );

        request.setAttribute( ATT_client, nouveauClient );
        request.setAttribute( ATT_clientForm, clientForm );
        if ( clientForm.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( vueAfficher ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( vueForm ).forward( request, response );
        }
    }

}
