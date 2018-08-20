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
        Parent root = FXMLLoader.load(getClass().getResource("../FirstView.fxml"));


        //TODO INFO za vulica: primer kako se koristi DAO

        AddressDao addressDao = DaoFactory.create(AddressDao.class);
        addressDao.deleteAll(); // ocisti celu bazu

        DaoFactory.create(PersonDao.class).deleteAll();
        DaoFactory.create(GroupDao.class).deleteAll();

        ContactDao contactDao = DaoFactory.create(ContactDao.class);
        contactDao.deleteAll();



        Address adr = new Address("Serbia", "Nis", "asd", "123");
        Address adr1 = new Address("Serbia", "BG", "wwwww", "3A/4");

        addressDao.insert(adr);
        addressDao.insert(adr1);

        adr.setCity("Pirot");
        addressDao.update(adr);
        System.out.println(addressDao.getAll());
        addressDao.delete(adr1);
        System.out.println(addressDao.getAll());
        System.out.println("deleted: " + adr1);

        Person person1 = new Person("Filip","Dimitrijevic", Gender.MALE);
        Person person2 = new Person("Milica","Milivojevic", Gender.FEMALE);

        Contact c1 = new Contact(person1, adr, "filipd@gmail.com", "053-234-123");
        Contact c2 = new Contact(person2, adr1, "milicam@gmail.com", "123-123-123");

        contactDao.insert(c1);
        contactDao.insert(c2);

        System.out.println(contactDao.getAll());


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
