package methode.tools;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class ChangementDeScene {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    /*On simplifie la génération de fenêtre */

    public void changementdeFenetre(String app, ActionEvent event ,boolean accueil ,MenuBar myMenuBar) throws IOException {

        stage = null;
        root = null;


        switch (app){
            case "periodicite" :
                root = FXMLLoader.load(getClass().getResource("../../javafx/vue/AppPeriodicite.fxml"));

                break;
            case "revue" :
                root = FXMLLoader.load(getClass().getResource("../../javafx/vue/AppRevue.fxml"));
                break;
            case "client" :
                root = FXMLLoader.load(getClass().getResource("../../javafx/vue/AppClient.fxml"));

                break;
            case "abonnement" :
                root = FXMLLoader.load(getClass().getResource("../../javafx/vue/AppAbonnement.fxml"));
                break;
            case "accueil" :
                root = FXMLLoader.load(getClass().getResource("../../javafx/vue/AppAccueil.fxml"));
                break;

        }

        if (accueil) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Revue on line");
            stage.show();
        }else {
            stage = (Stage) myMenuBar.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
