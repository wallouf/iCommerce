package com.wallouf.icommerce.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wallouf.icommerce.beans.Client;
import com.wallouf.icommerce.beans.Commande;

public class CreationCommandeForm {

    private static final String ATT_client                    = "client";
    private static final String PARAM_modePaiementCommande    = "modePaiementCommande";
    private static final String PARAM_statutPaiementCommande  = "statutPaiementCommande";
    private static final String PARAM_modeLivraisonCommande   = "modeLivraisonCommande";
    private static final String PARAM_statutLivraisonCommande = "statutLivraisonCommande";
    private static final String PARAM_montantCommande         = "montantCommande";

    private String              message;
    private Map<String, String> erreurs                       = new HashMap<String, String>();

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }

    public Commande creerCommande( HttpServletRequest request ) {
        /**
         * Creation des variables
         */
        String modePaiementCommande = request.getParameter( PARAM_modePaiementCommande );
        String statutPaiementCommande = request.getParameter( PARAM_statutPaiementCommande );
        String modeLivraisonCommande = request.getParameter( PARAM_modeLivraisonCommande );
        String statutLivraisonCommande = request.getParameter( PARAM_statutLivraisonCommande );
        String montantCommande = request.getParameter( PARAM_montantCommande );
        Commande commande = new Commande();
        commande.setClient( (Client) request.getAttribute( ATT_client ) );

        return commande;
    }

}
