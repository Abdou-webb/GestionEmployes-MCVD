package com.myapp.controllers;

import com.myapp.SceneManager;
import com.myapp.dao.EmployeeDAO;
import com.myapp.dao.TacheDAO;
import com.myapp.models.Employe;
import com.myapp.models.Tache;
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

    private final EmployeeDAO dao = new EmployeeDAO();
    private final TacheDAO tacheDao = new TacheDAO();
    private ObservableList<Employe> employeeList;

    @FXML
    public void initialize() {
        // Configuration sécurisée des colonnes
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNom != null) colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        if (colPrenom != null) colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        if (colPoste != null) colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        if (colSalaire != null) colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

        employeeList = FXCollections.observableArrayList();
        List<Employe> savedData = dao.chargerTousLesEmployes();
        if (savedData != null) {
            employeeList.addAll(savedData);
        }

        if (tableEmployes != null) {
            tableEmployes.setItems(employeeList);
        }
    }

    @FXML
    public void handleAjouter() {
        try {
            // Validation des entrées
            if (txtId.getText().isEmpty() || txtNom.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Champs requis", "L'ID et le Nom sont obligatoires.");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            double salaire = Double.parseDouble(txtSalaire.getText().trim());
            Employe e = new Employe(id, txtNom.getText(), txtPrenom.getText(), "EMSI", txtPoste.getText(), salaire);

            employeeList.add(e);
            dao.synchroniserListe(employeeList);
            effacerChamps();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Employé ajouté avec succès.");
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Erreur format", "L'ID et le Salaire doivent être des nombres.");
        }
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