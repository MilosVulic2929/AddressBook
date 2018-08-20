package se201.projekat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import se201.projekat.utils.PaneTransition;

public class AdditionalAnalysisController {

    @FXML
    private DatePicker dataPicker;

    public void handleDataPicker(ActionEvent actionEvent) {
    }

    public void handleBackBtn(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../Analysis.fxml");
    }

    public void handleHomeBtn(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../FirstView.fxml");
    }

    public void handleAddressBook(ActionEvent actionEvent) {
    }
}
