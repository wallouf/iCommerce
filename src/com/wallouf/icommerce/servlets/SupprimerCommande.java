package com.wallouf.icommerce.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallouf.icommerce.beans.Commande;
import com.wallouf.icommerce.dao.CommandeDao;
import com.wallouf.icommerce.dao.DAOException;
import com.wallouf.icommerce.dao.DAOFactory;

/**
 * Servlet implementation class SupprimerCommande
 */
@WebServlet( "/SupprimerCommande" )
public class SupprimerCommande extends HttpServlet {
    public static final String CONF_DAO_FACTORY    = "daofactory";
    public static final String PARAM_listeCommande = "listeCommande";
    public static final String PARAM_idCommande    = "idCommande";
    public static final String VUE                 = "/WEB-INF/listerCommande.jsp";
    private static final long  serialVersionUID    = 1L;

    private CommandeDao        commandeDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.commandeDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
    }

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
        Long id = Long.parseLong( getValeurParametre( request, PARAM_idCommande ) );
        if ( id != null ) {
            HttpSession session = request.getSession();
            Map<Long, Commande> listeCommande = (Map<Long, Commande>) session.getAttribute( PARAM_listeCommande );
            Commande commande = listeCommande.get( id );
            if ( commande != null ) {
                try {
                    commandeDao.supprimer( commande );
                    listeCommande.remove( id );
                } catch ( DAOException e ) {
                    e.printStackTrace();
                }
                session.setAttribute( PARAM_listeCommande, listeCommande );
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
