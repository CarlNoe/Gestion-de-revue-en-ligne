package javafx.controleur;

import methode.daofactory.DAOFactory;

import methode.metier.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import methode.tools.ChangementDeScene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static methode.tools.ProcessClient.normalizeAdresse;

public class ControleurClient implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private DAOFactory dao = ControleurAccueil.getDAO();
    private String Newligne=System.getProperty("line.separator");
    private boolean accueil = false;
    private ChangementDeScene changeScene = new ChangementDeScene();

    @FXML
    MenuBar myMenuBar;
    @FXML
    TableView<Client> tblClient;
    @FXML
    private TableColumn<Client,String> colClientAdresse;

    @FXML
    private TableColumn<Client,Integer> colClientId;

    @FXML
    private TableColumn<Client,String> colClientNom;

    @FXML
    private TableColumn<Client,String> colClientPrenom;

    @FXML
    private TextField textFieldCpClient;

    @FXML
    private TextField textFieldNoRueClient;

    @FXML
    private TextField textFieldNomClient;

    @FXML
    private TextField textFieldPaysClient;

    @FXML
    private TextField textFieldPrenomClient;

    @FXML
    private TextField textFieldVilleClient;

    @FXML
    private TextField textFieldVoieClient;

    @FXML
    private Button btnAjouterClient;

    @FXML
    private Button btnModifierClient;

    @FXML
    private Button btnSupprimerClient;


    public String fieldNonVide(TextField text,String message,String erreur){

        if (text.getText().trim().equalsIgnoreCase("")){
            message = message + "Veuiller saisir un" + erreur +Newligne;
        }
        return message;
    }

    public void refreshTextField(){
        textFieldCpClient.setText("");
        textFieldNomClient.setText("");
        textFieldPrenomClient.setText("");
        textFieldVoieClient.setText("");
        textFieldVilleClient.setText("");
        textFieldNoRueClient.setText("");
        textFieldPaysClient.setText("");
    }
    public boolean verificationDonnee() {

        String message ="";
        boolean ok = true;

        message = fieldNonVide(textFieldNomClient,message," Nom");
        message = fieldNonVide(textFieldPrenomClient,message," Prenom");
        message = fieldNonVide(textFieldVilleClient,message,"e Ville");
        message = fieldNonVide(textFieldNoRueClient,message," Numéros de rue:");
        message = fieldNonVide(textFieldPaysClient,message," Pays");
        message = fieldNonVide(textFieldVoieClient,message,"e Voie");
        message = fieldNonVide(textFieldCpClient,message," Code Postal");


        if(!(message.equalsIgnoreCase(""))){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Problème");
            dialog.setHeaderText("Problème de saisie");
            dialog.setContentText(message);
            dialog.showAndWait();
            ok = false;
        }

        return ok;
    }

    public void refreshTable(){
         refreshTextField();

        this.tblClient.getItems().clear();
        this.tblClient.getItems().addAll(dao.getClientDAO().findAll());
    }

    public void switchToAppAccueil(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("accueil", event, accueil,myMenuBar);
    }

    public void switchToAppAbonnement(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("abonnement", event, accueil,myMenuBar);
    }

    public void switchToAppRevue(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("revue",event, accueil,myMenuBar);
    }

    public void switchToAppPeriodicite(ActionEvent event) throws IOException {
        changeScene.changementdeFenetre("periodicite", event, accueil,myMenuBar);
    }

    @FXML
    public void boutonFermer(ActionEvent event) {
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void methodeReset(ActionEvent event) {
        refreshTextField();

        this.btnSupprimerClient.setDisable(true);
        this.btnModifierClient.setDisable(true);
        this.btnAjouterClient.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.btnSupprimerClient.setDisable(true);
        this.btnModifierClient.setDisable(true);


        colClientPrenom.setCellValueFactory(new PropertyValueFactory<Client, String>("Prenom"));
        colClientNom.setCellValueFactory(new PropertyValueFactory<Client, String>("Nom"));
        colClientId.setCellValueFactory(new PropertyValueFactory<Client, Integer>("Id_client"));
        colClientAdresse.setCellValueFactory(new PropertyValueFactory<Client, String>("AdresseComplete"));

        this.tblClient.getItems().addAll(dao.getClientDAO().findAll());

        this.tblClient.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.btnSupprimerClient.setDisable(newValue == null);
                    this.btnModifierClient.setDisable(newValue == null);
                    this.btnAjouterClient.setDisable(newValue != null);

                    Client client = tblClient.getSelectionModel().getSelectedItem();
                    if (!(client == null)) {
                        textFieldCpClient.setText(client.getCode_postal());
                        textFieldNomClient.setText(client.getNom());
                        textFieldPrenomClient.setText(client.getPrenom());
                        textFieldVoieClient.setText(client.getVoie());
                        textFieldVilleClient.setText(client.getVille());
                        textFieldNoRueClient.setText(client.getNoRue());
                        textFieldPaysClient.setText(client.getPays());
                    }
                });
    }

    @FXML
    void methodeAjouter(ActionEvent event) {
    if (verificationDonnee()) {
        Client client = new Client(textFieldNomClient.getText(), textFieldPrenomClient.getText(), textFieldNoRueClient.getText(), textFieldVilleClient.getText(), textFieldPaysClient.getText(), textFieldVoieClient.getText(), textFieldCpClient.getText(), 0);
        dao.getClientDAO().create(normalizeAdresse(client));
        refreshTable();
    }
    }



    @FXML
    void methodeModifier(ActionEvent event) {

        if (verificationDonnee()) {
            Client oldclient = tblClient.getSelectionModel().getSelectedItem();
            dao.getClientDAO().update(normalizeAdresse(new Client(textFieldNomClient.getText(), textFieldPrenomClient.getText(), textFieldNoRueClient.getText(), textFieldVilleClient.getText(), textFieldPaysClient.getText(), textFieldVoieClient.getText(), textFieldCpClient.getText(), oldclient.getId_client())));
            refreshTable();
        }
    }

    @FXML
    void methodeSupprimer(ActionEvent event) {
        Client client = tblClient.getSelectionModel().getSelectedItem();
        dao.getClientDAO().delete(client);
        refreshTable();
    }

    @FXML
    void methodeImporterClient(ActionEvent event) throws IOException {
        List<Client> listClient = dao.getClientDAO().findAll();
        boolean probleme = false;
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) { // si on choisit un fichier , on fait la methode
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath())); // recupère le chemin du dossier
            String line ="";
            try {

                while((line =reader.readLine()) != null) {
                    boolean existe = false;
                    String[] row = line.split(";");
                    Client client = new Client(row[0],row[1],row[2],row[3],row[4],row[5],row[6],Integer.parseInt(row[7]));
                    for (Client cl: listClient) {
                        if (normalizeAdresse(client).equals(cl)){
                            existe = true;
                            probleme = true;
                            System.out.println("yes");
                        }
                    }
                    if (!existe){
                        dao.getClientDAO().create(normalizeAdresse(client));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (probleme){
                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Problème");
                    dialog.setHeaderText("Des client n'ont pas pu être ajouter");
                    dialog.setContentText("Des clients n'ont pas pu être ajouter car il y a la présence de 2 même clients");
                    dialog.showAndWait();
                }
                refreshTable();
                reader.close();
            }
        }
    }
}
