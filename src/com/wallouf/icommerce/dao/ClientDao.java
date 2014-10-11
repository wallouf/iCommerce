package com.wallouf.icommerce.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.wallouf.icommerce.entities.Client;

@Stateless
public class ClientDao {
    private static final String JPQL_INSERT = "SELECT c FROM Client c ORDER BY c.id";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "icommerce_PU" )
    private EntityManager       em;

    public void creer( Client client ) throws DAOException {
        try {
            em.persist( client );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public Client trouver( long id ) throws DAOException {
        try {
            return em.find( Client.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<Client> lister() throws DAOException {
        try {
            TypedQuery<Client> query = em.createQuery( JPQL_INSERT, Client.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Client client ) throws DAOException {
        try {
            em.remove( client );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

}