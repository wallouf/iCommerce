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

        try {
            validationModePaiement( modePaiementCommande );
        } catch ( Exception e ) {
            setErreur( PARAM_modePaiementCommande, e.getMessage() );
        }
        commande.setModeDePaiement( modePaiementCommande );

        try {
            validationStatutPaiement( statutPaiementCommande );
        } catch ( Exception e ) {
            setErreur( PARAM_statutPaiementCommande, e.getMessage() );
        }
        commande.setStatutDePaiement( statutPaiementCommande );

        try {
            validationModeLivraison( modeLivraisonCommande );
        } catch ( Exception e ) {
            setErreur( PARAM_modeLivraisonCommande, e.getMessage() );
        }
        commande.setModeDeLivraison( modeLivraisonCommande );

        try {
            validationStatutLivraison( statutLivraisonCommande );
        } catch ( Exception e ) {
            setErreur( PARAM_statutLivraisonCommande, e.getMessage() );
        }
        commande.setStatutDeLivraison( statutLivraisonCommande );

        try {
            validationMontant( montantCommande );
            commande.setMontant( Double.valueOf( montantCommande ) );
        } catch ( Exception e ) {
            setErreur( PARAM_montantCommande, e.getMessage() );
            commande.setMontant( 0.0 );
        }

        if ( erreurs.isEmpty() ) {
            message = "Succès de la création de la commande.";
        } else {
            message = "Échec de la création de la commande.";
        }
        return commande;
    }

    private void validationModePaiement( String modePaiementCommande ) throws Exception {
        if ( modePaiementCommande != null && modePaiementCommande.length() < 2 ) {
            throw new Exception( "Le mode de paiement doit contenir au moins 2 caractères." );
        } else if ( modePaiementCommande == null ) {
            throw new Exception( "Merci de saisir un mode de paiement." );
        }
    }

    private void validationModeLivraison( String modeLivraisonCommande ) throws Exception {
        if ( modeLivraisonCommande != null && modeLivraisonCommande.length() < 2 ) {
            throw new Exception( "Le mode de livraison doit contenir au moins 2 caractères." );
        } else if ( modeLivraisonCommande == null ) {
            throw new Exception( "Merci de saisir un mode de livraison." );
        }
    }

    private void validationStatutPaiement( String statutCommande ) throws Exception {
        if ( statutCommande != null && statutCommande.length() < 2 ) {
            throw new Exception( "Le statut de paiement doit contenir au moins 2 caractères." );
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison != null && statutLivraison.length() < 2 ) {
            throw new Exception( "Le statut de livraison doit contenir au moins 2 caractères." );
        }
    }

    private void validationMontant( String montantCommande ) throws Exception {
        Double montant;
        try {
            montant = Double.valueOf( montantCommande );
        } catch ( Exception e ) {
            throw new Exception( "Le montant doit être un chiffre." );
        }
        if ( montant.isNaN() ) {
            throw new Exception( "Le montant doit être un chiffre." );
        } else if ( montant <= 0.0 ) {
            throw new Exception( "Le montant doit être positif et supérieur à zéro." );
        }
    }

}
