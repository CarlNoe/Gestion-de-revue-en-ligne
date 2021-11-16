package methode.mysqldao;

import methode.connexion.Connexion;
import methode.idao.RevueDAO;
import methode.metier.Periodicite;
import methode.metier.Revue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLRevueDAO implements RevueDAO<Revue> {

    private static MySQLRevueDAO instance;
    Periodicite periodicite;
    private MySQLRevueDAO() {}

    public static MySQLRevueDAO getInstance() {
        if (instance == null) {
            instance = new MySQLRevueDAO();
        }
        return instance;
    }

    @Override
    public List<Revue> findAll() {
        List<Revue> re = new ArrayList<>();
        try{
            Connexion connexion = new Connexion();
            Connection laConnexion = connexion.creeConnexion();

            PreparedStatement req = laConnexion.prepareStatement("SELECT * FROM Revue");
            ResultSet res = req.executeQuery();
            while (res.next()){
                int idRevue =res.getInt("id_revue");
                String titre = res.getString("titre");
                String description = res.getString("description");
                Float tarifNumero = res.getFloat("tarif_numero");
                String visuel = res.getString("visuel");
                int idPeriodicite = res.getInt("id_periodicite");

                re.add(new Revue(idRevue,MySQLPeriodiciteDAO.getInstance().getById(idPeriodicite), description,tarifNumero,titre,visuel));
            }
            return re;

        }catch (SQLException e){
            System.out.println("Pb dans select " + e.getMessage());
            return null ;
        }
    }

    @Override
    public Revue getById(int id) {

        try {
            Connexion connexion = new Connexion();
            Connection laConnexion = connexion.creeConnexion();

            PreparedStatement req = laConnexion.prepareStatement("SELECT id_revue,titre,description,tarif_numero,visuel,id_periodicite FROM Revue WHERE id_revue = ? ");
            req.setInt(1,id);
            ResultSet res = req.executeQuery();
            Revue re = null;
            while (res.next()){
                String titre = res.getString("titre");
                String description = res.getString("description");
                Float tarif_numeros = res.getFloat("tarif_numero");
                String visuel = res.getString("visuel");

                re = new Revue(id,MySQLPeriodiciteDAO.getInstance().getById(id),description,tarif_numeros,titre,visuel);
            }

            return re;

        }catch (SQLException sqle){
            System.out.println("Pb dans le select"+sqle.getMessage());
            return null;
        }

    }

    @Override
    public boolean create(Revue objet) {
        try{
            Connexion connexion = new Connexion();
            Connection laConnexion = connexion.creeConnexion();

            PreparedStatement requete = laConnexion.prepareStatement("insert into Revue (id_revue,titre, description,tarif_numero,visuel,id_periodicite) values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            requete.setInt(1,objet.getId_revue());
            requete.setString(2,objet.getTitre());
            requete.setString(3,objet.getDescription());
            requete.setFloat(4,objet.getTarif_numeros());
            requete.setString(5,objet.getVisuel());
            requete.setInt(6, objet.getPeriodicite().getId());

            requete.executeUpdate();
            return true;

        }catch (SQLException sqle){
            System.out.println("Pb dans select " + sqle.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Revue objet) {
        try {
            Connexion connexion = new Connexion();
            Connection laConnexion = connexion.creeConnexion();

            PreparedStatement requete = laConnexion.prepareStatement("update Revue SET description = ? , tarif_numero = ?, titre = ? , visuel = ? , id_periodicite = ? WHERE id_revue = ?");
            requete.setString(1, objet.getDescription());
            requete.setFloat(2, objet.getTarif_numeros());
            requete.setString(3, objet.getTitre());
            requete.setString(4, objet.getVisuel());
            requete.setInt(5,objet.getPeriodicite().getId());
            requete.setInt(6, objet.getId_revue());
            requete.executeUpdate();

            return true;
        } catch (SQLException sqle) {
            System.out.println("Pb dans select " + sqle.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(Revue objet) {
        try {
            Connexion connexion = new Connexion();
            Connection laConnexion = connexion.creeConnexion();

            PreparedStatement requete = laConnexion.prepareStatement("delete from Revue where id_revue=?");
            requete.setInt(1,objet.getId_revue());
            requete.executeUpdate();
            return true;
        }
        catch (SQLException sqle){
            System.out.println("Pb dans select " + sqle.getMessage());
            return false;
        }
    }

}
