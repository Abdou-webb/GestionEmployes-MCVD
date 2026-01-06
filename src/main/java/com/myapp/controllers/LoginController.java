package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.dao.UserDAO;
import com.myapp.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class LoginController {
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() throws Exception {
        // Nettoyage des saisies
        String username = (txtUsername != null) ? txtUsername.getText().trim() : "";
        String password = (txtPassword != null) ? txtPassword.getText() : "";

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs vides", "Veuillez saisir vos identifiants.");
            return;
        }

        User user = userDAO.trouverParNomUtilisateur(username);

        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Utilisateur inexistant.");
            return;
        }

        // Bloc de réparation (Optionnel : à supprimer après le premier succès)
        if ("admin".equals(username)) {
            reparerCompteAdmin();
            user = userDAO.trouverParNomUtilisateur(username); // Recharger
        }

        if (user.checkPassword(password)) {
            if ("ADMIN".equals(user.getRole())) {
                SceneManager.switchScene("MainView.fxml", "Administration");
            } else {
                EmployeeDashboardController.setLoggedEmployeeId(user.getEmployeeId());
                SceneManager.switchScene("EmployeeView.fxml", "Mon Espace");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec", "Mot de passe incorrect.");
        }
    }

    private void reparerCompteAdmin() {
        String hash = org.mindrot.jbcrypt.BCrypt.hashpw("admin", org.mindrot.jbcrypt.BCrypt.gensalt());
        try (Connection conn = com.myapp.utils.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET password_hash = ? WHERE username = 'admin'")) {
            pstmt.setString(1, hash);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void goToSignup() throws Exception {
        SceneManager.switchScene("SignupView.fxml", "Inscription");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}