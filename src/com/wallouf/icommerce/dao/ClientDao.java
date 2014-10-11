package com.wallouf.icommerce.dao;

import java.util.List;

import com.wallouf.icommerce.entities.Client;

public interface ClientDao {

    void creer( Client client ) throws DAOException;

    Client trouver( long id ) throws DAOException;

    List<Client> lister() throws DAOException;

    void supprimer( Client client ) throws DAOException;

}
