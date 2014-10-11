package com.wallouf.icommerce.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.wallouf.icommerce.dao.ClientDao;
import com.wallouf.icommerce.dao.CommandeDao;
import com.wallouf.icommerce.entities.Client;
import com.wallouf.icommerce.entities.Commande;

public class CreationCommandeForm {

    private static final String PARAM_nomClient               = "nomClient";
    private static final String PARAM_nomClientErreur         = "Merci de saisir un autre nom, car ce compte existe déjà.";
    public static final String  PARAM_clientType              = "optionsRadios";
    public static final String  PARAM_clientId                = "clientExistant";
    public static final String  PARAM_listeClient             = "listeClient";
    private static final String PARAM_modePaiementCommande    = "modePaiementCommande";
    private static final String PARAM_statutPaiementCommande  = "statutPaiementCommande";
    private static final String PARAM_modeLivraisonCommande   = "modeLivraisonCommande";
    private static final String PARAM_statutLivraisonCommande = "statutLivraisonCommande";
    private static final String PARAM_montantCommande         = "montantCommande";

    private CommandeDao         commandeDao;
    private ClientDao           clientDao;
    private String              clientType;
    private String              message;
    private Map<String, String> erreurs                       = new HashMap<String, String>();

    public CreationCommandeForm( CommandeDao commandeDao, ClientDao clientDao ) {
        this.commandeDao = commandeDao;
        this.clientDao = clientDao;
    }

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
        Long clientExistant = Long.parseLong( request.getParameter( PARAM_clientId ) );
        String modePaiementCommande = request.getParameter( PARAM_modePaiementCommande );
        String statutPaiementCommande = request.getParameter( PARAM_statutPaiementCommande );
        String modeLivraisonCommande = request.getParameter( PARAM_modeLivraisonCommande );
        String statutLivraisonCommande = request.getParameter( PARAM_statutLivraisonCommande );
        String montantCommande = request.getParameter( PARAM_montantCommande );
        DateTime dt = new DateTime();
        Commande commande = new Commande();
        commande.setDate( dt );
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        if ( clientType != null && clientType.equalsIgnoreCase( "nouveau" ) ) {
            // creation dun client
            CreationClientForm clientForm = new CreationClientForm( clientDao );
            Client nouveauClient = clientForm.creerClient( request, chemin );
            erreurs = clientForm.getErreurs();
            commande.setClient( nouveauClient );
        } else if ( clientType != null && clientType.equalsIgnoreCase( "existant" ) ) {
            Map<Long, Client> listeClient = (Map<Long, Client>) session.getAttribute( PARAM_listeClient );
            if ( !listeClient.isEmpty() && listeClient.containsKey( clientExistant ) ) {
                commande.setClient( listeClient.get( clientExistant ) );
            } else {
                setErreur( PARAM_clientId, "Impossible de retrouver le client. Veuillez reessayer." );
            }
        }
        traiterMontant( montantCommande, commande );
        traiterModePaiement( modePaiementCommande, commande );
        traiterStatutPaiement( statutPaiementCommande, commande );
        traiterModeLivraison( modeLivraisonCommande, commande );
        traiterStatutLivraison( statutLivraisonCommande, commande );

        if ( erreurs.isEmpty() ) {
            message = "Succès de la création de la commande.";
            commandeDao.creer( commande );
        } else {
            message = "Échec de la création de la commande.";
        }
        return commande;
    }

    private void traiterMontant( String montant, Commande commande ) {
        double valeurMontant = -1;
        try {
            valeurMontant = validationMontant( montant );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_montantCommande, e.getMessage() );
        }
        commande.setMontant( valeurMontant );
    }

    private void traiterModePaiement( String modePaiement, Commande commande ) {
        try {
            validationModePaiement( modePaiement );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_modePaiementCommande, e.getMessage() );
        }
        commande.setModeDePaiement( modePaiement );
    }

    private void traiterStatutPaiement( String statutPaiement, Commande commande ) {
        try {
            validationStatutPaiement( statutPaiement );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_statutPaiementCommande, e.getMessage() );
        }
        commande.setStatutDePaiement( statutPaiement );
    }

    private void traiterModeLivraison( String modeLivraison, Commande commande ) {
        try {
            validationModeLivraison( modeLivraison );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_modeLivraisonCommande, e.getMessage() );
        }
        commande.setModeDeLivraison( modeLivraison );
    }

    private void traiterStatutLivraison( String statutLivraison, Commande commande ) {
        try {
            validationStatutLivraison( statutLivraison );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_statutLivraisonCommande, e.getMessage() );
        }
        commande.setStatutDeLivraison( statutLivraison );
    }

    private void validationModePaiement( String modePaiementCommande ) throws FormValidationException {
        if ( modePaiementCommande != null && modePaiementCommande.length() < 2 ) {
            throw new FormValidationException( "Le mode de paiement doit contenir au moins 2 caractères." );
        } else if ( modePaiementCommande == null ) {
            throw new FormValidationException( "Merci de saisir un mode de paiement." );
        }
    }

    private void validationModeLivraison( String modeLivraisonCommande ) throws FormValidationException {
        if ( modeLivraisonCommande != null && modeLivraisonCommande.length() < 2 ) {
            throw new FormValidationException( "Le mode de livraison doit contenir au moins 2 caractères." );
        } else if ( modeLivraisonCommande == null ) {
            throw new FormValidationException( "Merci de saisir un mode de livraison." );
        }
    }

    private void validationStatutPaiement( String statutCommande ) throws FormValidationException {
        if ( statutCommande != null && statutCommande.length() < 2 && statutCommande.length() > 0 ) {
            throw new FormValidationException( "Le statut de paiement doit contenir au moins 2 caractères." );
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws FormValidationException {
        if ( statutLivraison != null && statutLivraison.length() < 2 && statutLivraison.length() > 0 ) {
            throw new FormValidationException( "Le statut de livraison doit contenir au moins 2 caractères." );
        }
    }

    private Double validationMontant( String montantCommande ) throws FormValidationException {
        Double montant;
        if ( montantCommande != null ) {
            try {
                montant = Double.valueOf( montantCommande );
                if ( montant.isNaN() ) {
                    throw new FormValidationException( "Le montant doit être un chiffre." );
                } else if ( montant <= 0.0 ) {
                    throw new FormValidationException( "Le montant doit être positif et supérieur à zéro." );
                }
            } catch ( Exception e ) {
                montant = 0.0;
                throw new FormValidationException( "Le montant doit être un chiffre." );
            }
        } else {
            montant = 0.0;
            throw new FormValidationException( "Merci d'entrer un montant." );
        }
        return montant;
    }

}
