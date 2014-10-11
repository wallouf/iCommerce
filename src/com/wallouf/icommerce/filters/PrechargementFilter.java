package com.wallouf.icommerce.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wallouf.icommerce.dao.ClientDao;
import com.wallouf.icommerce.dao.CommandeDao;
import com.wallouf.icommerce.entities.Client;
import com.wallouf.icommerce.entities.Commande;

/**
 * Servlet Filter implementation class PrechargementFilter
 */
@WebFilter( urlPatterns = "/*" )
public class PrechargementFilter implements Filter {
    public static final String PARAM_listeCommande = "listeCommande";
    public static final String PARAM_listeClient   = "listeClient";

    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private CommandeDao        commandeDao;

    // Injection de notre EJB (Session Bean Stateless)
    @EJB
    private ClientDao          clientDao;

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

    @Override
    public void init( FilterConfig arg0 ) throws ServletException {
        // TODO Auto-generated method stub

    }

}
