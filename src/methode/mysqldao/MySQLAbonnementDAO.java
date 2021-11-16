package methode.mysqldao;

import methode.connexion.Connexion;
import methode.idao.AbonnementDAO;
import methode.metier.Abonnement;
import methode.metier.Client;
import methode.metier.Revue;
import methode.tools.Helper;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

public class MySQLAbonnementDAO implements AbonnementDAO<Abonnement> {

    int id;
    int idRevue;
    int idClient;
    LocalDate dateDeb;
    LocalDate dateFin;
    private Connexion connexion = new Connexion();
    private Connection laConnexion = connexion.creeConnexion();

    private static MySQLAbonnementDAO instance;

    private MySQLAbonnementDAO() {}

    public static MySQLAbonnementDAO getInstance() {
        if (instance == null) {
            instance = new MySQLAbonnementDAO();
        }
        return instance;
    }

    private static java.sql.Date convertToDateSql(LocalDate date) throws ParseException {
        return java.sql.Date.valueOf(date);
    }

    @Override
    public List<Abonnement> findAll(){

        List<Abonnement> result = new ArrayList<>();
        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement requete = this.laConnexion.prepareStatement("SELECT * FROM Abonnement");
            ResultSet res = requete.executeQuery();

            while (res.next()) {
                id = res.getInt("id_abonnement");
                idRevue = res.getInt("id_revue");
                idClient = res.getInt("id_client");
                dateDeb = Helper.convertFromDate(res.getDate("date_debut"));
                dateFin = Helper.convertFromDate(res.getDate("date_fin"));



                Abonnement ab = new Abonnement(id, dateDeb, dateFin,MySQLClientDAO.getInstance().getById(idClient), MySQLRevueDAO.getInstance().getById(idRevue));

                result.add(ab);

            }
            return result;
        }
        catch (SQLException sql){
            System.out.println("pb dans le select " + sql.getMessage());
        }
        return null;
    }

    @Override
    public Abonnement getById(int id) {


        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement requete = laConnexion.prepareStatement("SELECT id_abonnement,id_client,id_revue,date_debut,date_fin FROM Abonnement WHERE id_abonnement = ? ");
            requete.setInt(1, id);
            ResultSet res = requete.executeQuery();

            while (res.next()) {
                idRevue = res.getInt("id_revue");
                idClient = res.getInt("id_client");
                dateDeb = Helper.convertFromDate(res.getDate("date_debut"));
                dateFin = Helper.convertFromDate(res.getDate("date_fin"));

                return new Abonnement(id, dateDeb, dateFin,MySQLClientDAO.getInstance().getById(idClient),MySQLRevueDAO.getInstance().getById(idRevue));

            }
        }
        catch (SQLException sql){
            System.out.println("pb dans le select " + sql.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Abonnement objet) {
        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement requete = laConnexion.prepareStatement("insert into Abonnement (date_debut,date_fin,id_revue,id_client) values (?,?,?,?)");
            requete.setDate(1, convertToDateSql(objet.getDate_deb()));
            requete.setDate(2, convertToDateSql(objet.getDate_fin()));
            requete.setInt(3, objet.getRevue().getId_revue());
            requete.setInt(4,objet.getClient().getId_client());



            requete.executeUpdate();

            return true;
        } catch (SQLException | ParseException e) {
            System.out.println("pb dans le select" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Abonnement objet) {
        try {
            Connection laConnexion = this.laConnexion;

            PreparedStatement requete = laConnexion.prepareStatement("update Abonnement SET date_debut = ?, date_fin = ?, id_revue = ?, id_client = ? WHERE id_abonnement = ?");

            requete.setDate(1, convertToDateSql(objet.getDate_deb()));
            requete.setDate(2, convertToDateSql(objet.getDate_fin()));

            requete.setInt(3,objet.getRevue().getId_revue());
            requete.setInt(4,objet.getClient().getId_client());
            requete.setInt(5,objet.getId_abonnement());
            requete.executeUpdate();

            return true;
        } catch (SQLException | ParseException e) {
            System.out.println("pb dans le select" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Abonnement objet) {
        try{
            Connection laConnexion = this.laConnexion;

            PreparedStatement requete = laConnexion.prepareStatement("delete from Abonnement where id_abonnement=?");
            requete.setInt(1,objet.getId_abonnement());
            requete.executeUpdate();
            return true;
        }catch (SQLException sqle){
            System.out.println("Pb dans select " + sqle.getMessage());
        }
        return false;
    }

}
