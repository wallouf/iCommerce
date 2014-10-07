package com.wallouf.icommerce.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wallouf.icommerce.beans.Client;
import com.wallouf.icommerce.beans.Commande;

public class CreationCommandeForm {

    public static final String  PARAM_clientType              = "optionsRadios";
    public static final String  PARAM_clientName              = "clientExistant";
    public static final String  PARAM_listeCommande           = "listeCommande";
    public static final String  PARAM_listeClient             = "listeClient";
    private static final String PARAM_modePaiementCommande    = "modePaiementCommande";
    private static final String PARAM_statutPaiementCommande  = "statutPaiementCommande";
    private static final String PARAM_modeLivraisonCommande   = "modeLivraisonCommande";
    private static final String PARAM_statutLivraisonCommande = "statutLivraisonCommande";
    private static final String PARAM_montantCommande         = "montantCommande";

    private String              clientType;
    private String              message;
    private Map<String, String> erreurs                       = new HashMap<String, String>();

    public String getclientType() {
        return clientType;
    }

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

    public Commande creerCommande( HttpServletRequest request, String chemin ) {
        /**
         * Creation des variables
         */
        clientType = request.getParameter( PARAM_clientType );
        String clientExistant = request.getParameter( PARAM_clientName );
        String modePaiementCommande = request.getParameter( PARAM_modePaiementCommande );
        String statutPaiementCommande = request.getParameter( PARAM_statutPaiementCommande );
        String modeLivraisonCommande = request.getParameter( PARAM_modeLivraisonCommande );
        String statutLivraisonCommande = request.getParameter( PARAM_statutLivraisonCommande );
        String montantCommande = request.getParameter( PARAM_montantCommande );
        Commande commande = new Commande();
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        if ( clientType != null && clientType.equalsIgnoreCase( "nouveau" ) ) {
            // creation dun client
            CreationClientForm clientForm = new CreationClientForm();
            Client nouveauClient = clientForm.creerClient( request, chemin );
            erreurs = clientForm.getErreurs();
            commande.setClient( nouveauClient );
        } else if ( clientType != null && clientType.equalsIgnoreCase( "existant" ) ) {
            Map<String, Client> listeClient = new HashMap<String, Client>();
            if ( session.getAttribute( PARAM_listeClient ) != null ) {
                try {
                    listeClient = (Map<String, Client>) session.getAttribute( PARAM_listeClient );
                } catch ( Exception e ) {
                }
            }
            if ( !listeClient.isEmpty() && listeClient.containsKey( clientExistant ) ) {
                commande.setClient( listeClient.get( clientExistant ) );
            } else {
                setErreur( PARAM_clientName, "Impossible de retrouver le client. Veuillez reessayer." );
            }
        }

        Map<String, Commande> listeCommande = new HashMap<String, Commande>();
        if ( session.getAttribute( PARAM_listeCommande ) != null ) {
            try {
                listeCommande = (Map<String, Commande>) session.getAttribute( PARAM_listeCommande );
            } catch ( Exception e ) {
            }
        }

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
            listeCommande.put( "" + Math.random(), commande );
            session.setAttribute( PARAM_listeCommande, listeCommande );
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
        if ( statutCommande != null && statutCommande.length() < 2 && statutCommande.length() > 0 ) {
            throw new Exception( "Le statut de paiement doit contenir au moins 2 caractères." );
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison != null && statutLivraison.length() < 2 && statutLivraison.length() > 0 ) {
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
