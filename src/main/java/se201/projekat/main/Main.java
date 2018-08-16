package se201.projekat.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se201.projekat.utils.DB;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FirstView.fxml"));
        Scene scene = new Scene(root);
        DB db = DB.getInstance();
        primaryStage.setTitle("JavaFX and Maven");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
