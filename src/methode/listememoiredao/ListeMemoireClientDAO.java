package methode.listememoiredao;

import methode.idao.ClientDAO;
import methode.metier.Client;

import java.util.ArrayList;
import java.util.List;

public class ListeMemoireClientDAO implements ClientDAO<Client> {

    private static ListeMemoireClientDAO instance;
    private List<Client> donnees;

    private ListeMemoireClientDAO(){

        this.donnees = new ArrayList<>();

        donnees.add(new Client("CARL","Noé","3bis","Metz","France","rue rose","57800",1));
        donnees.add(new Client("COURS","Nathan","2bis","Diesen","France","rue bleu","57890",2));


    }

    public static ListeMemoireClientDAO getInstance() {
        if (instance == null) {
            instance = new ListeMemoireClientDAO();
        }
        return instance;
    }

    @Override
    public List<Client> findAll() {
        return this.donnees;
    }

    @Override
    public Client getById(int id) {
        int idx = this.donnees.indexOf(new Client(null, null,null,null,null,null,null,id));
        if (idx == -1) {
            throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");
        } else {
            return this.donnees.get(idx);
        }
    }

    @Override
    public boolean create(Client objet) {
        objet.setId_client(3);
        // Ne fonctionne que si l'objet mÃ©tier est bien fait...
        while (this.donnees.contains(objet)) {

            objet.setId_client(objet.getId_client() + 1);
        }
        boolean ok = this.donnees.add(objet);

        return ok;
    }

    @Override
    public boolean update(Client objet) {

        int idx = -1;
        for (Client client : this.donnees) {
            if (client.getId_client() == objet.getId_client()) {
                idx = this.donnees.indexOf(client);
            }
        }
        if (idx == -1) {
            throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");
        }
        else {
            this.donnees.set(idx, objet);
            return true;
        }
    }

    @Override
    public boolean delete(Client objet) {
        Client supprimer;
        int idx = this.donnees.indexOf(objet);

        if (idx == -1) {
            throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");
        } else {
            supprimer = this.donnees.remove(idx);
        }

        return objet.equals(supprimer);
    }
}
