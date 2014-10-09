package com.wallouf.icommerce.dao;

import com.wallouf.icommerce.beans.Commande;

public interface CommandeDao {

    void creer( Commande commande ) throws DAOException;

    Commande trouver( long id ) throws DAOException;

    Commande lister() throws DAOException;

    void supprimer( Commande commande ) throws DAOException;

}
