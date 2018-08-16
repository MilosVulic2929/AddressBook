package se201.projekat.pane_transitions;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaneTransition implements Transition {

    private static PaneTransition instance = new PaneTransition();

    private PaneTransition(){}

    public static PaneTransition getInstance(){
        return instance;
    }

    @Override
    public void transition(ActionEvent actionEvent, String path) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
