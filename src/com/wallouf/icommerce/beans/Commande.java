package com.wallouf.icommerce.beans;

import java.sql.Timestamp;

public class Commande {

    private Long      id;
    private Client    client;
    private String    modeDePaiement;
    private String    statutDePaiement;
    private String    modeDeLivraison;
    private String    statutDeLivraison;
    private Timestamp date;
    private double    montant;

    public Commande() {
        super();
    }

    public Commande( Client client, String modeDePaiement,
            String statutDePaiement, String modeDeLivraison,
            String statutDeLivraison, double montant ) {
        super();
        this.client = client;
        this.modeDePaiement = modeDePaiement;
        this.statutDePaiement = statutDePaiement;
        this.modeDeLivraison = modeDeLivraison;
        this.statutDeLivraison = statutDeLivraison;
        this.montant = montant;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate( Timestamp date ) {
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
