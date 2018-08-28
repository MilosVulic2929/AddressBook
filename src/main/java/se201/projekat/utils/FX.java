package se201.projekat.utils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Klasa sa pomocnim metodama za JavaFX
 */
public class FX {

    /**
     * Koristi se za kreiranje iskacucih prozora razlicitog tipa i sa razlicitim podacima
     */
    public static Alert createAlert(Alert.AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static Label createLabel(String text, Font f){
        Label lbl = new Label(text);
        lbl.setFont(f);
        return lbl;
    }

    /**
     * Koristi se za tranziciju sa panela na panel
     */
    public static void transition(ActionEvent actionEvent, String path) {
        try {
            Parent root = FXMLLoader.load(FX.class.getResource(path));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
