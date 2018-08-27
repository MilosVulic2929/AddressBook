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
import se201.projekat.models.*;
import se201.projekat.utils.FX;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


//TODO info za vulica, ovo je klasa za dodavanje novog kontakta, ovako sam nazvao jer treba da imamo i NewGroupController
// Nisam brisao fajlove stare, ali ovde je ubacena tvoja ekstenzija mislim da izgleda dobro
public class NewContactController implements Initializable {

    @FXML
    TextField textFirstName, textLastName, textEmail, textPhone, textCountry, textCity, textStreet, textStreetNumber;

    @FXML
    ComboBox<EmailExtension> comboEmailExt;

    @FXML
    ComboBox<Gender> comboGender;


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
    }

    /**
     * Zatvara stage
     * @param event
     */
    public void onBack(ActionEvent event){
        // Ako se stavi hide ostaje window u pozadini bolje je ovako jer se onda close
        Window window =  ((Node) (event.getSource())).getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Proverava validnost i da li postoji isti u bazi(Ista kombinacija imena i prezimena, ili isti email),
     * ako je sve ok sacuva u bazu i zatvara stage
     * @param event
     */
    public void onSave(ActionEvent event){
        if(isInputValid()){

            String firstName = textFirstName.getText();
            String lastName = textLastName.getText();
            Gender gender = comboGender.getValue();
            String email = textEmail.getText();
            String phone = textPhone.getText();
            String country = textCountry.getText();
            String city = textCity.getText();
            String street=  textStreet.getText();
            String stNumber = textStreetNumber.getText();

            ContactDao contactDao = DaoFactory.create(ContactDao.class);
            try {
                List<Contact> allContacts = contactDao.getAll();
                for (Contact c : allContacts){
                    if(c.getPerson().getFirstName().equals(firstName) && c.getPerson().getLastName().equals(lastName)){
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "Contact with same first name and last name already exists").showAndWait();
                        return;
                    } else if(c.getEmail().equals(email)){
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "Contact with same email already exists").showAndWait();
                        return;
                    }
                }

                Contact newContact = new Contact(
                        new Person(firstName, lastName, gender),
                        new Address(country,city,street,stNumber),
                        email,
                        phone);
                contactDao.insert(newContact);

                Window window =  ((Node) (event.getSource())).getScene().getWindow();
                window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
            } catch (SQLException e){
                FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(), "Couldn't save new contact!",
                        e.getMessage()).showAndWait();
            }
        } else {
            FX.createAlert(Alert.AlertType.INFORMATION, "Invalid input", "All fields must be valid", "").showAndWait();
        }
    }

    private String str;
    private boolean firstClick;

    public void handleEmailExtension(){
        //TODO za vulica info ima bug, ako promeni sa other na neki drugi, umesto da doda na kraj ono doda na pocetak
        if (!firstClick || textEmail.getText().length() == 0 || !textEmail.getText().contains(str)) {
            textEmail.appendText(comboEmailExt.getValue().getName());
            firstClick = true;
            str = comboEmailExt.getValue().getName();
        } else {
            textEmail.replaceText(startPosition(textEmail.getText(), str), endPosition(textEmail.getText(), str), comboEmailExt.getValue().getName());
            str = comboEmailExt.getValue().getName();
        }
    }

    private int startPosition(String sentence, String word) {
        return sentence.indexOf(word);
    }

    private int endPosition(String sentence, String word) {
        int startingPosition = sentence.indexOf(word);
        return startingPosition + word.length();
    }

    /**
     * Ovde moze validacija da se ubaci, trenutno uvek vraca true
     * @return
     */
    private boolean isInputValid(){
        //TODO da se ubaci neka osnovna provera
        return true;
    }
}
