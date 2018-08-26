package se201.projekat.utils.sorting;

import javafx.collections.ObservableList;
import se201.projekat.models.Contact;

import java.util.Comparator;

public interface SortingStrategy extends Comparator<Contact> {

    void sort(ObservableList<Contact> listToSort);

    boolean isAscending();
    void setAscending(boolean ascending);

}
