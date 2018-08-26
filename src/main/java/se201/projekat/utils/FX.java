package se201.projekat.utils;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class FX {

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

}
