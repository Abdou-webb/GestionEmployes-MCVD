package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.dao.EmployeeDAO;
import com.myapp.dao.TacheDAO;
import com.myapp.models.Employe;
import com.myapp.models.Tache;
import com.myapp.services.EmployeeService;
import com.myapp.services.TacheService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeController {

    @FXML private TableView<Employe> tableEmployes;
    @FXML private TextField txtId, txtNom, txtPrenom, txtPoste, txtSalaire;
    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colNom, colPrenom, colPoste;
    @FXML private TableColumn<Employe, Double> colSalaire;


    private final EmployeeService employeeService = new EmployeeService();
    private final TacheService tacheService = new TacheService();

    private ObservableList<Employe> employeeList;

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNom != null) colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        if (colPrenom != null) colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        if (colPoste != null) colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        if (colSalaire != null) colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

        employeeList = FXCollections.observableArrayList();

        // APPEL AU SERVICE : au lieu du DAO
        employeeList.addAll(employeeService.recupererTousLesEmployes());

        if (tableEmployes != null) {
            tableEmployes.setItems(employeeList);
        }
    }

    @FXML
    public void handleAjouter() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            double salaire = Double.parseDouble(txtSalaire.getText().trim());
            Employe e = new Employe(id, txtNom.getText(), txtPrenom.getText(), "EMSI", txtPoste.getText(), salaire);

            employeeList.add(e);

            // appel au service
            employeeService.sauvegarderModifications(employeeList);

            effacerChamps();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Employé ajouté via le Service.");
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Vérifiez les formats numériques.");
        }
    }

    @FXML
    public void handleModifier() {
        // recuperer l'emplye par tableau
        Employe selected = tableEmployes.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                // utilisant les setters pour modifier en memoire
                selected.setNom(txtNom.getText());
                selected.setPrenom(txtPrenom.getText());
                selected.setPoste(txtPoste.getText());
                selected.setSalaire(Double.parseDouble(txtSalaire.getText().trim()));


                tableEmployes.refresh();

                //  On synchronise la liste avec MySQL
                employeeService.sauvegarderModifications(employeeList);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "L'employé a été modifié avec succès.");
                effacerChamps();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le format du salaire est incorrect.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un employé dans le tableau.");
        }
    }
    @FXML
    public void handleSupprimer() {
        Employe selected = tableEmployes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeList.remove(selected);

            // APPEL AU SERVICE
            employeeService.supprimerEmployeParId(selected.getId());

            showAlert(Alert.AlertType.INFORMATION, "Suppression", "Employé supprimé.");
            effacerChamps();
        }
    }

    @FXML
    public void handleEnvoyerTache() {
        Employe selected = tableEmployes.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Directive");
        dialog.setHeaderText("Tâche pour " + selected.getNom());
        dialog.setContentText("Description :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(desc -> {
            Tache t = new Tache(0, selected.getId(), desc, LocalDate.now().toString());

            // APPEL AU SERVICE TACHE
            tacheService.envoyerNouvelleTache(t);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Tâche envoyée.");
        });
    }

    @FXML
    public void handleTableClick() {
        Employe selected = tableEmployes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (txtId != null) txtId.setText(String.valueOf(selected.getId()));
            if (txtNom != null) txtNom.setText(selected.getNom());
            if (txtPrenom != null) txtPrenom.setText(selected.getPrenom());
            if (txtPoste != null) txtPoste.setText(selected.getPoste());
            if (txtSalaire != null) txtSalaire.setText(String.valueOf(selected.getSalaire()));
            if (txtId != null) txtId.setEditable(false);
        }
    }

    @FXML
    public void handleLogout() throws IOException {
        SceneManager.switchScene("LoginView.fxml", "Connexion EMSI");
    }

    private void effacerChamps() {
        if (txtId != null) { txtId.clear(); txtId.setEditable(true); }
        if (txtNom != null) txtNom.clear();
        if (txtPrenom != null) txtPrenom.clear();
        if (txtPoste != null) txtPoste.clear();
        if (txtSalaire != null) txtSalaire.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}