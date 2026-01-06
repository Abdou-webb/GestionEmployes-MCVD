package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.models.User;
import com.myapp.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    // APPEL AU SERVICE uniquement
    private final UserService userService = new UserService();

    @FXML
    private void handleLogin() throws Exception {
        String username = (txtUsername != null) ? txtUsername.getText().trim() : "";
        String password = (txtPassword != null) ? txtPassword.getText() : "";

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Champs vides", "Merci de saisir vos identifiants.");
            return;
        }

        // Utilisation du Service
        User user = userService.trouverUtilisateur(username);

        if (user == null) {
            showAlert("Erreur", "Utilisateur inexistant.");
            return;
        }

        if ("admin".equals(username)) {
            userService.reparerHashAdmin(); // Le service s'occupe de la DB
            user = userService.trouverUtilisateur(username);
        }

        if (user.checkPassword(password)) {
            if ("ADMIN".equals(user.getRole())) {
                SceneManager.switchScene("MainView.fxml", "Administration");
            } else {
                EmployeeDashboardController.setLoggedEmployeeId(user.getEmployeeId());
                SceneManager.switchScene("EmployeeView.fxml", "Mon Espace");
            }
        } else {
            showAlert("Échec", "Mot de passe incorrect.");
        }
    }

    @FXML private void goToSignup() throws Exception {
        SceneManager.switchScene("SignupView.fxml", "Inscription");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}