package se201.projekat.introduction;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstViewController {

    public void setOnCreateNewAccount(ActionEvent actionEvent) throws Exception {

        //TODO  Promeni ime controllera tj kako oces da ti se zove controller za panel gde addujes novi kontakt
        Parent root = FXMLLoader.load(getClass().getResource("Nesto.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setTitle("JavaFX and Maven");
        window.setScene(scene);
        window.show();
    }

    public void setOnOpenAddressBook(ActionEvent actionEvent) {
    }

    public void setOnCheckTheAnalysis(ActionEvent actionEvent) {
    }
}
