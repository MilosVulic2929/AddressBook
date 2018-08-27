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


/**
 * Kontroler za editovanje kontakta
 */
public class EditContactController implements Initializable {
    @FXML
    TextField textFirstName, textLastName, textEmail, textPhone, textCountry, textCity, textStreet, textStreetNumber;

    @FXML
    ComboBox<Gender> comboGender;

    private Contact contact;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpComboBoxes();
    }

    /**
     * Popunjava sve ComboBox elemente
     */
    private void setUpComboBoxes() {
        comboGender.getItems().addAll(Gender.values());
        comboGender.getSelectionModel().selectFirst();
    }

    // Setuje kontakt koji se edituje
    public void setContact(Contact contact){
        this.contact = contact;

        textFirstName.setText(contact.getPerson().getFirstName());
        textLastName.setText(contact.getPerson().getLastName());
        textEmail.setText(contact.getEmail());
        textPhone.setText(contact.getPhone());
        textCountry.setText(contact.getAddress().getCountry());
        textCity.setText(contact.getAddress().getCity());
        textStreet.setText(contact.getAddress().getStreet());
        textStreetNumber.setText(contact.getAddress().getNumber());

        comboGender.getSelectionModel().select(contact.getPerson().getGender());
    }

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
                    if(c.getId() != contact.getId() && c.getPerson().getFirstName().equals(firstName) && c.getPerson().getLastName().equals(lastName)){
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "That first name and last name is already taken by another contact").showAndWait();
                        return;
                    } else if(c.getId() != contact.getId() && c.getEmail().equals(email)){
                        FX.createAlert(Alert.AlertType.INFORMATION, "Duplicate Entry", "Duplicate entry",
                                "That email is already taken by another contact").showAndWait();
                        return;
                    }
                }
                contact.getPerson().setFirstName(firstName);
                contact.getPerson().setLastName(lastName);
                contact.getPerson().setGender(gender);
                contact.setEmail(email);
                contact.setPhone(phone);
                contact.getAddress().setCountry(country);
                contact.getAddress().setCity(city);
                contact.getAddress().setStreet(street);
                contact.getAddress().setNumber(stNumber);
                contactDao.update(contact);

                Window window =  ((Node) (event.getSource())).getScene().getWindow();
                window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));

            } catch (SQLException e){
                FX.createAlert(Alert.AlertType.ERROR, "Database Error " + e.getErrorCode(), "Couldn't update contact!",
                        e.getMessage()).showAndWait();
            }
        } else {
            FX.createAlert(Alert.AlertType.INFORMATION, "Invalid input",
                    "All fields must be valid", "").showAndWait();
        }
    }

    public void onBack(ActionEvent event){
        Window window =  ((Node) (event.getSource())).getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


    private boolean isInputValid(){
        //TODO treba da se doda osnovna validacija
        return true;
    }


}
