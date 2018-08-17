package se201.projekat.controllers;

import javafx.event.ActionEvent;
import se201.projekat.utils.PaneTransition;


public class FirstViewController {

    public void setOnCreateNewAccount(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../AddingContact.fxml");
    }

    public void setOnOpenAddressBook(ActionEvent actionEvent) {

        //TODO namenjen (Filipu)
        // Ovde ti je tvoja akcija da otvoris adressBook window
    }

    public void setOnCheckTheAnalysis(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../Analysis.fxml");
    }
}
