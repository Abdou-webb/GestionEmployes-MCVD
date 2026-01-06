package com.myapp.dao;

import com.myapp.models.Employe;
import com.myapp.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Méthode pour récupérer toute la liste depuis MySQL
    public List<Employe> chargerTousLesEmployes() {
        List<Employe> liste = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        "EMSI",
                        rs.getString("poste"),
                        rs.getDouble("salaire")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur dans chargerTousLesEmployes: " + e.getMessage());
        }
        return liste;
    }

    // Sauvegarde de la liste (utilisé après un ajout/modif/suppr)
    public void synchroniserListe(List<Employe> liste) {
        for (Employe e : liste) {
            sauvegarderOuMettreAJour(e);
        }
    }

    // Logique simplifiée : Si l'ID existe on UPDATE, sinon on INSERT
    public void sauvegarderOuMettreAJour(Employe e) {
        if (findById(e.getId()) == null) {
            // Cas d'un nouvel employé
            String sqlInsert = "INSERT INTO employees (id, nom, prenom, poste, salaire) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, e.getId());
                ps.setString(2, e.getNom());
                ps.setString(3, e.getPrenom());
                ps.setString(4, e.getPoste());
                ps.setDouble(5, e.getSalaire());
                ps.executeUpdate();
            } catch (SQLException ex) { ex.printStackTrace(); }
        } else {
            // Cas d'une modification
            String sqlUpdate = "UPDATE employees SET nom=?, prenom=?, poste=?, salaire=? WHERE id=?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, e.getNom());
                ps.setString(2, e.getPrenom());
                ps.setString(3, e.getPoste());
                ps.setDouble(4, e.getSalaire());
                ps.setInt(5, e.getId());
                ps.executeUpdate();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public Employe findById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Employe(rs.getInt("id"), rs.getString("nom"),
                            rs.getString("prenom"), "EMSI",
                            rs.getString("poste"), rs.getDouble("salaire"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void supprimerEmploye(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Employé avec ID " + id + " supprimé de la base.");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}