package com.wallouf.icommerce.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.wallouf.icommerce.entities.Commande;

@Stateless
public class CommandeDao {
    private static final String JPQL_INSERT = "SELECT c FROM Commande c ORDER BY c.id";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "icommerce_PU" )
    private EntityManager       em;

    public void creer( Commande commande ) throws DAOException {
        try {
            em.persist( commande );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public Commande trouver( long id ) throws DAOException {
        try {
            return em.find( Commande.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<Commande> lister() throws DAOException {
        try {
            TypedQuery<Commande> query = em.createQuery( JPQL_INSERT, Commande.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Commande commande ) throws DAOException {
        try {
            em.remove( commande );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

}