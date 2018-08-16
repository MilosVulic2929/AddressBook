package se201.projekat.utils.sorting;

import javafx.collections.ObservableList;
import se201.projekat.models.Contact;

public class SortByLastName implements SortingStrategy{

    private boolean ascending;

    public SortByLastName(boolean ascending){
        this.ascending = ascending;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public void sort(ObservableList<Contact> listToSort) {
        listToSort.sort(this);
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        int comparisonValue = o1.getPerson().getLastName().compareToIgnoreCase(o2.getPerson().getLastName());
        return ascending ? comparisonValue : comparisonValue * -1;
    }
}