package com.wallouf.icommerce.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.wallouf.icommerce.beans.Client;
import com.wallouf.icommerce.dao.ClientDao;

import eu.medsea.mimeutil.MimeUtil;

public class CreationClientForm {
    private static final String PARAM_nomClient       = "nomClient";
    private static final String PARAM_prenomClient    = "prenomClient";
    private static final String PARAM_adresseClient   = "adresseClient";
    private static final String PARAM_telephoneClient = "telephoneClient";
    private static final String PARAM_emailClient     = "emailClient";
    private static final String PARAM_imageClient     = "fichier";
    private static final int    TAILLE_TAMPON         = 10240;

    private String              message;
    private Map<String, String> erreurs               = new HashMap<String, String>();
    private ClientDao           clientDao;

    public String getMessage() {
        return message;
    }

    public CreationClientForm( ClientDao clientDao ) {
        this.clientDao = clientDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Client creerClient( HttpServletRequest request, String chemin ) {
        /**
         * Creation des variables
         */
        String nom = getValeurChamp( request, PARAM_nomClient );
        String prenom = getValeurChamp( request, PARAM_prenomClient );
        String adresse = getValeurChamp( request, PARAM_adresseClient );
        String telephone = getValeurChamp( request, PARAM_telephoneClient );
        String email = getValeurChamp( request, PARAM_emailClient );
        Client client = new Client();

        traiterNom( nom, client );
        traiterPrenom( prenom, client );
        traiterAdresse( adresse, client );
        traiterTelephone( telephone, client );
        traiterEmail( email, client );
        traiterImage( client, request, chemin );

        /* Si aucune erreur n'est survenue jusqu'à présent */
        if ( erreurs.isEmpty() ) {
            message = "Succès de l'inscription.";
            /**
             * Ajout dans la session de l'utilisateur
             */
            clientDao.creer( client );
        } else {
            message = "Échec de l'inscription.";
        }

        return client;
    }

    private void traiterNom( String nom, Client client ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_nomClient, e.getMessage() );
        }
        client.setNom( nom );
    }

    private void traiterPrenom( String prenom, Client client ) {
        try {
            validationNom( prenom );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_nomClient, e.getMessage() );
        }
        client.setPrenom( prenom );
    }

    private void traiterAdresse( String adresse, Client client ) {
        try {
            validationAdresse( adresse );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_adresseClient, e.getMessage() );
        }
        client.setAdresse( adresse );
    }

    private void traiterTelephone( String telephone, Client client ) {
        try {
            validationTelephone( telephone );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_telephoneClient, e.getMessage() );
        }
        client.setTelephone( telephone );
    }

    private void traiterEmail( String email, Client client ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_emailClient, e.getMessage() );
        }
        client.setEmail( email );
    }

    private void traiterImage( Client client, HttpServletRequest request, String chemin ) {
        String image = null;
        try {
            image = validationImage( request, chemin );
        } catch ( FormValidationException e ) {
            setErreur( PARAM_imageClient, e.getMessage() );
        }
        client.setImage( image );
    }

    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null && email.length() > 0 ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            }
        }
    }

    private String validationImage( HttpServletRequest request, String chemin ) throws FormValidationException {
        /*
         * Récupération du contenu du champ image du formulaire. Il faut ici
         * utiliser la méthode getPart().
         */
        String nomFichier = null;
        InputStream contenuFichier = null;
        try {
            Part part = request.getPart( PARAM_imageClient );
            nomFichier = getNomFichier( part );

            /*
             * Si la méthode getNomFichier() a renvoyé quelque chose, il s'agit
             * donc d'un champ de type fichier (input type="file").
             */
            if ( nomFichier != null && !nomFichier.isEmpty() ) {
                /*
                 * Antibug pour Internet Explorer, qui transmet pour une raison
                 * mystique le chemin du fichier local à la machine du client...
                 * 
                 * Ex : C:/dossier/sous-dossier/fichier.ext
                 * 
                 * On doit donc faire en sorte de ne sélectionner que le nom et
                 * l'extension du fichier, et de se débarrasser du superflu.
                 */
                nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
                        .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

                /* Récupération du contenu du fichier */
                contenuFichier = part.getInputStream();

                /* Extraction du type MIME du fichier depuis l'InputStream */
                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
                Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

                /*
                 * Si le fichier est bien une image, alors son en-tête MIME
                 * commence par la chaîne "image"
                 */
                if ( mimeTypes.toString().startsWith( "image" ) ) {
                    /* Ecriture du fichier sur le disque */
                    ecrireFichier( contenuFichier, nomFichier, chemin );
                } else {
                    throw new FormValidationException( "Le fichier envoyé doit être une image." );
                }
            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception retournée si la taille des données dépasse les limites
             * définies dans la section <multipart-config> de la déclaration de
             * notre servlet d'upload dans le fichier web.xml
             */
            // e.printStackTrace();
            throw new FormValidationException( "Le fichier envoyé ne doit pas dépasser 1Mo." );
        } catch ( IOException e ) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            // e.printStackTrace();
            throw new FormValidationException( "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data.
             */
            // e.printStackTrace();
            throw new FormValidationException(
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
        }

        return nomFichier;
    }

    private void validationNom( String nom ) throws FormValidationException {
        if ( nom != null && nom.length() < 2 ) {
            throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
        } else if ( nom == null ) {
            throw new FormValidationException( "Merci de saisir un nom." );
        }
    }

    private void validationPrenom( String prenom ) throws FormValidationException {
        if ( prenom != null && prenom.length() < 2 && prenom.length() > 0 ) {
            throw new FormValidationException( "Le prenom de l'utilisateur doit contenir au moins 2 caractères." );
        }
    }

    private void validationAdresse( String adresse ) throws FormValidationException {
        if ( adresse != null && adresse.length() < 10 ) {
            throw new FormValidationException( "L'adresse de l'utilisateur doit contenir au moins 10 caractères." );
        } else if ( adresse == null ) {
            throw new FormValidationException( "Merci de saisir une adresse." );
        }
    }

    private void validationTelephone( String telephone ) throws FormValidationException {
        if ( telephone != null && telephone.length() > 0 ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new FormValidationException( "Merci de saisir un telephone valide et d'au moins 4 chiffres." );
            } else if ( telephone.length() < 4 ) {
                throw new FormValidationException( "Merci de saisir un telephone d'au moins 4 chiffres." );
            } else if ( telephone.length() > 10 ) {
                throw new FormValidationException( "Merci de saisir un telephone de moins de 10 chiffres." );
            }
        } else if ( telephone == null || telephone.length() <= 0 ) {
            throw new FormValidationException( "Merci de saisir un telephone." );
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

    private static String getNomFichier( Part part ) {
        /* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            /* Recherche de l'éventuelle présence du paramètre "filename". */
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                /*
                 * Si "filename" est présent, alors renvoi de sa valeur,
                 * c'est-à-dire du nom de fichier sans guillemets.
                 */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        /* Et pour terminer, si rien n'a été trouvé... */
        return null;
    }

    /*
     * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
     * sur le disque, dans le répertoire donné et avec le nom donné.
     */
    private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws FormValidationException {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenu, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } catch ( Exception e ) {
            throw new FormValidationException( "Erreur lors de l'écriture du fichier sur le disque." );
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }

}
