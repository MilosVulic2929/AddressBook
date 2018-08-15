package se201.projekat.introduction;

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
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("JavaFX and Maven");
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnOpenAddressBook(ActionEvent actionEvent) {
    }

    public void setOnCheckTheAnalysis(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Analysis.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("JavaFX and Maven");
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
