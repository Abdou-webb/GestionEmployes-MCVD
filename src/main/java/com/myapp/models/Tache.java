package com.myapp.models;

public class Tache {
    private int id;
    private int employeeId;
    private String description;
    private String dateSaisie;

    public Tache(int id, int employeeId, String description, String dateSaisie) {
        this.id = id;
        this.employeeId = employeeId;
        this.description = description;
        this.dateSaisie = dateSaisie;
    }

    // IL FAUT CES MÉTHODES POUR QUE LE DAO FONCTIONNE :
    public int getId() { return id; }
    public int getEmployeeId() { return employeeId; }
    public String getDescription() { return description; }
    public String getDateSaisie() { return dateSaisie; }
}