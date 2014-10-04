package com.wallouf.icommerce.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallouf.icommerce.beans.Commande;
import com.wallouf.icommerce.forms.CreationCommandeForm;

/**
 * Servlet implementation class CreationCommande
 */
@WebServlet( "/CreationCommande" )
public class CreationCommande extends HttpServlet {

    private static final String ATT_commande     = "commande";
    private static final String ATT_commandeForm = "form";
    private static final String vueForm          = "/WEB-INF/creerCommande.jsp";
    private static final String vueAfficher      = "/WEB-INF/afficherCommande.jsp";

    private static final long   serialVersionUID = 1L;

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
        this.getServletContext().getRequestDispatcher( vueForm ).forward( request, response );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub

        CreationCommandeForm commandeForm = new CreationCommandeForm();
        Commande nouvelleCommande = commandeForm.creerCommande( request );

        request.setAttribute( ATT_commande, nouvelleCommande );
        request.setAttribute( ATT_commandeForm, commandeForm );
        if ( commandeForm.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher( vueAfficher ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( vueForm ).forward( request, response );
        }
    }

}
