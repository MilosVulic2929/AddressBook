package se201.projekat.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.dao.GroupDao;
import se201.projekat.models.AddressBook;
import se201.projekat.models.Contact;
import se201.projekat.models.Group;
import se201.projekat.utils.FX;
import se201.projekat.utils.sorting.SortByEmail;
import se201.projekat.utils.sorting.SortByFirstName;
import se201.projekat.utils.sorting.SortByLastName;
import se201.projekat.utils.sorting.SortingStrategy;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnEdit, btnDelete;

    @FXML
    private ListView<Contact> listContacts;

    @FXML
    private ListView<Group> listGroups;

    @FXML
    private BorderPane root;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<String> comboAscending;

    @FXML
    private ComboBox<SortingStrategy> comboSort;

    @FXML
    private TabPane tabPane;

    @FXML
    private Accordion accordionGroups;


    private SimpleObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Group> selectedGroup = new SimpleObjectProperty<>();
    private AddressBook addressBook;


    public MainController() {
        try {
            addressBook = new AddressBook();
        } catch (SQLException e) {
            Alert alert = FX.createAlert(Alert.AlertType.ERROR,
                    "Database Access Error " + e.getErrorCode(),
                    e.getMessage() + "\nSystem will exit now!",
                    Arrays.toString(e.getStackTrace()));
            alert.showAndWait();
            System.exit(1);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpContactList();
        setUpGroupList();
        setUpComboBoxes();
        selectedContact.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                root.setCenter(imageView);
            } else {
                showContactInfo(newValue);
            }
        });
        setupTabPane();

        btnDelete.disableProperty().bind(selectedContact.isNull().and(selectedGroup.isNull()));
        btnEdit.disableProperty().bind(selectedContact.isNull().and(selectedGroup.isNull()));
    }

    private void setupTabPane() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                loadContacts();
                listContacts.getSelectionModel().clearSelection();
                if (newValue.getText().equals("By group")) {
                    updateAccordion();
                } else if (newValue.getText().equals("Groups")) {
                    selectedContact.set(null);

                } else {
                    selectedContact.set(null);
                }
            }
        });
    }

    /**
     * Prikazuje sve informacije o kontaktu
     * Ovo je sad gotovo, prikazuje i datum kreiranja i sve
     */
    private void showContactInfo(Contact newValue) {
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
        Label creationDate = FX.createLabel("Creation Date: " + newValue.getCreationDate(), f);
        String groupName = "Group: N/A";

        if (newValue.getGroupId() > 0) {
            System.out.println("LOL");
            try {
                groupName = "Group: " + DaoFactory.create(GroupDao.class).getById(newValue.getGroupId()).getName();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Label group = FX.createLabel(groupName, f);
        contactInfo.getChildren().addAll(
                firstName, lastName, gender, email, phone, country, city, street, group, creationDate
        );
        root.setCenter(contactInfo);
    }

    /**
     * Popunjava list kontakta sa podacima iz baze
     */
    private void setUpContactList() {
        listContacts.setItems(addressBook.getContacts());
        listContacts.getSelectionModel().selectedItemProperty().addListener(listContactChanged);
    }

    private void setUpGroupList() {
        listGroups.setItems(addressBook.getGroups());
        listGroups.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                selectedGroup.set(newValue);
            }
        });
    }

    /**
     * Popunjava ComboBoxeve
     */
    private void setUpComboBoxes() {
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
            if (oldValue != newValue) {
                newValue.sort(addressBook.getContacts());
            }
        });

        comboSort.getSelectionModel().selectFirst();
        comboAscending.getSelectionModel().selectFirst();
    }

    public void onNewContact() throws IOException {
        listContacts.getSelectionModel().clearSelection();
        Parent root = FXMLLoader.load(getClass().getResource("../NewContact.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("New Contact");
        stage.initModality(Modality.APPLICATION_MODAL); // Da ne moze da se klikne negde drugde dok se ne zatvori
        stage.setOnCloseRequest(event -> loadContacts());
        stage.show();
    }

    public void onNewGroup() throws IOException {
        listGroups.getSelectionModel().clearSelection();
        Parent root = FXMLLoader.load(getClass().getResource("../NewGroup.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("New Group");
        stage.initModality(Modality.APPLICATION_MODAL); // Da ne moze da se klikne negde drugde dok se ne zatvori
        stage.setOnCloseRequest(event -> loadContacts());
        stage.show();
    }


    public void onCheckStatistics(ActionEvent actionEvent) {
        FX.transition(actionEvent, "../Statistics.fxml");
    }

    public void onEdit() throws IOException {
        if (selectedContact.get() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EditContact.fxml"));
            Parent root = loader.load();
            EditContactController controller = loader.getController();
            controller.setContact(selectedContact.get());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Contact");
            stage.initModality(Modality.APPLICATION_MODAL); // Da ne moze da se klikne negde drugde dok se ne zatvori
            stage.setOnCloseRequest(event -> loadContacts());
            stage.show();
        } else if (selectedGroup.get() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EditGroup.fxml"));
            Parent root = loader.load();
            EditGroupController controller = loader.getController();
            controller.setGroup(selectedGroup.get());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Group");
            stage.initModality(Modality.APPLICATION_MODAL); // Da ne moze da se klikne negde drugde dok se ne zatvori
            stage.setOnCloseRequest(event -> loadContacts());
            stage.show();
        }
    }

    public void onDelete() {
        if (selectedContact.get() != null) {
            Alert alert = FX.createAlert(Alert.AlertType.CONFIRMATION, "Delete Contact Confirmation",
                    "Are you sure you want to delete contact?", "");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DaoFactory.create(ContactDao.class).delete(selectedContact.get());
                    addressBook.getContacts().remove(selectedContact.get());
                    listContacts.getSelectionModel().clearSelection();
                    updateAccordion();
                } catch (SQLException e) {
                    FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(),
                            "Couldn't delete contact!", "Please try again").showAndWait();
                }
            }
        } else if (selectedGroup.get() != null) {
            Alert alert = FX.createAlert(Alert.AlertType.CONFIRMATION, "Delete Contact Confirmation",
                    "Are you sure you want to delete group?", "(This will also delete all contacts in that group)");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DaoFactory.create(GroupDao.class).delete(selectedGroup.get());
                    addressBook.getGroups().remove(selectedGroup.get());
                    listGroups.getSelectionModel().clearSelection();
                } catch (SQLException e) {
                    FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(),
                            "Couldn't delete group!", "Please try again").showAndWait();
                }
            }
        }
    }

    // Listener koji se poziva kada se selektuje neki element u listi
    private ChangeListener<Contact> listContactChanged = ((observable, oldValue, newValue) -> {
        if (oldValue != newValue) {
            selectedContact.set(newValue);
        }
    });

    private void loadContacts() {
        addressBook.getContacts().clear();
        addressBook.getGroups().clear();
        try {
            addressBook.getContacts().addAll(DaoFactory.create(ContactDao.class).getAll());
            addressBook.getGroups().addAll(DaoFactory.create(GroupDao.class).getAll());
        } catch (SQLException e) {
            FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(), "Couldn't load contacts!",
                    "Application will exit now!").showAndWait();

            System.exit(1);
        }
    }

    private void updateAccordion() {
        accordionGroups.getPanes().clear();
        for (Group g : addressBook.getGroups()) {
            TitledPane pane = new TitledPane();
            pane.setText(g.getName());
            ListView<Contact> contactsInGroup = new ListView<>();
            ObservableList<Contact> list = FXCollections.observableArrayList();
            for (Contact c : addressBook.getContacts()) {
                if (c.getGroupId() == g.getId()) {
                    list.add(c);
                }
            }
            contactsInGroup.setItems(list);
            contactsInGroup.getSelectionModel().selectedItemProperty().addListener(listContactChanged);
            pane.setContent(contactsInGroup);
            accordionGroups.getPanes().add(pane);
        }
    }


}
