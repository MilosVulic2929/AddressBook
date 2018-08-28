package se201.projekat.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.dao.GroupDao;
import se201.projekat.models.*;
import se201.projekat.utils.FX;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class NewContactController implements Initializable {

    @FXML
    TextField textFirstName, textLastName, textEmail, textPhone, textCountry, textCity, textStreet, textStreetNumber;

    @FXML
    ComboBox<EmailExtension> comboEmailExt;

    @FXML
    ComboBox<Gender> comboGender;

    @FXML
    ComboBox<Group> comboGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpComboBoxes();
    }

    /**
     * Popunjava sve ComboBox elemente
     */
    private void setUpComboBoxes() {
        comboEmailExt.getItems().addAll(EmailExtension.values());
        comboGender.getItems().addAll(Gender.values());
        comboGender.getSelectionModel().selectFirst();
        try {
            comboGroup.getItems().add(new Group("N/A"));
            comboGroup.getItems().addAll(DaoFactory.create(GroupDao.class).getAll());
            comboGroup.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(), "Couldn't load groups!",
                    e.getMessage()).showAndWait();
        }
    }

    /**
     * Zatvara stage
     */
    public void onBack(ActionEvent event) {
        // Ako se stavi hide ostaje window u pozadini bolje je ovako jer se onda close
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Proverava validnost i da li postoji isti u bazi(Ista kombinacija imena i prezimena, ili isti email),
     * ako je sve ok sacuva u bazu i zatvara stage
     */
    public void onSave(ActionEvent event) {
        if (isInputValid()) {
            String firstName = textFirstName.getText();
            String lastName = textLastName.getText();
            Gender gender = comboGender.getValue();
            String email = textEmail.getText();
            String phone = textPhone.getText();
            String country = textCountry.getText();
            String city = textCity.getText();
            String street = textStreet.getText();
            String stNumber = textStreetNumber.getText();

            ContactDao contactDao = DaoFactory.create(ContactDao.class);
            try {
                List<Contact> allContacts = contactDao.getAll();
                for (Contact c : allContacts) {
                    if (c.getPerson().getFirstName().equals(firstName) && c.getPerson().getLastName().equals(lastName)) {
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "Contact with same first name and last name already exists").showAndWait();
                        return;
                    } else if (c.getEmail().equals(email)) {
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "Contact with same email already exists").showAndWait();
                        return;
                    }
                }
                Contact newContact = new Contact(
                        new Person(firstName, lastName, gender),
                        new Address(country, city, street, stNumber),
                        email,
                        phone);
                newContact.setGroupId(comboGroup.getValue().getId());

                contactDao.insert(newContact);
                Window window = ((Node) (event.getSource())).getScene().getWindow();
                window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
            } catch (SQLException e) {
                FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(), "Couldn't save new contact!",
                        e.getMessage()).showAndWait();
            }
        } else {
            FX.createAlert(Alert.AlertType.INFORMATION, "Invalid input",
                    "Contact must have FirstName or LastName", "").showAndWait();
        }
    }

    private String str;
    private boolean firstClick;

    /**
     * metoda koja dodaje extenziju mejla oblika @gmail.com ili nesto slicno tome, u zavisnosti od toga sta je korisnik odabraou  comboBoxu
     */
    public void handleEmailExtension() {
        if (!firstClick || textEmail.getText().length() == 0 || !textEmail.getText().contains(str)) {
            textEmail.appendText(comboEmailExt.getValue().getName());
            firstClick = true;
            str = comboEmailExt.getValue().getName();
        } else if (str.length() == 0) {
            textEmail.appendText(comboEmailExt.getValue().getName());
            str = comboEmailExt.getValue().getName();
        } else {
            textEmail.replaceText(startPosition(textEmail.getText(), str), endPosition(textEmail.getText(), str), comboEmailExt.getValue().getName());
            str = comboEmailExt.getValue().getName();
        }
    }

    /**
     * pomocne metode koje odredjuju indexe pocetka i kraja u datom stringu
     */
    private int startPosition(String sentence, String word) {
        return sentence.indexOf(word);
    }

    private int endPosition(String sentence, String word) {
        int startingPosition = sentence.indexOf(word);
        return startingPosition + word.length();
    }

    /**
     * Metoda za koja vraca true ako su sva polja validno popunjena a false ako nisu
     */
    private boolean isInputValid() {
        return textFirstName.getLength() > 0 || textLastName.getText().length() > 0;
    }

}
