package com.myapp;

import com.myapp.dao.UserDAO;
import com.myapp.dao.EmployeeDAO; // Ajouté pour la cohérence
import com.myapp.models.Employe;
import com.myapp.models.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Initialisation de l'admin au lancement
        initAdminAccount();

        // Configuration du gestionnaire de scènes
        SceneManager.setStage(stage);

        // Chargement de la première page (Login)
        SceneManager.switchScene("LoginView.fxml", "Connexion EMSI");
    }

    private void initAdminAccount() {
        UserDAO userDAO = new UserDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // 1. On utilise le nouveau nom : trouverParNomUtilisateur
        if (userDAO.trouverParNomUtilisateur("admin") == null) {

            // Note : Pour éviter une erreur de clé étrangère MySQL,
            // on vérifie si l'employé ID 1 existe pour l'admin
            if (employeeDAO.findById(1) == null) {
                employeeDAO.sauvegarderOuMettreAJour(new Employe(1, "ADMIN", "System", "EMSI", "Admin", 0.0));
            }

            // Création de l'objet User
            User admin = new User("admin", "admin", "ADMIN", 1, false);

            // 2. On utilise le nouveau nom : creerCompteUtilisateur
            userDAO.creerCompteUtilisateur(admin);

            System.out.println(">>> Système : Compte admin par défaut créé (admin/admin).");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}