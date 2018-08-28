package se201.projekat.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se201.projekat.dao.ContactDao;
import se201.projekat.dao.DaoFactory;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AddressBookTest {

    AddressBook addressBook;
    ContactDao contactDao = DaoFactory.create(ContactDao.class);

    Contact c1;
    Contact c2;

    @Before
    public void setup(){
        ContactDao contactDao = DaoFactory.create(ContactDao.class);
        contactDao.deleteAll();
        Address adr1 = new Address("Serbia", "Nis", "www", "123");
        Address adr2 = new Address("Serbia", "BG", "asd", "3A/4");
        Person person1 = new Person("Filip","Dimitrijevic", Gender.MALE);
        Person person2 = new Person("Milica","Milivojevic", Gender.FEMALE);
        c1 = new Contact(person1, adr1, "filipd@gmail.com", "053-234-123");
        c2 = new Contact(person2, adr2, "milicam@gmail.com", "123-123-123");
    }

    @Test
    public void testAddressBook(){
        try {
            contactDao.insert(c1);
            contactDao.insert(c2);
            addressBook = new AddressBook();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(addressBook.getContacts().contains(c1));
        assertTrue(addressBook.getContacts().contains(c2));
    }

    @After
    public void tearDown(){
        contactDao.deleteAll();
    }

}