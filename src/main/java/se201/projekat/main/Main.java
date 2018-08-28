package se201.projekat.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import se201.projekat.dao.*;
import se201.projekat.models.*;
import se201.projekat.utils.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {

        // TEST PODACI
        if(true){
            // Prvo ocisti celu bazu
            AddressDao addressDao = DaoFactory.create(AddressDao.class);
            addressDao.deleteAll();
            GroupDao groupDao = DaoFactory.create(GroupDao.class);
            groupDao.deleteAll();
            ContactDao contactDao = DaoFactory.create(ContactDao.class);
            contactDao.deleteAll();
            DaoFactory.create(PersonDao.class).deleteAll();
            DaoFactory.create(GroupDao.class).deleteAll();

            Address adr1 = new Address("Serbia", "Knjazevac", "Ulica 1", "123");
            Address adr2 = new Address("Serbia", "Beograd", "Ulica 2", "3/C");
            Person person1 = new Person("Filip","Dimitrijevic", Gender.MALE);
            Person person2 = new Person("Milica","Aleksic", Gender.FEMALE);
            Contact c1 = new Contact(person1, adr1, "filipd@gmail.com", "053-234-123");
            Contact c2 = new Contact(person2, adr2, "milica.aleksic123@hotmail.com", "123-123-123");

            contactDao.insert(c1);
            contactDao.insert(c2);

            groupDao.insert(new Group("Prijatelji"));
            groupDao.insert(new Group("Posao"));
        }

        Parent root = FXMLLoader.load(getClass().getResource("../MainView.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
