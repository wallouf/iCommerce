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
import com.wallouf.icommerce.dao.ClientDao;
import com.wallouf.icommerce.dao.DAOFactory;
import com.wallouf.icommerce.forms.CreationClientForm;

/**
 * Servlet implementation class CreationClient
 */
@WebServlet( "/CreationClient" )
public class CreationClient extends HttpServlet {
    public static final String  CONF_DAO_FACTORY  = "daofactory";

    public static final String  PARAM_listeClient = "listeClient";
    private static final String ATT_client        = "client";
    public static final String  CHEMIN            = "chemin";
    private static final String ATT_clientForm    = "form";
    private static final String vueForm           = "/WEB-INF/creerClient.jsp";
    private static final String vueAfficher       = "/WEB-INF/afficherClient.jsp";

    private static final long   serialVersionUID  = 1L;

    private ClientDao           clientDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

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

        CreationClientForm clientForm = new CreationClientForm( clientDao );

        Client nouveauClient = clientForm.creerClient( request, chemin );

        request.setAttribute( ATT_client, nouveauClient );
        request.setAttribute( ATT_clientForm, clientForm );

        if ( clientForm.getErreurs().isEmpty() ) {

            /* Récupération de la session depuis la requête */
            HttpSession session = request.getSession();
            Map<Long, Client> listeClient = new HashMap<Long, Client>();
            if ( session.getAttribute( PARAM_listeClient ) != null ) {
                try {
                    listeClient = (Map<Long, Client>) session.getAttribute( PARAM_listeClient );
                } catch ( Exception e ) {
                }
            }
            listeClient.put( nouveauClient.getId(), nouveauClient );
            session.setAttribute( PARAM_listeClient, listeClient );
            this.getServletContext().getRequestDispatcher( vueAfficher ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( vueForm ).forward( request, response );
        }
    }

}
