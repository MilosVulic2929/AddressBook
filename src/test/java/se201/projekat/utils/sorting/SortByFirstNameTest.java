package se201.projekat.utils.sorting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import se201.projekat.models.Contact;
import se201.projekat.models.Gender;
import se201.projekat.models.Person;

import static org.junit.Assert.assertEquals;

public class SortByFirstNameTest {

    @Test
    public void sortingTestAscending() {

        SortingStrategy strategy = new SortByFirstName(true);

        ObservableList<Contact> lista = FXCollections.observableArrayList(
                new Contact(new Person("Bojan", "Bojovic", Gender.FEMALE), null, "", ""),
                new Contact(new Person("Stefan", "Stefanovic", Gender.FEMALE), null, "", ""),
                new Contact(new Person("Ana", "Antic", Gender.FEMALE), null, "", "")
        );

        strategy.sort(lista);

        assertEquals("Ana", lista.get(0).getPerson().getFirstName());
        assertEquals("Bojan", lista.get(1).getPerson().getFirstName());
        assertEquals("Stefan", lista.get(2).getPerson().getFirstName());
    }

    @Test
    public void sortingTestDescending() {

        SortingStrategy strategy = new SortByFirstName(false);
        ObservableList<Contact> lista = FXCollections.observableArrayList(
                new Contact(new Person("Petar", "Petrovic", Gender.FEMALE), null, "", ""),
                new Contact(new Person("Zorana", "Zokic", Gender.FEMALE), null, "", ""),
                new Contact(new Person("Stefan", "Stefanovic", Gender.FEMALE), null, "", "")
        );
        strategy.sort(lista);

        assertEquals("Zorana", lista.get(0).getPerson().getFirstName());
        assertEquals("Stefan", lista.get(1).getPerson().getFirstName());
        assertEquals("Petar", lista.get(2).getPerson().getFirstName());

    }

}