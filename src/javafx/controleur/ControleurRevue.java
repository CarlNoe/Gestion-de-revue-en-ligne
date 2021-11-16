package javafx.controleur;


import methode.daofactory.DAOFactory;
import methode.daofactory.Persistance;
import methode.metier.Periodicite;
import methode.metier.Revue;
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
import methode.tools.ChangementDeScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

;

public class ControleurRevue implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String Newligne=System.getProperty("line.separator");
    private boolean accueil = false;

    @FXML
    private MenuBar myMenuBar;

    @FXML
    private TableView<Revue> tblRevue;

    @FXML
    private TableColumn<Revue,Integer> colRevueId;

    @FXML
    private TableColumn<Revue,String> colRevueTitre;

    @FXML
    private TableColumn<Revue,Float> colRevueTarif;

    @FXML
    private TableColumn<Revue,String> colRevueDescription;

    @FXML
    private TableColumn<Revue,String> colRevueVisuel;

    @FXML
    private TableColumn<Revue, Periodicite> colRevuePeriodicite;

    @FXML
    private ChoiceBox<Periodicite> cbxPeriodicite;

    @FXML
    private TextArea textDescriptionRevue;

    @FXML
    private TextField textTarifRevue;

    @FXML
    private TextField textTitreRevue;

    @FXML
    private Button btnSupprimerRevue;

    @FXML
    private Button btnModifierRevue;

    @FXML
    private Button btnAjouterRevue;

    @FXML
    private RadioMenuItem radioListeMemoire;

    @FXML
    private RadioMenuItem radioMySql;

    private DAOFactory dao  = ControleurAccueil.getDAO();
    private ChangementDeScene changeScene = new ChangementDeScene();




    public boolean verificationDonnee() {

        String message ="";
        boolean ok = true;

        if (textTitreRevue.getText().trim().equalsIgnoreCase("")){
            message = "Veuillez saisir un Titre"+Newligne;
            ok = false;
        }
        if (textTarifRevue.getText().trim().equalsIgnoreCase("") ){
            message = message +"Veuillez saisir un Tarif"+Newligne;
            ok = false;
        } else if (!(textTarifRevue.getText().trim().matches("[+-]?\\d*(\\.\\d+)?"))) {
            message = message +"Veuillez saisir que des Chiffres)"+Newligne;
            ok = false;
        }
        if (textDescriptionRevue.getText().trim().matches("")){
            message = message + "Veuillez saisir une description"+Newligne;
            ok = false;
        }
        if (cbxPeriodicite.getValue() == null){
            message = message + "Veuillez saisir une périodicite";
            ok = false;
        }

        if(!ok){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Problème");
            dialog.setHeaderText("Problème de saisie");
            dialog.setContentText(message);
            dialog.showAndWait();
        }

        return ok;
    }

    public void refreshTable(){

        textTarifRevue.setText("");
        textDescriptionRevue.setText("");
        textTitreRevue.setText("");
        cbxPeriodicite.getSelectionModel().selectFirst();

        this.tblRevue.getItems().clear();
        this.tblRevue.getItems().addAll(dao.getRevueDAO().findAll());
    }

    //Création de tout les changement de scene dans le menu
    public void switchToAppAccueil(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("accueil", event, accueil,myMenuBar);
    }

    public void switchToAppAbonnement(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("abonnement", event, accueil,myMenuBar);
    }

    public void switchToAppPeriodicite(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("periodicite", event, accueil,myMenuBar);
    }

    public void switchToAppClient(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("client", event, accueil,myMenuBar);
    }

    @FXML
    public void boutonFermer(ActionEvent event) {
        stage = (Stage) myMenuBar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        this.cbxPeriodicite.setItems(FXCollections.observableArrayList(dao.getPeriodicteDAO().findAll()));

        colRevueTitre.setCellValueFactory(new PropertyValueFactory<Revue,String>("Titre"));
        colRevueId.setCellValueFactory(new PropertyValueFactory<Revue, Integer>("id_revue"));
        colRevueTarif.setCellValueFactory(new PropertyValueFactory<Revue, Float>("Tarif_numeros"));
        colRevueDescription.setCellValueFactory(new PropertyValueFactory<Revue, String>("Description"));
        colRevueVisuel.setCellValueFactory(new PropertyValueFactory<Revue, String>("Visuel"));
        colRevuePeriodicite.setCellValueFactory(new PropertyValueFactory<Revue,Periodicite>("Periodicite"));



        this.tblRevue.getItems().addAll(dao.getRevueDAO().findAll());
        this.btnSupprimerRevue.setDisable(true);
        this.btnModifierRevue.setDisable(true);



        this.tblRevue.getSelectionModel().selectedItemProperty
                ().addListener(
                (observable, oldValue, newValue) -> {
                    this.btnSupprimerRevue.setDisable(newValue == null);
                    this.btnModifierRevue.setDisable(newValue == null);
                    this.btnAjouterRevue.setDisable(newValue != null);

                    Revue revue = tblRevue.getSelectionModel().getSelectedItem();
                    if (!(revue == null)) {
                        this.textTarifRevue.setText(String.valueOf(revue.getTarif_numeros()));
                        this.textDescriptionRevue.setText(revue.getDescription());
                        this.textTitreRevue.setText(revue.getTitre());
                        cbxPeriodicite.getSelectionModel().select(revue.getPeriodicite());
                    }
                });


    }
    @FXML
    void resetRevue(ActionEvent event) {
        textTarifRevue.setText("");
        textDescriptionRevue.setText("");
        textTitreRevue.setText("");
        cbxPeriodicite.getSelectionModel().selectFirst();

        this.btnAjouterRevue.setDisable(false);
        this.btnModifierRevue.setDisable(true);
        this.btnSupprimerRevue.setDisable(true);
    }

    public void ajouterRevue(ActionEvent event) throws IOException {

        if (verificationDonnee()) {
            float tarif = Float.parseFloat(textTarifRevue.getText());
            Periodicite periodicite = cbxPeriodicite.getValue();

            dao.getRevueDAO().create(new Revue(0, periodicite, textDescriptionRevue.getText(), tarif, textTitreRevue.getText(), "bonjour"));
            refreshTable();
        }

    }


    @FXML
    void supprimerRevue(ActionEvent event) {
        dao.getRevueDAO().delete(tblRevue.getSelectionModel().getSelectedItem());
        refreshTable();
    }

    @FXML
    void modifierRevue(ActionEvent event) {
        if (verificationDonnee()){

            Revue revue = tblRevue.getSelectionModel().getSelectedItem();
            float tarif = Float.parseFloat(textTarifRevue.getText());
            Periodicite periodicite = cbxPeriodicite.getValue();

            dao.getRevueDAO().update(new Revue(revue.getId_revue(), periodicite, textDescriptionRevue.getText(), tarif, textTitreRevue.getText(), "bonjour"));
            refreshTable();
        }
    }

}
