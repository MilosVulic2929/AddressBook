package se201.projekat.controllers;

import javafx.event.ActionEvent;
import se201.projekat.pane_transitions.PaneTransition;


public class FirstViewController {

    public void setOnCreateNewAccount(ActionEvent actionEvent) {
        PaneTransition paneTransition = PaneTransition.getInstance();
        paneTransition.transition(actionEvent, "../AddingContact.fxml");
    }

    public void setOnOpenAddressBook(ActionEvent actionEvent) {

        //TODO namenjen (Filipu)
        // Ovde ti je tvoja akcija da otvoris adressBook window
    }

    public void setOnCheckTheAnalysis(ActionEvent actionEvent) {
        PaneTransition paneTransition = PaneTransition.getInstance();
        paneTransition.transition(actionEvent, "../Analysis.fxml");
    }
}
