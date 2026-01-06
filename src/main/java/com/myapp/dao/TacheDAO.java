package com.myapp.dao;

import com.myapp.models.Tache;
import com.myapp.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {

    public void enregistrerTache(Tache tache) {
        String sql = "INSERT INTO taches (employee_id, description, date_saisie) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tache.getEmployeeId());
            ps.setString(2, tache.getDescription());
            ps.setString(3, tache.getDateSaisie());
            ps.executeUpdate();

            System.out.println("Tâche enregistrée pour l'employé ID: " + tache.getEmployeeId());
        } catch (SQLException e) {
            System.err.println("Erreur enregistrement tâche: " + e.getMessage());
        }
    }

    public List<Tache> recupererTachesParEmploye(int empId) {
        List<Tache> list = new ArrayList<>();
        // On trie par date pour afficher la plus recente en ordre
        String sql = "SELECT * FROM taches WHERE employee_id = ? ORDER BY date_saisie DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Tache(
                            rs.getInt("id"),
                            rs.getInt("employee_id"),
                            rs.getString("description"),
                            rs.getString("date_saisie")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}