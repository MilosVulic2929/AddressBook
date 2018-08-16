package se201.projekat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FirstViewController {

    public void setOnCreateNewAccount(ActionEvent actionEvent) {


        try {
            Parent root = FXMLLoader.load(getClass().getResource("../AddingContact.fxml"));
           // ((Node) actionEvent.getSource()).getScene().setRoot(root);

            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
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
           // ((Node) actionEvent.getSource()).getScene().setRoot(root);

            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
