package methode.listememoiredao;

import methode.idao.AbonnementDAO;
import methode.metier.Abonnement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ListeMemoireAbonnementDAO implements AbonnementDAO<Abonnement> {

    private static ListeMemoireAbonnementDAO instance;
    private List<Abonnement> donnees;




    private ListeMemoireAbonnementDAO() {

        this.donnees = new ArrayList<>();

    }

    public static ListeMemoireAbonnementDAO getInstance(){
        if (instance == null) {
            instance = new ListeMemoireAbonnementDAO();
        }
        return instance;
    }

    @Override
    public List<Abonnement> findAll() {

        return this.donnees;

    }

    @Override
    public Abonnement getById(int id) {
        int idx = this.donnees.indexOf(new Abonnement(id, null,null,null,null));
        if (idx == -1) {
            throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");
        } else {
            return this.donnees.get(idx);
        }
    }

    @Override
    public boolean create(Abonnement objet) {
        objet.setId_abonnement(1);
        // Ne fonctionne que si l'objet mÃ©tier est bien fait...
        while (this.donnees.contains(objet)) {

            objet.setId_abonnement(objet.getId_abonnement() + 1);
        }
        boolean ok = this.donnees.add(objet);

        return ok;
    }

    @Override
    public boolean update(Abonnement objet) {
        int idx = this.donnees.indexOf(objet);

        if (idx == -1) {
            throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");
        } else {

            this.donnees.set(idx, objet);
        }

        return true;
    }

    @Override
    public boolean delete(Abonnement objet) {
        Abonnement supprimer;

        int idx = this.donnees.indexOf(objet);
        if (idx == -1) {
            throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");
        } else {
            supprimer = this.donnees.remove(idx);
        }

        return objet.equals(supprimer);
    }


}
