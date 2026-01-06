package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.dao.EmployeeDAO;
import com.myapp.dao.TacheDAO;
import com.myapp.models.Employe;
import com.myapp.models.Tache;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class EmployeeDashboardController {

    @FXML private Label lblNom, lblPrenom, lblPoste, lblSalaire, lblPerformance, lblSociete, lblDate;

    private static int loggedEmployeeId;
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final TacheDAO tacheDAO = new TacheDAO();

    public static void setLoggedEmployeeId(int id) {
        loggedEmployeeId = id;
    }

    @FXML
    public void initialize() {
        // 1. Mise à jour de la date
        if (lblDate != null) {
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
            lblDate.setText(date.format(formatter));
        }

        // 2. Récupération des données
        Employe emp = employeeDAO.findById(loggedEmployeeId);

        if (emp != null) {
            if (lblNom != null) lblNom.setText(emp.getNom().toUpperCase());
            if (lblPrenom != null) lblPrenom.setText(emp.getPrenom());
            if (lblPoste != null) lblPoste.setText(emp.getPoste());
            if (lblSalaire != null) lblSalaire.setText(String.format("%.2f DH", emp.getSalaire()));
            if (lblSociete != null) lblSociete.setText("EMSI - École Marocaine des Sciences de l'Ingénieur");
            if (lblPerformance != null) lblPerformance.setText("95/100");
        } else {
            // Ces vérifications évitent aussi les erreurs si l'employé n'est pas trouvé
            if (lblNom != null) lblNom.setText("Profil");
            if (lblPrenom != null) lblPrenom.setText("Inconnu");
            if (lblPoste != null) lblPoste.setText("Non assigné");
        }
    } // Fin de initialize

    @FXML
    private void handleLogout() throws IOException {
        SceneManager.switchScene("LoginView.fxml", "Connexion EMSI");
    }

    @FXML
    private void handleShowTasks() {
        List<Tache> taches = tacheDAO.recupererTachesParEmploye(loggedEmployeeId);

        StringBuilder sb = new StringBuilder();

        if (taches.isEmpty()) {
            sb.append("Vous n'avez aucune tâche assignée pour le moment.");
        } else {
            sb.append("Voici vos dernières directives :\n\n");
            for (Tache t : taches) {
                sb.append("📍 ").append(t.getDescription())
                        .append("\n📅 Envoyée le : ").append(t.getDateSaisie())
                        .append("\n----------------------------------\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Messagerie Interne");
        alert.setHeaderText("Directives de l'Administration");
        alert.setContentText(sb.toString());
        alert.getDialogPane().setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        alert.showAndWait();
    }
}