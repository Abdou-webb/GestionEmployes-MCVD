package com.myapp.services;

import com.myapp.dao.EmployeeDAO;
import com.myapp.models.Employe;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();


    public List<Employe> recupererTousLesEmployes() {
        return employeeDAO.chargerTousLesEmployes();
    }


    public void sauvegarderModifications(List<Employe> liste) {
        employeeDAO.synchroniserListe(liste);
    }


    public void supprimerEmployeParId(int id) {
        employeeDAO.supprimerEmploye(id);
    }


    public Employe trouverEmploye(int id) {
      //on demande au dao a chercher dans mysql emsi_db
        return employeeDAO.findById(id);
    }
}