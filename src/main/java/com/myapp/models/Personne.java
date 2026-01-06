package com.myapp.models;

public abstract class Personne {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String adresse;

    public Personne(String nom, String prenom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public abstract void afficherInfos();{
    }
}


