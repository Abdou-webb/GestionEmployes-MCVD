package com.myapp.models;

public class Employe extends Personne {
    private int id;
    private String poste;
    private double salaire;

    public Employe(int id, String nom, String prenom, String adresse, String poste, double salaire) {
        super(nom, prenom, adresse);
        this.id = id; // assigne l'id saisis par admin
        this.poste = poste;
        this.salaire = salaire;
    }

    // Getters
    public int getId() { return id; }
    public String getPoste() { return poste; }
    public double getSalaire() { return salaire; }

    // Setters
    public void setPoste(String poste) { this.poste = poste; }
    public void setSalaire(double salaire) { this.salaire = salaire; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    @Override
    public void afficherInfos() {

    }
}