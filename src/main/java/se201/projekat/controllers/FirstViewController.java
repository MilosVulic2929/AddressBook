package se201.projekat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FirstViewController {

    public void setOnCreateNewAccount(ActionEvent actionEvent) {

        //TODO namenjen (Filipu)
        // Promeni ime controllera i fxml fajla tj kako oces da ti se zove controller i fxml za panel gde addujes novi kontakt
        // Stavi ih po paketima isto kako mislis da treba
        // Imam jos jednu ideju ako mu ovo bude bilo malo ili brzo zavrsimo i to, mozemo da izvucemo jos jedan panel tipa log in i register ono razmisli o tome

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../AddingContact.fxml"));
            ((Node) actionEvent.getSource()).getScene().setRoot(root);

            /*
                TODO INFO namenjen (Vulicu)
                Ovo sam skratio posto kad se uzme scena to je ta vec postojeca ne mora se pravi nova i sve to
                samo se zameni root na sceni i tolko,
                ali ostavio sam ovaj kod ako treba duza verzija
             */

            /*
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnOpenAddressBook(ActionEvent actionEvent) {

        //TODO namenjen (Filipu)
        // Ovde ti je tvoja akcija da otvoris adressBook window
    }

    public void setOnCheckTheAnalysis(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Analysis.fxml"));
            Scene scene = ((Node) actionEvent.getSource()).getScene();
            scene.getWindow().setHeight(800);
            ((Node) actionEvent.getSource()).getScene().setRoot(root);
            /*
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
