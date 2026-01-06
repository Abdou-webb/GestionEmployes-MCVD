package com.myapp.services;

import com.myapp.dao.UserDAO;
import com.myapp.models.User;
import com.myapp.utils.DatabaseConnection;
import java.sql.*;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public User trouverUtilisateur(String username) {
        return userDAO.trouverParNomUtilisateur(username);
    }

    public void creerCompte(User user) {
        userDAO.creerCompteUtilisateur(user);
    }

    // deplace la logique de reparation
    public void reparerHashAdmin() {
        String hash = org.mindrot.jbcrypt.BCrypt.hashpw("admin", org.mindrot.jbcrypt.BCrypt.gensalt());
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET password_hash = ? WHERE username = 'admin'")) {
            pstmt.setString(1, hash);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}