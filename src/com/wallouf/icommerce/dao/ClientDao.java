package com.wallouf.icommerce.dao;

import com.wallouf.icommerce.beans.Client;

public interface ClientDao {

    void creer( Client client ) throws DAOException;

    Client trouver( long id ) throws DAOException;

    Client lister() throws DAOException;

    void supprimer( Client client ) throws DAOException;

}
