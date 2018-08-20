package se201.projekat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import se201.projekat.models.EmailExtension;
import se201.projekat.utils.PaneTransition;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingController implements Initializable {

    @FXML
    private ComboBox comboBox;

    @FXML
    private TextField emailTxt;

    private String string;
    private boolean prviKlik;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpCombo();
    }

    public void setUpCombo() {
        comboBox.getItems().addAll(
                EmailExtension.GMAIL.getName(),
                EmailExtension.YAHOO.getName(),
                EmailExtension.ICLOUD.getName(),
                EmailExtension.OUTLOOK.getName(),
                EmailExtension.OTHER.getName());
    }

    public void handleBackButton(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../FirstView.fxml");
    }

    // TODO INFO za (filipa)
    // funkcionalnost izmedju comba i textFielda, (appendovanje brisanje) dole ispod su i pomocne metode

    public void handleComboBox() {
        if (prviKlik == false || emailTxt.getText().length() == 0 || !emailTxt.getText().contains(string)) {
            emailTxt.appendText(comboBox.getValue().toString());
            prviKlik = true;
            string = comboBox.getValue().toString();
        } else {
            emailTxt.replaceText(startPosition(emailTxt.getText(), string), endPosition(emailTxt.getText(), string), comboBox.getValue().toString());
            string = comboBox.getValue().toString();
        }
    }

    public int startPosition(String sentence, String word) {
        int startingPosition = sentence.indexOf(word);
        return startingPosition;
    }

    public int endPosition(String sentence, String word) {
        int startingPosition = sentence.indexOf(word);
        return startingPosition + word.length();
    }
}
