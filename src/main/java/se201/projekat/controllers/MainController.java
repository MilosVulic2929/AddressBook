package se201.projekat.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.dao.GroupDao;
import se201.projekat.models.AddressBook;
import se201.projekat.models.Contact;
import se201.projekat.utils.FX;
import se201.projekat.utils.PaneTransition;
import se201.projekat.utils.sorting.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnNew, btnEdit, btnDelete;

    @FXML
    private ListView<Contact> listContacts;

    @FXML
    private BorderPane root;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<String> comboAscending;

    @FXML
    private ComboBox<SortingStrategy> comboSort;

    private SimpleObjectProperty<Contact> selected = new SimpleObjectProperty<>();
    private AddressBook addressBook;


    public MainController() {
        try {
            addressBook = new AddressBook();
        } catch (SQLException e) {
            Alert alert = FX.createAlert(Alert.AlertType.ERROR,
                    "Database Access Error " + e.getErrorCode(),
                    e.getMessage() + "\nSystem will exit now!",
                    Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initContactsList();
        initComboBoxes();
        selected.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                root.setCenter(imageView);
            } else {
                Font f = Font.font("Arial", FontWeight.BOLD, 16);
                VBox contactInfo = new VBox();
                contactInfo.setSpacing(10);
                contactInfo.setAlignment(Pos.CENTER_LEFT);
                contactInfo.setPadding(new Insets(10, 10, 10, 20));
                Label firstName = FX.createLabel("First Name: " + newValue.getPerson().getFirstName(), f);
                Label lastName = FX.createLabel("Last Name: " + newValue.getPerson().getLastName(), f);
                Label gender = FX.createLabel("Gender: " + newValue.getPerson().getGender(), f);
                Label email = FX.createLabel("Email: " + newValue.getEmail(), f);
                Label phone = FX.createLabel("Phone: " + newValue.getPhone(), f);
                Label country = FX.createLabel("Country: " + newValue.getAddress().getCountry(), f);
                Label city = FX.createLabel("City: " + newValue.getAddress().getCity(), f);
                Label street = FX.createLabel("Street: " + newValue.getAddress().getStreet()
                        + ", " + newValue.getAddress().getNumber(), f);
                String groupName = "Group: N/A";
                if (newValue.getGroupId() > 0) {
                    try {
                        groupName = "Group: " + DaoFactory.create(GroupDao.class).getById(newValue.getGroupId()).getName();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                Label group = FX.createLabel(groupName, f);

                contactInfo.getChildren().addAll(
                        firstName, lastName, gender, email,
                        phone, country, city, street, group
                );

                root.setCenter(contactInfo);
            }
        });

        btnDelete.disableProperty().bind(selected.isNull());
        btnEdit.disableProperty().bind(selected.isNull());
    }


    private void initContactsList() {
        listContacts.setItems(addressBook.getContacts());
        listContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Hmm " + newValue);
            if (oldValue != newValue) {
                System.out.println("Selected: " + newValue);
                selected.set(newValue);
            }
        });
    }

    private void initComboBoxes(){
        comboAscending.getItems().addAll("Ascending", "Descending");
        comboSort.getItems().addAll(
                new SortByFirstName(false),
                new SortByLastName(false),
                new SortByEmail(false)
        );

        comboAscending.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                comboSort.getItems().forEach(item -> item.setAscending(newValue.equals("Ascending")));
                comboSort.getSelectionModel().getSelectedItem().sort(addressBook.getContacts());
            }
        });

        comboSort.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != newValue){
                newValue.sort(addressBook.getContacts());
            }
        });

        comboSort.getSelectionModel().selectFirst();
        comboAscending.getSelectionModel().selectFirst();
    }

    public void setOnNewContact() {
        //TODO otvoriti panel za dodavanje kontakta
        System.out.println("Insert new contact");
    }


    public void setOnCheckStatistics(ActionEvent actionEvent) {
        PaneTransition.getInstance().transition(actionEvent, "../Analysis.fxml");
    }

    public void setOnEditContact() {
        if (selected.get() != null) {
            System.out.println("edit " + selected.get());
        } else {
            System.out.println("Nothing is selected");
        }
    }

    public void setOnDeleteContact(ActionEvent actionEvent) {
        if (selected.get() != null) {
            System.out.println("delete " + selected.get());
            try {
                DaoFactory.create(ContactDao.class).delete(selected.get());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                addressBook.getContacts().remove(selected.get());
            }
        } else {
            System.out.println("Nothing is selected");
        }
    }


}
