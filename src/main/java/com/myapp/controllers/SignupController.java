package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.models.Employe;
import com.myapp.models.User;
import com.myapp.services.UserService;
import com.myapp.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignupController {
    @FXML private TextField txtUsername, txtEmployeeId;
    @FXML private PasswordField txtPassword, txtConfirmPassword;

    // UTILISATION DES SERVICES
    private final UserService userService = new UserService();
    private final EmployeeService employeeService = new EmployeeService();

    @FXML
    private void handleSignup() throws Exception {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String confirm = txtConfirmPassword.getText();
        String empIdStr = txtEmployeeId.getText().trim();

        if (username.isEmpty() || password.isEmpty() || empIdStr.isEmpty()) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        if (!password.equals(confirm)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(empIdStr);


            Employe emp = employeeService.trouverEmploye(employeeId);

            if (emp == null) {
                showAlert("ID Invalide", "Cet employé n'existe pas dans nos registres.");
                return;
            }

            // On demande au Service User de créer le compte
            User newUser = new User(username, password, "EMPLOYEE", employeeId, false);
            userService.creerCompte(newUser);

            showAlert("Succès", "Compte créé avec succès !");
            SceneManager.switchScene("LoginView.fxml", "Connexion");

        } catch (NumberFormatException e) {
            showAlert("Erreur Format", "L'ID doit être un nombre.");
        }
    }

    @FXML private void goToLogin() throws Exception {
        SceneManager.switchScene("LoginView.fxml", "Connexion");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}