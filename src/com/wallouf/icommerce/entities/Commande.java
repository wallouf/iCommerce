package com.wallouf.icommerce.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.wallouf.icommerce.tools.JodaDateTimeConverter;

@Entity
public class Commande {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long     id;
    @ManyToOne
    @JoinColumn( name = "id_client" )
    private Client   client;
    @Column( name = "modeDePaiement" )
    private String   modeDePaiement;
    @Column( name = "statutDePaiement" )
    private String   statutDePaiement;
    @Column( name = "modeDeLivraison" )
    private String   modeDeLivraison;
    @Column( name = "statutDeLivraison" )
    private String   statutDeLivraison;
    @Column( columnDefinition = "TIMESTAMP" )
    @Converter( name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class )
    @Convert( "dateTimeConverter" )
    private DateTime date;
    @Column( name = "montant" )
    private double   montant;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate( DateTime date ) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public String getModeDePaiement() {
        return modeDePaiement;
    }

    public void setModeDePaiement( String modeDePaiement ) {
        this.modeDePaiement = modeDePaiement;
    }

    public String getStatutDePaiement() {
        return statutDePaiement;
    }

    public void setStatutDePaiement( String statutDePaiement ) {
        this.statutDePaiement = statutDePaiement;
    }

    public String getModeDeLivraison() {
        return modeDeLivraison;
    }

    public void setModeDeLivraison( String modeDeLivraison ) {
        this.modeDeLivraison = modeDeLivraison;
    }

    public String getStatutDeLivraison() {
        return statutDeLivraison;
    }

    public void setStatutDeLivraison( String statutDeLivraison ) {
        this.statutDeLivraison = statutDeLivraison;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant( double montant ) {
        this.montant = montant;
    }

}
