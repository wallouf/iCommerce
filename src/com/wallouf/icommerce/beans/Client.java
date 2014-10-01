package com.wallouf.icommerce.beans;

public class Client {
    private String nom;
    private String prenom;
    private String adress;
    private String mail;
    private String phone;

    public Client() {
        super();
    }

    public Client( String nom, String prenom, String adress, String mail,
            String phone ) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.adress = adress;
        this.mail = mail;
        this.phone = phone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom( String prenom ) {
        this.prenom = prenom;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress( String adress ) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail( String mail ) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

}
