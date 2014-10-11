package com.wallouf.icommerce.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallouf.icommerce.dao.ClientDao;
import com.wallouf.icommerce.dao.DAOException;
import com.wallouf.icommerce.dao.DAOFactory;
import com.wallouf.icommerce.entities.Client;

/**
 * Servlet implementation class SupprimerClient
 */
@WebServlet( "/SupprimerClient" )
public class SupprimerClient extends HttpServlet {
    private static final long  serialVersionUID  = 1L;
    public static final String CONF_DAO_FACTORY  = "daofactory";

    public static final String PARAM_listeClient = "listeClient";
    public static final String PARAM_idClient    = "idClient";
    public static final String VUE               = "/WEB-INF/listerClient.jsp";
    private ClientDao          clientDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

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
        Long id = Long.parseLong( getValeurParametre( request, PARAM_idClient ) );
        if ( id != null ) {
            // suppression
            HttpSession session = request.getSession();
            Map<Long, Client> listeClient = (Map<Long, Client>) session.getAttribute( PARAM_listeClient );
            Client client = listeClient.get( id );
            if ( client != null ) {
                try {
                    clientDao.supprimer( client );
                    listeClient.remove( id );
                } catch ( DAOException e ) {
                    e.printStackTrace();
                }
                session.setAttribute( PARAM_listeClient, listeClient );
            }
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

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }

}
