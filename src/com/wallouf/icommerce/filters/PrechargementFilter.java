package com.wallouf.icommerce.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallouf.icommerce.beans.Client;
import com.wallouf.icommerce.beans.Commande;
import com.wallouf.icommerce.dao.ClientDao;
import com.wallouf.icommerce.dao.CommandeDao;
import com.wallouf.icommerce.dao.DAOFactory;

/**
 * Servlet Filter implementation class PrechargementFilter
 */
@WebFilter( "/PrechargementFilter" )
public class PrechargementFilter implements Filter {
    public static final String CONF_DAO_FACTORY    = "daofactory";
    public static final String PARAM_listeCommande = "listeCommande";
    public static final String PARAM_listeClient   = "listeClient";

    private CommandeDao        commandeDao;

    private ClientDao          clientDao;

    /**
     * Default constructor.
     */
    public void init( FilterConfig config ) throws ServletException {
        // TODO Auto-generated constructor stub
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.commandeDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getCommandeDao();
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException,
            ServletException {
        // TODO Auto-generated method stub
        // place your code here
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        /*
         * Si la map des clients n'existe pas en session, alors l'utilisateur se
         * connecte pour la première fois et nous devons précharger en session
         * les infos contenues dans la BDD.
         */
        if ( session.getAttribute( PARAM_listeClient ) == null ) {
            /*
             * Récupération de la liste des clients existants, et enregistrement
             * en session
             */
            List<Client> listeClients = clientDao.lister();
            Map<Long, Client> mapClients = new HashMap<Long, Client>();
            for ( Client client : listeClients ) {
                mapClients.put( client.getId(), client );
            }
            session.setAttribute( PARAM_listeClient, mapClients );
        }

        /*
         * De même pour la map des commandes
         */
        if ( session.getAttribute( PARAM_listeCommande ) == null ) {
            /*
             * Récupération de la liste des commandes existantes, et
             * enregistrement en session
             */
            List<Commande> listeCommandes = commandeDao.lister();
            Map<Long, Commande> mapCommandes = new HashMap<Long, Commande>();
            for ( Commande commande : listeCommandes ) {
                mapCommandes.put( commande.getId(), commande );
            }
            session.setAttribute( PARAM_listeCommande, mapCommandes );
        }
        // pass the request along the filter chain
        chain.doFilter( request, response );
    }

}
