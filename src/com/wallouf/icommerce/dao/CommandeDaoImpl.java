package com.wallouf.icommerce.dao;

import static com.wallouf.icommerce.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.wallouf.icommerce.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.wallouf.icommerce.beans.Commande;

public class CommandeDaoImpl implements CommandeDao {
    private DAOFactory          daoFactory;
    private static final String SQL_SELECT        = "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande ORDER BY id";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Commande (id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Commande WHERE id = ?";

    public CommandeDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
        // TODO Auto-generated co4gnstructor stub
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private Commande map( ResultSet resultSet ) throws SQLException {
        Commande commande = new Commande();
        commande.setId( resultSet.getLong( "id" ) );
        commande.setClient( this.daoFactory.getClientDao().trouver( resultSet.getLong( "id" ) ) );
        commande.setModeDeLivraison( resultSet.getString( "mode_livraison" ) );
        commande.setModeDePaiement( resultSet.getString( "mode_paiement" ) );
        commande.setStatutDeLivraison( resultSet.getString( "statut_livraison" ) );
        commande.setModeDePaiement( resultSet.getString( "statut_paiement" ) );
        commande.setMontant( resultSet.getDouble( "montant" ) );
        commande.setDate( resultSet.getTimestamp( "date" ) );
        return commande;
    }

    @Override
    public void creer( Commande commande ) throws DAOException {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion, SQL_INSERT, true,
                    commande.getClient().getId(), commande.getDate(), commande.getMontant(),
                    commande.getModeDePaiement(), commande.getStatutDePaiement(), commande.getModeDeLivraison(),
                    commande.getStatutDeLivraison() );
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
                commande.setId( valeursAutoGenerees.getLong( 1 ) );
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
    public Commande trouver( long id ) throws DAOException {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Commande commande = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) initialisationRequetePreparee( connexion,
                    SQL_SELECT_PAR_ID,
                    false, id );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if ( resultSet.next() ) {
                commande = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return commande;
    }

    @Override
    public Commande lister() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void supprimer( Commande commande ) throws DAOException {
        // TODO Auto-generated method stub

    }

}
