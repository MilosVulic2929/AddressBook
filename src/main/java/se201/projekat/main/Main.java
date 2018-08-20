package se201.projekat.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se201.projekat.dao.AddressDao;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.dao.GroupDao;
import se201.projekat.models.Address;
import se201.projekat.models.Group;
import se201.projekat.utils.DB;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FirstView.fxml"));


        //TODO INFO za vulica: primer kako se koristi DAO

        AddressDao addressDao = DaoFactory.create(AddressDao.class);

        addressDao.deleteAll(); // ocisti celu bazu

        Address adr = new Address("Serbia", "Nis", "asd", "123");
        Address adr1 = new Address("Serbia", "BG", "wwwww", "3A/4");

        addressDao.insert(adr);
        addressDao.insert(adr1);

        adr.setCity("Pirot");
        addressDao.update(adr);
        System.out.println(addressDao.getAll());
        addressDao.delete(adr1.getId());
        System.out.println(addressDao.getById(1));

        GroupDao groupDao = DaoFactory.create(GroupDao.class);
        groupDao.deleteAll();

        Group group = new Group("LoL");
        groupDao.insert(group);
        groupDao.insert(new Group("Friends"));
        groupDao.insert(new Group("Shroomers"));

        group.setName("Not LoL");
        groupDao.update(group);

        System.out.println(groupDao.getAll());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
