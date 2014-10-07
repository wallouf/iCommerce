package com.wallouf.icommerce.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.wallouf.icommerce.beans.Client;

public class CreationClientForm {
    public static final String  PARAM_listeClient     = "listeClient";
    private static final String PARAM_nomClient       = "nomClient";
    private static final String PARAM_prenomClient    = "prenomClient";
    private static final String PARAM_adresseClient   = "adresseClient";
    private static final String PARAM_telephoneClient = "telephoneClient";
    private static final String PARAM_emailClient     = "emailClient";
    private static final String CHAMP_FICHIER         = "fichier";
    private static final int    TAILLE_TAMPON         = 10240;

    private String              message;
    private Map<String, String> erreurs               = new HashMap<String, String>();

    public String getMessage() {
        return message;
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

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        Map<String, Client> listeClient = new HashMap<String, Client>();
        if ( session.getAttribute( PARAM_listeClient ) != null ) {
            try {
                listeClient = (Map<String, Client>) session.getAttribute( PARAM_listeClient );
            } catch ( Exception e ) {
            }
        }

        String nomFichier = null;
        InputStream contenuFichier = null;

        try {
            Part part = request.getPart( CHAMP_FICHIER );
            /*
             * Il faut déterminer s'il s'agit bien d'un champ de type fichier :
             * on délègue cette opération à la méthode utilitaire
             * getNomFichier().
             */
            nomFichier = getNomFichier( part );

            /*
             * Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ
             * de type fichier (input type="file").
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

            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception retournée si la taille des données dépasse les limites
             * définies dans la section <multipart-config> de la déclaration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            setErreur( CHAMP_FICHIER, "Les données envoyées sont trop volumineuses." );
        } catch ( IOException e ) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            e.printStackTrace();
            setErreur( CHAMP_FICHIER, "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data. Cela ne peut arriver que si l'utilisateur
             * essaie de contacter la servlet d'upload par un formulaire
             * différent de celui qu'on lui propose... pirate ! :|
             */
            e.printStackTrace();
            setErreur( CHAMP_FICHIER,
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
        }

        /* Si aucune erreur n'est survenue jusqu'à présent */
        if ( erreurs.isEmpty() ) {

            try {
                validationNom( nom, listeClient );
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

            /* Validation du champ fichier. */
            try {
                validationFichier( nomFichier, contenuFichier );
            } catch ( Exception e ) {
                setErreur( CHAMP_FICHIER, e.getMessage() );
            }
            client.setImage( nomFichier );
        }

        /* Si aucune erreur n'est survenue jusqu'à présent */
        if ( erreurs.isEmpty() ) {
            /* Écriture du fichier sur le disque */
            try {
                ecrireFichier( contenuFichier, nomFichier, chemin );
            } catch ( Exception e ) {
                setErreur( CHAMP_FICHIER, "Erreur lors de l'écriture du fichier sur le disque." );
            }
        }

        if ( erreurs.isEmpty() ) {
            message = "Succès de l'inscription.";
            /**
             * Ajout dans la session de l'utilisateur
             */
            listeClient.put( nom, client );
            session.setAttribute( PARAM_listeClient, listeClient );
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

    /*
     * Valide le fichier envoyé.
     */
    private void validationFichier( String nomFichier, InputStream contenuFichier ) throws Exception {
        if ( nomFichier != null && contenuFichier == null ) {
            throw new Exception( "Merci de sélectionner un fichier à envoyer." );
        }
    }

    private void validationNom( String nom, Map<String, Client> listeClient ) throws Exception {
        if ( nom != null && nom.length() < 2 ) {
            throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
        } else if ( nom == null ) {
            throw new Exception( "Merci de saisir un nom." );
        } else if ( !listeClient.isEmpty() && listeClient.containsKey( nom ) ) {
            throw new Exception( "Merci de saisir un autre nom, car ce compte existe déjà." );
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
        if ( telephone != null && telephone.length() > 0 ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new Exception( "Merci de saisir un telephone valide et d'au moins 4 chiffres." );
            } else if ( telephone.length() < 4 ) {
                throw new Exception( "Merci de saisir un telephone d'au moins 4 chiffres." );
            }
        } else if ( telephone == null || telephone.length() <= 0 ) {
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
    private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws Exception {
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
