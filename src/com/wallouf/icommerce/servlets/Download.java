package com.wallouf.icommerce.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "Download", urlPatterns = "/fichiers/*",
        initParams = @WebInitParam( name = "chemin", value = "/Users/wallouf/Documents/DEV/fileTemp/" ) )
public class Download extends HttpServlet {
    public static final int TAILLE_TAMPON = 10240; // 10ko

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /*
         * Lecture du paramÃ¨tre 'chemin' passÃ© Ã la servlet via la
         * dÃ©claration dans le web.xml
         */
        String chemin = this.getServletConfig().getInitParameter( "chemin" );

        /*
         * RÃ©cupÃ©ration du chemin du fichier demandÃ© au sein de l'URL de la
         * requÃªte
         */
        String fichierRequis = request.getPathInfo();

        /* VÃ©rifie qu'un fichier a bien Ã©tÃ© fourni */
        if ( fichierRequis == null ) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandÃ©e n'existe pas
             */
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        /*
         * DÃ©code le nom de fichier rÃ©cupÃ©rÃ©, susceptible de contenir des
         * espaces et autres caractÃ¨res spÃ©ciaux, et prÃ©pare l'objet File
         */
        fichierRequis = URLDecoder.decode( fichierRequis, "UTF-8" );
        File fichier = new File( chemin, fichierRequis );

        /* VÃ©rifie que le fichier existe bien */
        if ( !fichier.exists() ) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandÃ©e n'existe pas
             */
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        /* RÃ©cupÃ¨re le type du fichier */
        String type = getServletContext().getMimeType( fichier.getName() );

        /*
         * Si le type de fichier est inconnu, alors on initialise un type par
         * dÃ©faut
         */
        if ( type == null ) {
            type = "application/octet-stream";
        }

        /* Initialise la rÃ©ponse HTTP */
        response.reset();
        response.setBufferSize( TAILLE_TAMPON );
        response.setContentType( type );
        response.setHeader( "Content-Length", String.valueOf( fichier.length() ) );
        response.setHeader( "Content-Disposition", "attachment; filename=\"" + fichier.getName() + "\"" );

        /* PrÃ©pare les flux */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux */
            entree = new BufferedInputStream( new FileInputStream( fichier ), TAILLE_TAMPON );
            sortie = new BufferedOutputStream( response.getOutputStream(), TAILLE_TAMPON );

            /* Lit le fichier et Ã©crit son contenu dans la rÃ©ponse HTTP */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } finally {
            sortie.close();
            entree.close();
        }
    }
}