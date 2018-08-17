package se201.projekat.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;
import se201.projekat.dao.GroupDao;

public class AddressBook {

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private ObservableList<Group> groups = FXCollections.observableArrayList();

    public AddressBook(){

    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public ObservableList<Group> getGroups() {
        return groups;
    }
}
