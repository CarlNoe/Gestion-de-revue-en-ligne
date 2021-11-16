package javafx.controleur;

import methode.daofactory.DAOFactory;
import methode.idao.AbonnementDAO;
import methode.metier.Abonnement;
import methode.metier.Client;
import methode.metier.Revue;
import methode.tools.ChangementDeScene;
import methode.tools.Helper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


public class ControleurAbonnement implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean accueil =false;

    private ChangementDeScene changeScene = new ChangementDeScene();

    private String Newligne=System.getProperty("line.separator");

    private DAOFactory dao = ControleurAccueil.getDAO();

    @FXML
    private MenuBar myMenuBar;
    @FXML
    private TableView<Abonnement> tblAbonnement ;
    @FXML
    private TableColumn<Abonnement,LocalDate> colAbonnementDebut;

    @FXML
    private TableColumn<Abonnement,LocalDate> colAbonnementFin;

    @FXML
    private TableColumn<Abonnement,Integer> colAbonnementId;

    @FXML
    private TableColumn<Abonnement, Client> colAbonnementClient;

    @FXML private TableColumn<Abonnement, Revue> colAbonnementRevue;


    @FXML private DatePicker dateDebTextAbonnement;

    @FXML private DatePicker dateFinTextAbonnement;

    @FXML private ChoiceBox<Revue> cbxRevueAbonnemement;

    @FXML private Button btnAjouterAbonnement;

    @FXML private Button btnModifierAbonnement;

    @FXML private Button btnSupprimerAbonnement;

    @FXML private ChoiceBox<Client> cbxClientAbonnement;

    public void reset(){
        dateDebTextAbonnement.setValue(null);
        dateFinTextAbonnement.setValue(null);
        cbxClientAbonnement.getSelectionModel().selectFirst();
        cbxRevueAbonnemement.getSelectionModel().selectFirst();

        this.btnAjouterAbonnement.setDisable(false);
        this.btnModifierAbonnement.setDisable(true);
        this.btnSupprimerAbonnement.setDisable(true);
    }


    public void refreshTable(){
        tblAbonnement.getItems().clear();
        this.tblAbonnement.getItems().addAll(dao.getAbonnementDAO().findAll());

    }

    public boolean verifAbonnement(LocalDate dateDeb,LocalDate dateFin){

        String message = "";
        boolean ok = true;

        if (dateDeb == null || dateFin == null){
            message = "Veuillez saisir des dates"+Newligne;
            ok = false;
        }else if (dateDeb.isAfter(dateFin)){
            message = "Veuillez saisir une date de début valide"+Newligne;
            ok = false;
        }
        if (cbxClientAbonnement.getValue() == null){
            message = message + "Veuillez saisir un Abonnement"+Newligne;
            ok = false;
        }
        if (cbxRevueAbonnemement.getValue() == null){
            message = message + "Veuillez saisir une Revue"+Newligne;
            ok = false;
        }

        if (!ok){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Problème");
            dialog.setHeaderText("Problème de saisie");
            dialog.setContentText(message);
            dialog.showAndWait();
        }
        return ok ;
    }

    public void switchToAppAccueil(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("accueil",event,accueil,myMenuBar);
    }

    public void switchToAppPeriodicite(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("periodicite",event,accueil,myMenuBar);
    }

    public void switchToAppRevue(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("revue",event,accueil,myMenuBar);
    }

    public void switchToAppClient(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("client",event,accueil,myMenuBar);
    }

    @FXML
    public void boutonFermer(ActionEvent event) {
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*Initilialisation des différentes tables*/
        cbxClientAbonnement.setItems(FXCollections.observableArrayList(dao.getClientDAO().findAll()));
        cbxRevueAbonnemement.setItems(FXCollections.observableArrayList(dao.getRevueDAO().findAll()));
        colAbonnementId.setCellValueFactory(new PropertyValueFactory<Abonnement,Integer>("Id_abonnement"));
        colAbonnementDebut.setCellValueFactory(new PropertyValueFactory<Abonnement,LocalDate>("Date_deb"));
        colAbonnementFin.setCellValueFactory(new PropertyValueFactory<Abonnement,LocalDate>("Date_fin"));
        colAbonnementRevue.setCellValueFactory(new PropertyValueFactory<Abonnement,Revue>("Revue"));
        colAbonnementClient.setCellValueFactory(new PropertyValueFactory<Abonnement,Client>("Client"));

        /*Ajout des données dans les tables*/
        this.tblAbonnement.getItems().addAll(dao.getAbonnementDAO().findAll());

        /*Permet de remplir les texte quand on sélectionne dans la table*/
        this.tblAbonnement.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.btnSupprimerAbonnement.setDisable(newValue == null);
            this.btnModifierAbonnement.setDisable(newValue == null);
            this.btnAjouterAbonnement.setDisable(newValue != null);

            Abonnement abonnement = tblAbonnement.getSelectionModel().getSelectedItem();

            if (!(abonnement == null)) {
                cbxRevueAbonnemement.getSelectionModel().select(abonnement.getRevue());
                cbxClientAbonnement.getSelectionModel().select(abonnement.getClient());
                dateDebTextAbonnement.setValue(abonnement.getDate_deb());
                dateFinTextAbonnement.setValue(abonnement.getDate_fin());
            }
        });
    }

    @FXML
    void methodeAjouter(ActionEvent event){

        if (verifAbonnement(dateDebTextAbonnement.getValue(),dateFinTextAbonnement.getValue())){

            Revue revue = cbxRevueAbonnemement.getValue();
            Client client = cbxClientAbonnement.getValue();
            LocalDate dateFin = dateFinTextAbonnement.getValue();
            LocalDate dateDeb = dateDebTextAbonnement.getValue();

            dao.getAbonnementDAO().create(new Abonnement(0,dateDeb,dateFin,client ,revue));

            reset();
            refreshTable();
        }


    }

    @FXML
    void methodeModifier(ActionEvent event) {
        if (verifAbonnement(dateDebTextAbonnement.getValue(),dateFinTextAbonnement.getValue())){

            Abonnement abonnement = tblAbonnement.getSelectionModel().getSelectedItem();
            Revue revue = cbxRevueAbonnemement.getValue();
            Client client = cbxClientAbonnement.getValue();
            LocalDate dateFin = dateFinTextAbonnement.getValue();
            LocalDate dateDeb = dateDebTextAbonnement.getValue();

            dao.getAbonnementDAO().update(new Abonnement(abonnement.getId_abonnement(), dateDeb,dateFin, client,revue));
            reset();
            refreshTable();
        }
    }

    @FXML
    void methodeSupprimer(ActionEvent event) {
        dao.getAbonnementDAO().delete(tblAbonnement.getSelectionModel().getSelectedItem());
        reset();
        refreshTable();

    }

    @FXML
    void methodeReset(ActionEvent event) {
        reset();

    }


}
