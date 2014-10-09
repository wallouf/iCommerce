package com.wallouf.icommerce.dao;

import static com.wallouf.icommerce.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.wallouf.icommerce.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.wallouf.icommerce.beans.Client;

public class ClientDaoImpl implements ClientDao {
    private DAOFactory          daoFactory;
    private static final String SQL_SELECT        = "SELECT id, nom, prenom, adresse, telephone, email, image FROM Client ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, nom, prenom, adresse, telephone, email, image FROM Client WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Client (nom, prenom, adresse, telephone, email, image) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Client WHERE id = ?";

    public ClientDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
        // TODO Auto-generated constructor stub
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance entre une
     * ligne issue de la table et un bean.
     */
    private static Client map( ResultSet resultSet ) throws SQLException {
        Client client = new Client();
        client.setId( resultSet.getLong( "id" ) );
        client.setNom( resultSet.getString( "nom" ) );
        client.setPrenom( resultSet.getString( "prenom" ) );
        client.setEmail( resultSet.getString( "email" ) );
        client.setAdresse( resultSet.getString( "adresse" ) );
        client.setTelephone( resultSet.getString( "telephone" ) );
        client.setImage( resultSet.getString( "image" ) );
        return client;
    }

    @Override
    public void creer( Client client ) throws DAOException {
        // TODO Auto-generated method stub
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    client.getNom(), client.getPrenom(), client.getAdresse(), client.getTelephone(), client.getEmail(),
                    client.getImage() );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /*
                 * Puis initialisation de la propriété id du bean Utilisateur
                 * avec sa valeur
                 */
                client.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public Client trouver( long id ) throws DAOException {
        // TODO Auto-generated method stub
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion,
                    SQL_SELECT_PAR_ID,
                    false, id );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if ( resultSet.next() ) {
                client = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return client;
    }

    @Override
    public Client lister() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void supprimer( Client client ) throws DAOException {
        // TODO Auto-generated method stub

    }

}
