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
import com.wallouf.icommerce.dao.ClientDao;
import com.wallouf.icommerce.dao.CommandeDao;
import com.wallouf.icommerce.dao.DAOFactory;
import com.wallouf.icommerce.forms.CreationCommandeForm;

/**
 * Servlet implementation class CreationCommande
 */
@WebServlet( "/CreationCommande" )
public class CreationCommande extends HttpServlet {
    public static final String  CONF_DAO_FACTORY    = "daofactory";

    public static final String  PARAM_listeCommande = "listeCommande";
    private static final String ATT_commande        = "commande";
    public static final String  CHEMIN              = "chemin";
    private static final String ATT_commandeForm    = "form";
    private static final String vueForm             = "/WEB-INF/creerCommande.jsp";
    private static final String vueAfficher         = "/WEB-INF/afficherCommande.jsp";

    private static final long   serialVersionUID    = 1L;

    private CommandeDao         commandeDao;

    private ClientDao           clientDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.commandeDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

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
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        CreationCommandeForm commandeForm = new CreationCommandeForm( commandeDao, clientDao );
        Commande nouvelleCommande = commandeForm.creerCommande( request, chemin );

        request.setAttribute( ATT_commande, nouvelleCommande );
        request.setAttribute( ATT_commandeForm, commandeForm );
        if ( commandeForm.getErreurs().isEmpty() ) {
            HttpSession session = request.getSession();
            Map<Long, Commande> listeCommande = new HashMap<Long, Commande>();
            if ( session.getAttribute( PARAM_listeCommande ) != null ) {
                try {
                    listeCommande = (Map<Long, Commande>) session.getAttribute( PARAM_listeCommande );
                } catch ( Exception e ) {
                }
            }
            listeCommande.put( nouvelleCommande.getId(), nouvelleCommande );
            session.setAttribute( PARAM_listeCommande, listeCommande );
            this.getServletContext().getRequestDispatcher( vueAfficher ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( vueForm ).forward( request, response );
        }
    }

}
