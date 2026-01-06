package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.dao.EmployeeDAO;
import com.myapp.dao.UserDAO;
import com.myapp.models.Employe;
import com.myapp.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignupController {
    @FXML private TextField txtUsername, txtEmployeeId;
    @FXML private PasswordField txtPassword, txtConfirmPassword;

    private final UserDAO userDAO = new UserDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    private void handleSignup() throws Exception {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String confirm = txtConfirmPassword.getText();
        String empIdStr = txtEmployeeId.getText().trim();

        if (username.isEmpty() || password.isEmpty() || empIdStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Tous les champs sont obligatoires.");
            return;
        }

        if (!password.equals(confirm)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(empIdStr);
            Employe emp = employeeDAO.findById(employeeId);

            if (emp == null) {
                showAlert(Alert.AlertType.ERROR, "ID Invalide", "Cet ID employé n'existe pas dans la base.");
                return;
            }

            User newUser = new User(username, password, "EMPLOYEE", employeeId, false);
            userDAO.creerCompteUtilisateur(newUser);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Compte créé pour " + emp.getNom());
            SceneManager.switchScene("LoginView.fxml", "Connexion");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Format ID", "L'ID doit être un nombre.");
        }
    }

    @FXML private void goToLogin() throws Exception {
        SceneManager.switchScene("LoginView.fxml", "Connexion");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}