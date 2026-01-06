package com.myapp.services;

import com.myapp.dao.TacheDAO;
import com.myapp.models.Tache;
import java.util.List;

public class TacheService {
    private final TacheDAO tacheDAO = new TacheDAO();

    public void envoyerNouvelleTache(Tache t) {
        // On verifie si la description est vide ou pas
        if (t.getDescription() != null && !t.getDescription().trim().isEmpty()) {
            tacheDAO.enregistrerTache(t);
        }
    }

    public List<Tache> recupererTachesEmploye(int id) {
        return tacheDAO.recupererTachesParEmploye(id);
    }
}