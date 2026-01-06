package com.myapp.dao;

import com.myapp.models.User;
import com.myapp.utils.DatabaseConnection;
import java.sql.*;

public class UserDAO {

    public void creerCompteUtilisateur(User user) {
        String sql = "INSERT INTO users (username, password_hash, role, employee_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername().trim());
            ps.setString(2, user.getPassword()); // Le hash BCrypt envoyé par le contrôleur
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getEmployeeId());

            ps.executeUpdate();
            System.out.println("DEBUG : Utilisateur " + user.getUsername() + " inséré avec succès.");

        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de l'inscription : " + e.getMessage());
        }
    }

    public User trouverParNomUtilisateur(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("role"),
                            rs.getInt("employee_id"),
                            true // L'utilisateur vient de la DB, donc déjà haché
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche user: " + e.getMessage());
        }
        return null;
    }
}