package com.wallouf.icommerce.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wallouf.icommerce.beans.Client;

public class CreationClientForm {
    private static final String PARAM_nomClient       = "nomClient";
    private static final String PARAM_prenomClient    = "prenomClient";
    private static final String PARAM_adresseClient   = "adresseClient";
    private static final String PARAM_telephoneClient = "telephoneClient";
    private static final String PARAM_emailClient     = "emailClient";

    private String              message;
    private Map<String, String> erreurs               = new HashMap<String, String>();

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Client creerClient( HttpServletRequest request ) {
        /**
         * Creation des variables
         */
        String nom = request.getParameter( PARAM_nomClient );
        String prenom = request.getParameter( PARAM_prenomClient );
        String adresse = request.getParameter( PARAM_adresseClient );
        String telephone = request.getParameter( PARAM_telephoneClient );
        String email = request.getParameter( PARAM_emailClient );
        Client client = new Client();
        try {
            validationNom( nom );
        } catch ( Exception e ) {
            setErreur( PARAM_nomClient, e.getMessage() );
        }
        client.setNom( nom );
        try {
            validationPrenom( prenom );
        } catch ( Exception e ) {
            setErreur( PARAM_prenomClient, e.getMessage() );
        }
        client.setPrenom( prenom );
        try {
            validationAdresse( adresse );
        } catch ( Exception e ) {
            setErreur( PARAM_adresseClient, e.getMessage() );
        }
        client.setAdress( adresse );

        try {
            validationTelephone( telephone );
        } catch ( Exception e ) {
            setErreur( PARAM_telephoneClient, e.getMessage() );
        }
        client.setPhone( telephone );

        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( PARAM_emailClient, e.getMessage() );
        }
        client.setMail( email );

        if ( erreurs.isEmpty() ) {
            message = "Succès de l'inscription.";
        } else {
            message = "Échec de l'inscription.";
        }

        return client;
    }

    private void validationEmail( String email ) throws Exception {
        if ( email != null && email.length() > 0 ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new Exception( "Merci de saisir une adresse mail valide." );
            }
        }
    }

    private void validationNom( String nom ) throws Exception {
        if ( nom != null && nom.length() < 2 ) {
            throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
        } else if ( nom == null ) {
            throw new Exception( "Merci de saisir un nom." );
        }
    }

    private void validationPrenom( String prenom ) throws Exception {
        if ( prenom != null && prenom.length() < 2 && prenom.length() > 0 ) {
            throw new Exception( "Le prenom de l'utilisateur doit contenir au moins 2 caractères." );
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse != null && adresse.length() < 10 ) {
            throw new Exception( "L'adresse de l'utilisateur doit contenir au moins 10 caractères." );
        } else if ( adresse == null ) {
            throw new Exception( "Merci de saisir une adresse." );
        }
    }

    private void validationTelephone( String telephone ) throws Exception {
        if ( telephone != null && telephone.length() < 4 ) {
            if ( !telephone.matches( "\\d" ) ) {
                throw new Exception( "Merci de saisir un telephone valide et d'au moins 4 chiffres." );
            } else {
                throw new Exception( "Merci de saisir un telephone d'au moins 4 chiffres." );
            }
        } else if ( telephone == null ) {
            throw new Exception( "Merci de saisir un telephone." );
        }
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

}
