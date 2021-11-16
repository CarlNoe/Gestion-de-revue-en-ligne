package javafx.controleur;

import methode.daofactory.DAOFactory;
import methode.daofactory.Persistance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import methode.tools.ChangementDeScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurAccueil implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    ChangementDeScene changeScene = new ChangementDeScene();
    private boolean accueil = true;
    private static DAOFactory dao;
    @FXML
    private RadioButton radioListeMemoire;

    @FXML
    private RadioButton radioMySql;

    @FXML
    public void getDaoMethode(ActionEvent event) {
        if (radioListeMemoire.isSelected()){
            dao = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
        }else if (radioMySql.isSelected()){
            dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
        }
    }

    public static DAOFactory getDAO(){
        return dao;
    }

    public void switchToAppRevue(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("revue",event,accueil,null);
    }

    public void switchToAppPeriodicite(ActionEvent event) throws IOException {

        changeScene.changementdeFenetre("periodicite",event,accueil,null);
    }

    public void switchToAppClient(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("client",event,accueil,null);
    }

    public void switchToAppAbonnement(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("abonnement",event,accueil,null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
        radioListeMemoire.setSelected(true);
    }
}

