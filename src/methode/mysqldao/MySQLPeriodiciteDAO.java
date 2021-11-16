package methode.mysqldao;

import methode.connexion.Connexion;
import methode.idao.PeriodiciteDAO;
import methode.metier.Periodicite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPeriodiciteDAO implements PeriodiciteDAO<Periodicite> {

    private static MySQLPeriodiciteDAO instance;
    private int id;
    private String libelle;
    private Connexion connexion = new Connexion();
    private Connection laConnexion = connexion.creeConnexion();

    private MySQLPeriodiciteDAO() {}

    public static MySQLPeriodiciteDAO getInstance() {
        if (instance == null) {
            instance = new MySQLPeriodiciteDAO();
        }
        return instance;
    }



    @Override
    public boolean create(Periodicite objet) {
        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement req = laConnexion.prepareStatement("Insert into Periodicite (libelle) value (?)");
            req.setString(1, objet.getLibelle());
            req.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Pb dans select " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Periodicite objet) {
        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement req = laConnexion.prepareStatement("update Periodicite SET libelle = ? WHERE id_periodicite = ?");
            req.setString(1, objet.getLibelle());
            req.setInt(2,objet.getId());

            req.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Pb dans select " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Periodicite objet) {
        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement req = laConnexion.prepareStatement("delete from Periodicite where id_periodicite=?");
            req.setInt(1,objet.getId());
            req.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Pb dans select " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<Periodicite> findAll() {
        List<Periodicite> pe = new ArrayList<>();
        try{
            Connection laConnexion = this.laConnexion;

            PreparedStatement req = laConnexion.prepareStatement("SELECT * FROM Periodicite");
            ResultSet res = req.executeQuery();
            while (res.next()){
                id =res.getInt("id_periodicite");
                libelle = res.getString("libelle");

                pe.add(new Periodicite(id,libelle));
            }
            return pe;

        }catch (SQLException e){
            System.out.println("Pb dans select " + e.getMessage());
            return null ;
        }
    }

    @Override
    public Periodicite getById(int id) {
        try{
            Connection laConnexion = this.laConnexion;

            PreparedStatement req = laConnexion.prepareStatement("SELECT libelle FROM Periodicite WHERE id_periodicite = ? ");
            req.setInt(1,id);
            ResultSet res = req.executeQuery();
            while (res.next()){
                libelle =res.getString("libelle");
            }
            return new Periodicite(id,libelle);

        }catch (SQLException e){
            System.out.println("Pb dans select " + e.getMessage());
            return null ;
        }
    }
}
